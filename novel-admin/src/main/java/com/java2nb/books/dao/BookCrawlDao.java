package com.java2nb.books.dao;

import com.java2nb.books.domain.BookCrawlDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-15 03:42:54
 */
@Mapper
public interface BookCrawlDao {

	BookCrawlDO get(Long id);
	
	List<BookCrawlDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(BookCrawlDO bookCrawl);
	
	int update(BookCrawlDO bookCrawl);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    void initStatus();
}
