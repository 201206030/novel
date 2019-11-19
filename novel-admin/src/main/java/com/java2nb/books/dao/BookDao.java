package com.java2nb.books.dao;

import com.java2nb.books.domain.BookDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:27:04
 */
@Mapper
public interface BookDao {

	BookDO get(Long id);
	
	List<BookDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(BookDO book);
	
	int update(BookDO book);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    void uptUpdateTime( @Param("id") Long bookId, @Param("updateTime") Date date);
}
