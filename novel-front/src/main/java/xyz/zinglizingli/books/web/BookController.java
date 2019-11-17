package xyz.zinglizingli.books.web;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.po.BookContent;
import xyz.zinglizingli.books.po.BookIndex;
import xyz.zinglizingli.books.po.ScreenBullet;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.books.vo.BookVO;
import xyz.zinglizingli.common.cache.CommonCacheUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("book")
public class BookController {


    @Autowired
    private BookService bookService;

    @Autowired
    private CommonCacheUtil commonCacheUtil;



    @RequestMapping("search")
    public String search(@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                         @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "catId", required = false) Integer catId,
                         @RequestParam(value = "historyBookIds", required = false) String ids,
                         @RequestParam(value = "bookStatus", required = false) String bookStatus,
                         @RequestParam(value = "token", required = false) String token,
                         @RequestParam(value = "sortBy", defaultValue = "update_time") String sortBy, @RequestParam(value = "sort", defaultValue = "DESC") String sort,
                         HttpServletRequest req, HttpServletResponse resp, ModelMap modelMap) {

        String userId = null;
        String titleType = "最近更新";
        if (catId != null) {
            titleType = bookService.getCatNameById(catId) + "分类频道";
            ;
        } else if ("score".equals(sortBy)) {
            titleType = "小说排行";
        } else if (ids != null) {
            titleType = "阅读记录";
        } else if (token != null) {
            userId = commonCacheUtil.get(token);
            titleType = "我的书架";
        } else if (bookStatus != null && bookStatus.contains("完成")) {
            titleType = "完本小说";
        } else if (keyword != null) {
            titleType = "搜索";
        }
        modelMap.put("titleType", titleType);
        List<Book> books;
        List<BookVO> bookVOList;
        if (StringUtils.isEmpty(ids) || !StringUtils.isEmpty(keyword)) {
            books = bookService.search(page, pageSize, userId, ids, keyword, bookStatus, catId, null, null, sortBy, sort);
            bookVOList = new ArrayList<>();
            for (Book book : books) {
                BookVO bookvo = new BookVO();
                BeanUtils.copyProperties(book, bookvo);
                bookvo.setCateName(bookService.getCatNameById(bookvo.getCatid()));
                bookVOList.add(bookvo);
            }

        } else {
            if (!ids.contains("-")) {
                books = bookService.search(page, 50, userId, ids, keyword, null, catId, null, null, sortBy, sort);
                List<String> idsArr = Arrays.asList(ids.split(","));
                int length = idsArr.size();
                BookVO[] bookVOArr = new BookVO[books.size()];
                for (Book book : books) {
                    int index = idsArr.indexOf(book.getId() + "");
                    BookVO bookvo = new BookVO();
                    BeanUtils.copyProperties(book, bookvo);
                    bookvo.setCateName(bookService.getCatNameById(bookvo.getCatid()));
                    bookVOArr[books.size() - index - 1] = bookvo;
                }
                bookVOList = Arrays.asList(bookVOArr);
            } else {
                books = new ArrayList<>();
                bookVOList = new ArrayList<>();
            }

        }

        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        modelMap.put("limit", bookPageInfo.getPageSize());
        modelMap.put("curr", bookPageInfo.getPageNum());
        modelMap.put("total", bookPageInfo.getTotal());
        modelMap.put("books", bookVOList);
        modelMap.put("ids", ids);
        modelMap.put("token", token);
        modelMap.put("bookStatus", bookStatus);
        modelMap.put("keyword", keyword);
        modelMap.put("catId", catId);
        modelMap.put("sortBy", sortBy);
        modelMap.put("sort", sort);
        return "books/book_search";
    }


    @RequestMapping("searchSoftBook.html")
    public String searchSoftBook(@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                                 @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "catId", defaultValue = "8") Integer catId,
                                 @RequestParam(value = "softCat", required = false) Integer softCat,
                                 @RequestParam(value = "bookStatus", required = false) String bookStatus,
                                 @RequestParam(value = "softTag", required = false) String softTag,
                                 @RequestParam(value = "sortBy", defaultValue = "update_time") String sortBy, @RequestParam(value = "sort", defaultValue = "DESC") String sort,
                                 HttpServletRequest req, HttpServletResponse resp, ModelMap modelMap) {

        String userId = null;
        List<Book> books = bookService.search(page, pageSize, userId, null, keyword, bookStatus, catId, softCat, softTag, sortBy, sort);
        List<BookVO> bookVOList;
        bookVOList = new ArrayList<>();
        for (Book book : books) {
            BookVO bookvo = new BookVO();
            BeanUtils.copyProperties(book, bookvo);
            bookvo.setCateName(bookService.getSoftCatNameById(bookvo.getSoftCat()));
            bookVOList.add(bookvo);
        }


        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        modelMap.put("limit", bookPageInfo.getPageSize());
        modelMap.put("curr", bookPageInfo.getPageNum());
        modelMap.put("total", bookPageInfo.getTotal());
        modelMap.put("books", bookVOList);
        modelMap.put("keyword", keyword);
        modelMap.put("bookStatus", bookStatus);
        modelMap.put("softCat", softCat);
        modelMap.put("softTag", softTag);
        modelMap.put("sortBy", sortBy);
        modelMap.put("sort", sort);
        return "books/soft_book_search";
    }

    @RequestMapping("searchMhBook.html")
    public String searchMhBook(@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                                 @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "catId", defaultValue = "9") Integer catId,
                                 @RequestParam(value = "softCat", required = false) Integer softCat,
                                 @RequestParam(value = "bookStatus", required = false) String bookStatus,
                                 @RequestParam(value = "softTag", required = false) String softTag,
                                 @RequestParam(value = "sortBy", defaultValue = "update_time") String sortBy, @RequestParam(value = "sort", defaultValue = "DESC") String sort,
                                 HttpServletRequest req, HttpServletResponse resp, ModelMap modelMap) {

        String userId = null;
        List<Book> books = bookService.search(page, pageSize, userId, null, keyword, bookStatus, catId, softCat, softTag, sortBy, sort);
        List<BookVO> bookVOList;
        bookVOList = new ArrayList<>();
        for (Book book : books) {
            BookVO bookvo = new BookVO();
            BeanUtils.copyProperties(book, bookvo);
            bookvo.setCateName(bookService.getMhCatNameById(bookvo.getSoftCat()));
            bookVOList.add(bookvo);
        }


        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        modelMap.put("limit", bookPageInfo.getPageSize());
        modelMap.put("curr", bookPageInfo.getPageNum());
        modelMap.put("total", bookPageInfo.getTotal());
        modelMap.put("books", bookVOList);
        modelMap.put("keyword", keyword);
        modelMap.put("bookStatus", bookStatus);
        modelMap.put("softCat", softCat);
        modelMap.put("softTag", softTag);
        modelMap.put("sortBy", sortBy);
        modelMap.put("sort", sort);
        return "books/mh_book_search";
    }

    @RequestMapping("{bookId}.html")
    public String detail(@PathVariable("bookId") Long bookId, ModelMap modelMap) {
        //查询基本信息
        Book book = bookService.queryBaseInfo(bookId);
        //查询最新目录信息
        List<BookIndex> indexList = bookService.queryNewIndexList(bookId);

        int minIndexNum = 0;
        //查询最小目录号
        List<Integer> integers = bookService.queryMaxAndMinIndexNum(bookId);
        if (integers.size() > 1) {
            minIndexNum = integers.get(1);
        }


        BookVO bookvo = new BookVO();
        BeanUtils.copyProperties(book, bookvo);
        bookvo.setCateName(bookService.getCatNameById(bookvo.getCatid()));

        modelMap.put("bookId", bookId);
        modelMap.put("book", bookvo);
        modelMap.put("minIndexNum", minIndexNum);
        modelMap.put("indexList", indexList);
        if (indexList != null && indexList.size() > 0) {
            modelMap.put("lastIndexName", indexList.get(0).getIndexName());
            modelMap.put("lastIndexNum", indexList.get(0).getIndexNum());
        }
        return "books/book_detail";
    }

    @RequestMapping("{bookId}/index.html")
    public String bookIndex(@PathVariable("bookId") Long bookId, ModelMap modelMap) {
        List<BookIndex> indexList = bookService.queryAllIndexList(bookId);
        String bookName = bookService.queryBaseInfo(bookId).getBookName();
        modelMap.put("indexList", indexList);
        modelMap.put("bookName", bookName);
        modelMap.put("bookId", bookId);
        return "books/book_index";
    }

    @RequestMapping("{bookId}/{indexNum}.html")
    public String bookContent(@PathVariable("bookId") Long bookId, @PathVariable("indexNum") Integer indexNum, ModelMap modelMap) {
        BookContent bookContent = bookService.queryBookContent(bookId, indexNum);
        String indexName;
        if (bookContent == null) {
            bookContent = new BookContent();
            bookContent.setId(-1l);
            bookContent.setBookId(bookId);
            bookContent.setIndexNum(indexNum);
            bookContent.setContent("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新");
            indexName = "更新中。。。";
        } else {
            indexName = bookService.queryIndexNameByBookIdAndIndexNum(bookId, indexNum);
        }
        List<Integer> preAndNextIndexNum = bookService.queryPreAndNextIndexNum(bookId, indexNum);
        modelMap.put("nextIndexNum", preAndNextIndexNum.get(0));
        modelMap.put("preIndexNum", preAndNextIndexNum.get(1));
        modelMap.put("bookContent", bookContent);
        modelMap.put("indexName", indexName);
        String bookName = bookService.queryBaseInfo(bookId).getBookName();
        modelMap.put("bookName", bookName);
        return "books/book_content";
    }


    @RequestMapping("addVisit")
    @ResponseBody
    public String addVisit(@RequestParam("bookId") Long bookId) {

        bookService.addVisitCount(bookId);

        return "ok";
    }

    @RequestMapping("sendBullet")
    @ResponseBody
    public Map<String, Object> sendBullet(@RequestParam("contentId") Long contentId, @RequestParam("bullet") String bullet) {
        Map<String, Object> result = new HashMap<>();
        bookService.sendBullet(contentId, bullet);
        result.put("code", 1);
        result.put("desc", "ok");
        return result;
    }

    @RequestMapping("queryIsDownloading")
    @ResponseBody
    public Map<String, Object> queryIsDownloading(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (session.getAttribute("isDownloading") != null) {
            result.put("code", 1);
        } else {
            result.put("code", 0);
        }
        return result;
    }


    @RequestMapping("queryBullet")
    @ResponseBody
    public List<ScreenBullet> queryBullet(@RequestParam("contentId") Long contentId) {

        return bookService.queryBullet(contentId);
    }


    /**
     * 文件下载
     */
    @RequestMapping(value = "/download")
    public void download(@RequestParam("bookId") Long bookId, @RequestParam("bookName") String bookName, HttpServletResponse resp, HttpSession session) {
        try {
            session.setAttribute("isDownloading", 1);
            int count = bookService.countIndex(bookId);


            OutputStream out = resp.getOutputStream();
            //设置响应头，对文件进行url编码
            bookName = URLEncoder.encode(bookName, "UTF-8");
            resp.setContentType("application/octet-stream");//解决手机端不能下载附件的问题
            resp.setHeader("Content-Disposition", "attachment;filename=" + bookName + ".txt");
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    String index = bookService.queryIndexList(bookId, i);
                    String content = bookService.queryContentList(bookId, i);
                    out.write(index.getBytes("utf-8"));
                    out.write("\n".getBytes("utf-8"));
                    content = content.replaceAll("<br\\s*/*>", "\r\n");
                    content = content.replaceAll("&nbsp;", " ");
                    content = content.replaceAll("<a[^>]*>", "");
                    content = content.replaceAll("</a>", "");
                    content = content.replaceAll("<div[^>]*>", "");
                    content = content.replaceAll("</div>", "");
                    content = content.replaceAll("<p[^>]*>[^<]*<a[^>]*>[^<]*</a>\\s*</p>", "");
                    content = content.replaceAll("<p[^>]*>", "");
                    content = content.replaceAll("</p>", "\r\n");
                    out.write(content.getBytes("utf-8"));
                    out.write("\r\n".getBytes("utf-8"));
                    out.write("\r\n".getBytes("utf-8"));
                    out.flush();
                }

            }

            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.removeAttribute("isDownloading");
        }

    }


}