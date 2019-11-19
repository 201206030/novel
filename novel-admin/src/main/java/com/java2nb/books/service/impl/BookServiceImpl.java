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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public int remove(Long id){
		return bookDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return bookDao.batchRemove(ids);
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
	public int indexRemove(Long id) {
		return bookIndexDao.remove(id);
	}

	@Override
	public int batchIndexRemove(Long[] ids) {
		return bookIndexDao.batchRemove(ids);
	}

}
