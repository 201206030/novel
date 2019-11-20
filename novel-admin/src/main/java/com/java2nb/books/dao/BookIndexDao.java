package com.java2nb.books.dao;

import com.java2nb.books.domain.BookIndexDO;

import java.util.List;
import java.util.Map;

import com.java2nb.books.vo.BookIndexVO;
import com.java2nb.common.utils.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:28:15
 */
@Mapper
public interface BookIndexDao {

	BookIndexDO get(Long id);
	
	List<BookIndexDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(BookIndexDO bookIndex);
	
	int update(BookIndexDO bookIndex);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

    void insertBatch(List<BookIndexDO> newBookIndexList);

	Integer queryMaxIndexNum(@Param("bookId") Long bookId);

	List<BookIndexVO> listVO(Query query);

	int countVO(Query query);

    void removeByBookIds(@Param("bookIds") String bookIds);
}
