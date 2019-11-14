package com.java2nb.books.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.java2nb.books.dao.BookIndexDao;
import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.service.BookIndexService;



@Service
public class BookIndexServiceImpl implements BookIndexService {
	@Autowired
	private BookIndexDao bookIndexDao;
	
	@Override
	public BookIndexDO get(Long id){
		return bookIndexDao.get(id);
	}
	
	@Override
	public List<BookIndexDO> list(Map<String, Object> map){
		return bookIndexDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return bookIndexDao.count(map);
	}
	
	@Override
	public int save(BookIndexDO bookIndex){
		return bookIndexDao.save(bookIndex);
	}
	
	@Override
	public int update(BookIndexDO bookIndex){
		return bookIndexDao.update(bookIndex);
	}
	
	@Override
	public int remove(Long id){
		return bookIndexDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return bookIndexDao.batchRemove(ids);
	}
	
}
