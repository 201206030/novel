package com.java2nb.books.service;

import com.java2nb.books.domain.BookContentDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:28:11
 */
public interface BookContentService {
	
	BookContentDO get(Long id);
	
	List<BookContentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BookContentDO bookContent);
	
	int update(BookContentDO bookContent);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
