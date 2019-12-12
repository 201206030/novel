package xyz.zinglizingli.common.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import xyz.zinglizingli.common.crawl.BaseCrawlSource;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final BaseCrawlSource crawlSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("程序启动");


        while (true) {
            try {
                log.debug("crawlBooks执行中。。。。。。。。。。。。");
                crawlSource.parse();
                Thread.sleep(1000 * 60 * 5);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }

    }

}
