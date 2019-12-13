package xyz.zinglizingli.books.core.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import xyz.zinglizingli.books.core.crawl.BaseCrawlSource;

/**
 * @author 11797
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final BaseCrawlSource crawlSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("程序启动");
        new Thread(()->{
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
