package com.java2nb.books.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.java2nb.books.dao.BookContentDao;
import com.java2nb.books.domain.BookContentDO;
import com.java2nb.books.service.BookContentService;



@Service
public class BookContentServiceImpl implements BookContentService {
	@Autowired
	private BookContentDao bookContentDao;
	
	@Override
	public BookContentDO get(Long id){
		return bookContentDao.get(id);
	}
	
	@Override
	public List<BookContentDO> list(Map<String, Object> map){
		return bookContentDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return bookContentDao.count(map);
	}
	
	@Override
	public int save(BookContentDO bookContent){
		return bookContentDao.save(bookContent);
	}
	
	@Override
	public int update(BookContentDO bookContent){
		return bookContentDao.update(bookContent);
	}
	
	@Override
	public int remove(Long id){
		return bookContentDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return bookContentDao.batchRemove(ids);
	}
	
}
