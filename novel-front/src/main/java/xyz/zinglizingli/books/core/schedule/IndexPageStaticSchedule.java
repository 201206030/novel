package xyz.zinglizingli.books.core.schedule;


import ch.qos.logback.core.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.expression.Maps;
import xyz.zinglizingli.books.core.config.IndexRecBooksConfig;
import xyz.zinglizingli.books.core.config.SeoConfig;
import xyz.zinglizingli.books.core.constant.CacheKeyConstans;
import xyz.zinglizingli.books.core.utils.Constants;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.common.cache.CommonCacheUtil;

import javax.servlet.ServletContext;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 首页静态化定时任务
 *
 * @author 11797
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IndexPageStaticSchedule {


    @Value("${index.template}")
    private String indexTemplate;

    @Value("${index.pageLocation}")
    private String pageLocation;


    private final BookService bookService;

    private final CommonCacheUtil commonCacheUtil;

    private final IndexRecBooksConfig indexRecBooksConfig;

    private final TemplateEngine templateEngine;

    private final SeoConfig seoConfig;


    /**
     * 10分钟保存一次
     */
    @SneakyThrows
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void generate() {

        Map<String, Object> dataMap = new HashMap<>();

        List<Book> recBooks = (List<Book>) commonCacheUtil.getObject(CacheKeyConstans.REC_BOOK_LIST_KEY);
        if (!indexRecBooksConfig.isRead() || recBooks == null) {
            List<Map<String, String>> configMap = indexRecBooksConfig.getRecBooks();
            //查询推荐书籍数据
            recBooks = bookService.queryRecBooks(configMap);
            commonCacheUtil.setObject(CacheKeyConstans.REC_BOOK_LIST_KEY, recBooks, 60 * 60 * 24 * 10);
            indexRecBooksConfig.setRead(true);
        }


        List<Book> hotBooks = (List<Book>) commonCacheUtil.getObject(CacheKeyConstans.HOT_BOOK_LIST_KEY);
        if (hotBooks == null) {
            //查询热点数据
            hotBooks = bookService.search(1, 9, null, null, null, null, null, null, null, "visit_count DESC,score ", "DESC");
            commonCacheUtil.setObject(CacheKeyConstans.HOT_BOOK_LIST_KEY, hotBooks, 60 * 60 * 24);
        }

        List<Book> newBooks = (List<Book>) commonCacheUtil.getObject(CacheKeyConstans.NEWST_BOOK_LIST_KEY);
        if (newBooks == null) {
            //查询最近更新数据
            newBooks = bookService.search(1, 20, null, null, null, null, null, null, null, "update_time", "DESC");
            commonCacheUtil.setObject(CacheKeyConstans.NEWST_BOOK_LIST_KEY, newBooks, 60 * 10);
        }

        dataMap.put("recBooks", recBooks);
        dataMap.put("hotBooks", hotBooks);
        dataMap.put("newBooks", newBooks);
        Map<String, Object> application = new HashMap<>();
        application.put(Constants.SEO_CONFIG_KEY,seoConfig);
        application.put("noLazy", "1");
        dataMap.put("application",application);

        Context ctx = new Context(Locale.CHINA, dataMap);


        BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(pageLocation+"/index.html"),"UTF-8"));

        //清空原文件内容
        writer.write("");
        writer.write(templateEngine.process("books/index_" + indexTemplate, ctx));
        writer.flush();
        writer.close();

    }
}
