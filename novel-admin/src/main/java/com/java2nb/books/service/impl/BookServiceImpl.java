package com.java2nb.books.service.impl;

import com.java2nb.books.dao.BookContentDao;
import com.java2nb.books.dao.BookIndexDao;
import com.java2nb.books.domain.BookContentDO;
import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.util.StringUtil;
import com.java2nb.books.vo.BookIndexVO;
import com.java2nb.common.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.java2nb.books.dao.BookDao;
import com.java2nb.books.domain.BookDO;
import com.java2nb.books.service.BookService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;

	@Autowired
	private BookIndexDao bookIndexDao;

	@Autowired
	private BookContentDao bookContentDao;

	
	@Override
	public BookDO get(Long id){
		return bookDao.get(id);
	}
	
	@Override
	public List<BookDO> list(Map<String, Object> map){
		String sort = (String) map.get("sort");
		if(StringUtils.isNotBlank(sort)){
			map.put("sort",StringUtil.humpToLine(sort));

		}
		return bookDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return bookDao.count(map);
	}
	
	@Override
	public int save(BookDO book){
		book.setVisitCount(0l);
		if(book.getUpdateTime() == null){
			book.setUpdateTime(new Date());
		}
		return bookDao.save(book);
	}
	
	@Override
	public int update(BookDO book){

		return bookDao.update(book);
	}

	@Transactional
	@Override
	public int remove(Long id){
		int rows = bookDao.remove(id);
		bookIndexDao.removeByBookIds(id+"");
		bookContentDao.removeByBookIds(id+"");
		return rows;
	}

	@Transactional
	@Override
	public int batchRemove(Long[] ids){

		 int rows = bookDao.batchRemove(ids);
		 String bookIds = StringUtils.join(ids,",");
		bookIndexDao.removeByBookIds(bookIds);
		bookContentDao.removeByBookIds(bookIds);
		return rows;

	}

	@Override
	@Transactional
	public int saveIndexAndContent(BookIndexDO bookIndex, BookContentDO bookContent) {
		Integer maxBookNum = bookIndexDao.queryMaxIndexNum(bookIndex.getBookId());
		int nextIndexNum = 0;
		if(maxBookNum != null){
			nextIndexNum = maxBookNum + 1;
		}
		bookIndex.setIndexNum(nextIndexNum);
		bookContent.setBookId(bookIndex.getBookId());
		bookContent.setIndexNum(nextIndexNum);
		bookDao.uptUpdateTime(bookIndex.getBookId(),new Date());

		bookIndexDao.save(bookIndex);
		bookContentDao.save(bookContent);
		return 1;
	}

	@Override
	public List<BookIndexVO> indexVOList(Query query) {
		return bookIndexDao.listVO(query);
	}

	@Override
	public int indexVOCount(Query query) {
		return bookIndexDao.countVO(query);
	}

	@Override
	@Transactional
	public int indexRemove(Long id, Long bookId) {
		bookContentDao.removeByBookIds(id+"");
		return bookIndexDao.remove(id);
	}

	@Transactional
	@Override
	public int batchIndexRemove(Long[] ids, Long[] bookIds) {
		bookContentDao.removeByBookIds(StringUtils.join(ids,","));
		return bookIndexDao.batchRemove(ids);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveBookAndIndexAndContent(BookDO book, List<BookIndexDO> bookIndex, List<BookContentDO> bookContent) {
		Long bookId = -1L;
		book.setBookName(book.getBookName().trim());
		book.setAuthor(book.getAuthor().trim());
		Map<String, Object> bookExample = new HashMap<>();
		bookExample.put("bookName", book.getBookName());
		bookExample.put("author", book.getAuthor());
		List<BookDO> books = bookDao.list(bookExample);
		if (books.size() > 0) {
			//更新
			bookId = books.get(0).getId();
			book.setId(bookId);
			bookDao.update(book);

		} else {
			if (book.getVisitCount() == null) {
				Long visitCount = generateVisiteCount(book.getScore());
				book.setVisitCount(visitCount);
			}
			//插入
			int rows = bookDao.save(book);
			if (rows > 0) {
				bookId = book.getId();
			}


		}

		if (bookId >= 0) {


			List<BookIndexDO> newBookIndexList = new ArrayList<>();
			List<BookContentDO> newContentList = new ArrayList<>();
			for (int i = 0; i < bookIndex.size(); i++) {
				BookContentDO bookContentItem = bookContent.get(i);
				if (!bookContentItem.getContent().contains("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新")) {


					BookIndexDO bookIndexItem = bookIndex.get(i);
					bookIndexItem.setBookId(bookId);
					bookContentItem.setBookId(bookId);
					bookContentItem.setIndexNum(bookIndexItem.getIndexNum());

					newBookIndexList.add(bookIndexItem);
					newContentList.add(bookContentItem);
				}
			}

			if (newBookIndexList.size() > 0) {
				bookIndexDao.insertBatch(newBookIndexList);

				bookContentDao.insertBatch(newContentList);
			}


		}


	}

	private Long generateVisiteCount(Float score) {
		return Long.parseLong((int)(score*10000) + new Random().nextInt(1000) + "");
	}

}
