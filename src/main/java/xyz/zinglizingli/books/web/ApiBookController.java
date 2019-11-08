package xyz.zinglizingli.books.web;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.po.BookContent;
import xyz.zinglizingli.books.po.BookIndex;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.books.vo.BookVO;
import xyz.zinglizingli.search.cache.CommonCacheUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("api/book")
public class ApiBookController {


    @Autowired
    private BookService bookService;

    @Autowired
    private CommonCacheUtil commonCacheUtil;


    @RequestMapping("hotBook")
    public List<Book> hotBooks () {
        //查询热点数据
        List<Book> hotBooks = bookService.search(1, 6, null, null, null, null, null, null, null, "visit_count DESC,score ", "DESC");
        return hotBooks;
    }

    @RequestMapping("newstBook")
    public List<Book> newstBook() {
        //查询最近更新数据
        List<Book> newBooks = bookService.search(1, 6, null, null, null, null, null, null, null, "update_time", "DESC");

        return newBooks;
    }

    @RequestMapping("search")
    public Map<String,Object> search(@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                         @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "bookStatus", required = false) String bookStatus,
                                     @RequestParam(value = "catId", required = false) Integer catId,
                         @RequestParam(value = "historyBookIds", required = false) String ids,
                         @RequestParam(value = "token", required = false) String token,
                         @RequestParam(value = "sortBy", defaultValue = "update_time") String sortBy, @RequestParam(value = "sort", defaultValue = "DESC") String sort,
                         HttpServletRequest req, HttpServletResponse resp) {

        Map<String,Object> modelMap = new HashMap<>();
        String userId = null;
        String titleType = "最近更新";
        if (catId != null) {
            titleType = bookService.getCatNameById(catId);
            ;
        } else if (keyword != null) {
            titleType = "搜索";
        } else if ("score".equals(sortBy)) {
            titleType = "小说排行榜";
        } else if (ids != null) {
            titleType = "阅读记录";
        } else if (token != null) {
            userId = commonCacheUtil.get(token);
            titleType = "我的书架";
        }
        modelMap.put("titleType", titleType);
        List<Book> books = bookService.search(page, pageSize, userId, ids, keyword,bookStatus, catId, null, null, sortBy, sort);
        List<BookVO> bookVOList;
        if (StringUtils.isEmpty(ids)) {
            bookVOList = new ArrayList<>();
            for (Book book : books) {
                BookVO bookvo = new BookVO();
                BeanUtils.copyProperties(book, bookvo);
                bookvo.setCateName(bookService.getCatNameById(bookvo.getCatid()));
                bookVOList.add(bookvo);
            }

        } else {
            if (!ids.contains("-")) {
                List<String> idsArr = Arrays.asList(ids.split(","));
                int length = idsArr.size();
                BookVO[] bookVOArr = new BookVO[length];
                for (Book book : books) {
                    int index = idsArr.indexOf(book.getId() + "");
                    BookVO bookvo = new BookVO();
                    BeanUtils.copyProperties(book, bookvo);
                    bookvo.setCateName(bookService.getCatNameById(bookvo.getCatid()));
                    bookVOArr[length - index - 1] = bookvo;
                }
                bookVOList = Arrays.asList(bookVOArr);
            } else {
                bookVOList = new ArrayList<>();
            }

        }

        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        modelMap.put("limit", bookPageInfo.getPageSize());
        modelMap.put("curr", bookPageInfo.getPageNum());
        modelMap.put("total", bookPageInfo.getTotal());
        modelMap.put("books", bookVOList);
        modelMap.put("ids", ids);
        modelMap.put("keyword", keyword);
        modelMap.put("catId", catId);
        modelMap.put("sortBy", sortBy);
        modelMap.put("sort", sort);
        return modelMap;
    }

    @RequestMapping("{bookId}.html")
    public Map<String,Object> detail(@PathVariable("bookId") Long bookId) {
        Map<String,Object> modelMap = new HashMap<>();
        //查询基本信息
        Book book = bookService.queryBaseInfo(bookId);
        //查询最新目录信息
        List<BookIndex> indexList = bookService.queryNewIndexList(bookId);

        BookVO bookvo = new BookVO();
        BeanUtils.copyProperties(book, bookvo);
        bookvo.setCateName(bookService.getCatNameById(bookvo.getCatid()));
        modelMap.put("bookId", bookId);
        modelMap.put("book", bookvo);
        modelMap.put("indexList", indexList);
        return modelMap;
    }

    @RequestMapping("{bookId}/index.html")
    public Map<String,Object> bookIndex(@PathVariable("bookId") Long bookId) {
        Map<String,Object> modelMap = new HashMap<>();
        List<BookIndex> indexList = bookService.queryAllIndexList(bookId);
        String bookName = bookService.queryBaseInfo(bookId).getBookName();
        modelMap.put("indexList", indexList);
        modelMap.put("bookName", bookName);
        modelMap.put("bookId", bookId);
        return modelMap;
    }

    @RequestMapping("{bookId}/{indexNum}.html")
    public Map<String,Object> bookContent(@PathVariable("bookId") Long bookId, @PathVariable("indexNum") Integer indexNum) {
        Map<String,Object> modelMap = new HashMap<>();
        //查询最大章节号
        List<Integer> maxAndMinIndexNum = bookService.queryMaxAndMinIndexNum(bookId);
        if(maxAndMinIndexNum.size()>0) {
            int maxIndexNum = maxAndMinIndexNum.get(0);
            int minIndexNum = maxAndMinIndexNum.get(1);
            if (indexNum < minIndexNum) {
                indexNum = maxIndexNum;
            }
            if (indexNum > maxIndexNum) {
                indexNum = minIndexNum;
            }
        }
        BookContent bookContent = bookService.queryBookContent(bookId, indexNum);
        String indexName;
        if(bookContent==null) {
            bookContent = new BookContent();
            bookContent.setId(-1l);
            bookContent.setBookId(bookId);
            bookContent.setIndexNum(indexNum);
            bookContent.setContent("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新");
            indexName="？";
        }else{
            indexName = bookService.queryIndexNameByBookIdAndIndexNum(bookId, indexNum);
        }
        modelMap.put("indexName", indexName);
        String bookName = bookService.queryBaseInfo(bookId).getBookName();
        modelMap.put("bookName", bookName);
		modelMap.put("bookContent", bookContent);
        return modelMap;
    }

    @RequestMapping("addVisit")
    public String addVisit(@RequestParam("bookId") Long bookId) {

        bookService.addVisitCount(bookId);

        return "ok";
    }


}
