package xyz.zinglizingli.books.core.schedule;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.books.core.enums.PicSaveType;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.common.utils.FileUtil;

import java.util.List;

/**
 * 定时转换网络图片为本地图片
 *
 * @author 11797
 */
@ConditionalOnProperty(prefix = "pic.save",name = "type",havingValue = "2")
@Service
@RequiredArgsConstructor
@Slf4j
public class Network2LocalPicSchedule {

    private final BookService bookService;

    @Value("${pic.save.type}")
    private Integer picSaveType;

    @Value("${pic.save.path}")
    private String picSavePath;

    /**
     * 10分钟转一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void trans() {

        log.info("Network2LocalPicSchedule。。。。。。。。。。。。");


        Integer offset = 0, limit = 100;
        List<Book> networkPicBooks;
        do {
            networkPicBooks = bookService.queryNetworkPicBooks(limit, offset);
            for (Book book : networkPicBooks) {
                bookService.updateBook(book, book.getId());
            }
            offset += limit;
        } while (networkPicBooks.size() > 0);


    }
}
