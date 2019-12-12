package xyz.zinglizingli.common.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.common.crawl.BaseCrawlSource;
import xyz.zinglizingli.common.utils.RestTemplateUtil;

/**
 * 更新书籍章节内容定时任务
 *
 * @author 11797
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlBooksSchedule {


    private final BaseCrawlSource crawlSource;


    /**
     * 10分钟抓取一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void crawBooks() {

        log.debug("crawlBooksSchedule执行中。。。。。。。。。。。。");

        crawlSource.parse();


    }



}
