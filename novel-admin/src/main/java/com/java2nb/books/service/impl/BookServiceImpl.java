package com.java2nb.books.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.java2nb.books.dao.BookDao;
import com.java2nb.books.domain.BookDO;
import com.java2nb.books.service.BookService;



@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;
	
	@Override
	public BookDO get(Long id){
		return bookDao.get(id);
	}
	
	@Override
	public List<BookDO> list(Map<String, Object> map){
		return bookDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return bookDao.count(map);
	}
	
	@Override
	public int save(BookDO book){
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
	
}
