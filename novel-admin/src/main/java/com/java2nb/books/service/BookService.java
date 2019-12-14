package com.java2nb.books.service;

import com.java2nb.books.domain.BookContentDO;
import com.java2nb.books.domain.BookDO;
import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.vo.BookIndexVO;
import com.java2nb.common.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:27:04
 */
public interface BookService {
	
	BookDO get(Long id);
	
	List<BookDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BookDO book);
	
	int update(BookDO book);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	/**
	 * 保存章节
	 */
    int saveIndexAndContent(BookIndexDO bookIndex, BookContentDO bookContent);


	List<BookIndexVO> indexVOList(Query query);

	int indexVOCount(Query query);

	int indexRemove(Long id, Long bookId);

	int batchIndexRemove(Long[] ids, Long[] bookIds);

	void saveBookAndIndexAndContent(BookDO book, List<BookIndexDO> bookIndex, List<BookContentDO> bookContent);
}
