package xyz.zinglizingli.books.core.schedule;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.books.core.crawl.BaseCrawlSource;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;

import java.util.List;

/**
 * 定时更新
 *
 * @author 11797
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateBookSchedule {

    private final BaseCrawlSource crawlSource;


    /**
     * 10分钟更新一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void updateBook() {

        log.info("UpdateBookSchedule。。。。。。。。。。。。");

        crawlSource.update();
    }
}
