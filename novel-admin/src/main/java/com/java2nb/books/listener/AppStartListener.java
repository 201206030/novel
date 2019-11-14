package com.java2nb.books.listener;

import com.java2nb.books.dao.BookCrawlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private BookCrawlDao bookCrawlDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        bookCrawlDao.initStatus();

        System.out.println("项目启动成功");
    }
}
