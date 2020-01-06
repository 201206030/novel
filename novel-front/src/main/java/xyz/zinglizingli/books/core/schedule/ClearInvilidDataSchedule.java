package xyz.zinglizingli.books.core.schedule;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.books.service.BookService;

import java.io.File;

/**
 * 清理数据库中无效数据
 *
 * @author 11797*/
@Service
@RequiredArgsConstructor
@Slf4j
public class ClearInvilidDataSchedule {

    private final BookService bookService;

    @Value("${pic.save.path}")
    private String picSavePath;

    /**
     * 每天凌晨两点清理一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void clear() {

        log.debug("ClearInvilidDataSchedule。。。。。。。。。。。。");

        bookService.clearInvilidData();

        clearInvilidFile(new File(picSavePath));


    }

    private void clearInvilidFile(File directory) {
        for(File file : directory.listFiles()){
            if(file.isDirectory()){
                clearInvilidFile(file);
            }else{
                String fileName = file.getName();
                int count = bookService.countByPicName(fileName);
                if(count == 0){
                    file.deleteOnExit();
                }
            }
        }

    }
}
