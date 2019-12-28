package xyz.zinglizingli.books.core.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import xyz.zinglizingli.books.core.crawl.BaseCrawlSource;
import xyz.zinglizingli.books.core.utils.Constants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author 11797
 */
@WebListener
@Slf4j
@RequiredArgsConstructor
public class StartListener implements ServletContextListener {

    private final BaseCrawlSource crawlSource;

    @Value("${crawl.book.new.enabled}")
    private String crawlEnable;

    @Value("${website.name}")
    private String webSiteName;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("websiteName",webSiteName);
        if (!Constants.ENABLE_NEW_BOOK.equals(crawlEnable.trim())) {
            log.info("程序启动");
            new Thread(() -> {
                while (true) {
                    try {

                        log.info("crawlBooks执行中。。。。。。。。。。。。");
                        crawlSource.parse();

                        Thread.sleep(1000 * 60 * 5);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                }
            }).start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
