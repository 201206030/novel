package com.java2nb.books.service;

import com.java2nb.books.domain.BookIndexDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:28:15
 */
public interface BookIndexService {
	
	BookIndexDO get(Long id);
	
	List<BookIndexDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BookIndexDO bookIndex);
	
	int update(BookIndexDO bookIndex);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
