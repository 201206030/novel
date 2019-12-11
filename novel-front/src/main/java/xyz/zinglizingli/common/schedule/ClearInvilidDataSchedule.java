package xyz.zinglizingli.common.schedule;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.books.service.BookService;

/**
 * 清理数据库中无效数据
 *
 * @author 11797*/
@Service
@RequiredArgsConstructor
@Slf4j
public class ClearInvilidDataSchedule {

    private final BookService bookService;

    /**
     * 每天凌晨两点清理一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void clear() {

        log.debug("ClearInvilidDataSchedule。。。。。。。。。。。。");

        bookService.clearInvilidData();


    }
}
