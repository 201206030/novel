package xyz.zinglizingli.books.web;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.zinglizingli.books.core.constant.CacheKeyConstans;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.common.cache.CommonCacheUtil;
import xyz.zinglizingli.books.core.config.IndexRecBooksConfig;

import java.util.List;
import java.util.Map;

/**
 * 首页controller
 * @author XXY
 */
@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexController {


    private final BookService bookService;

    private final CommonCacheUtil commonCacheUtil;

    private final IndexRecBooksConfig indexRecBooksConfig;




    @RequestMapping(value = {"/index.html","/","/books","/book","/book/index.html"})
    public String index(ModelMap modelMap){
        List<Book> recBooks = (List<Book>) commonCacheUtil.getObject(CacheKeyConstans.REC_BOOK_LIST_KEY);
        if (!indexRecBooksConfig.isRead() || recBooks == null) {
            List<Map<String,String>> configMap = indexRecBooksConfig.getRecBooks();
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
            commonCacheUtil.setObject(CacheKeyConstans.NEWST_BOOK_LIST_KEY, newBooks, 60 * 30);
        }
        modelMap.put("recBooks", recBooks);
        modelMap.put("hotBooks", hotBooks);
        modelMap.put("newBooks", newBooks);

        return "books/index";
    }
}
