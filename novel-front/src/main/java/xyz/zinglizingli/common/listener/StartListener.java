package xyz.zinglizingli.common.listener;

import com.sun.javafx.tk.Toolkit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;
import xyz.zinglizingli.common.crawl.BaseCrawlSource;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final BaseCrawlSource crawlSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("程序启动");
        crawlBook();
    }

    private void crawlBook() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.debug("crawlBooks执行中。。。。。。。。。。。。");
                crawlSource.parse();
                crawlBook();
            }
        },1000*60*5);
    }

}
