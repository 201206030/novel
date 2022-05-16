package io.github.xxyopen.novel.controller.front;

import io.github.xxyopen.novel.core.common.resp.PageRespDto;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.req.BookSearchReqDto;
import io.github.xxyopen.novel.dto.resp.*;
import io.github.xxyopen.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 小说模块 API 接口
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 小说搜索接口
     */
    @GetMapping("search")
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition) {
        return bookService.searchBooks(condition);
    }

    /**
     * 小说信息查询接口
     */
    @GetMapping("{bookId}")
    public RestResp<BookInfoRespDto> getBookById(@PathVariable("bookId") Long bookId) {
        return bookService.getBookById(bookId);
    }

    /**
     * 增加小说点击量接口
     */
    @PostMapping("visit")
    public RestResp<Void> addVisitCount(Long bookId) {
        return bookService.addVisitCount(bookId);
    }

    /**
     * 小说最新章节相关信息查询接口
     */
    @GetMapping("lastChapterAbout")
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId) {
        return bookService.getLastChapterAbout(bookId);
    }

    /**
     * 小说推荐列表查询接口
     */
    @GetMapping("recList")
    public RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException {
        return bookService.listRecBooks(bookId);
    }

    /**
     * 小说章节列表查询接口
     */
    @GetMapping("chapterList")
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        return bookService.listChapters(bookId);
    }

    /**
     * 小说内容相关信息查询接口
     */
    @GetMapping("content/{chapterId}")
    public RestResp<BookContentAboutRespDto> getBookContentAbout(@PathVariable("chapterId") Long chapterId) {
        return bookService.getBookContentAbout(chapterId);
    }

    /**
     * 获取上一章节ID接口
     */
    @GetMapping("preChapterId/{chapterId}")
    public RestResp<Long> getPreChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookService.getPreChapterId(chapterId);
    }

    /**
     * 获取下一章节ID接口
     */
    @GetMapping("nextChapterId/{chapterId}")
    public RestResp<Long> nextChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookService.nextChapterId(chapterId);
    }

    /**
     * 小说点击榜查询接口
     */
    @GetMapping("visitRank")
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return bookService.listVisitRankBooks();
    }

    /**
     * 小说新书榜查询接口
     */
    @GetMapping("newestRank")
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return bookService.listNewestRankBooks();
    }

    /**
     * 小说更新榜查询接口
     */
    @GetMapping("updateRank")
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return bookService.listUpdateRankBooks();
    }

}
