package io.github.xxyopen.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.xxyopen.novel.core.common.resp.PageRespDto;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.DatabaseConsts;
import io.github.xxyopen.novel.dao.entity.BookChapter;
import io.github.xxyopen.novel.dao.entity.BookInfo;
import io.github.xxyopen.novel.dao.mapper.BookChapterMapper;
import io.github.xxyopen.novel.dao.mapper.BookInfoMapper;
import io.github.xxyopen.novel.dto.req.BookSearchReqDto;
import io.github.xxyopen.novel.dto.resp.*;
import io.github.xxyopen.novel.manager.*;
import io.github.xxyopen.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * 小说模块 服务实现类
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookCategoryCacheManager bookCategoryCacheManager;

    private final BookRankCacheManager bookRankCacheManager;

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookContentCacheManager bookContentCacheManager;

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    private static final Integer REC_BOOK_COUNT = 4;

    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition) {
        Page<BookInfoRespDto> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        List<BookInfo> bookInfos = bookInfoMapper.searchBooks(page, condition);
        return RestResp.ok(PageRespDto.of(condition.getPageNum(),condition.getPageSize(),page.getTotal()
                ,bookInfos.stream().map(v -> BookInfoRespDto.builder()
                        .id(v.getId())
                        .bookName(v.getBookName())
                        .categoryId(v.getCategoryId())
                        .categoryName(v.getCategoryName())
                        .authorId(v.getAuthorId())
                        .authorName(v.getAuthorName())
                        .wordCount(v.getWordCount())
                        .lastChapterName(v.getLastChapterName())
                        .build()).toList()));
    }

    @Override
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return RestResp.ok(bookRankCacheManager.listVisitRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return RestResp.ok(bookRankCacheManager.listNewestRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return RestResp.ok(bookRankCacheManager.listUpdateRankBooks());
    }

    @Override
    public RestResp<BookInfoRespDto> getBookById(Long bookId) {
        return RestResp.ok(bookInfoCacheManager.getBookInfo(bookId));
    }

    @Override
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId) {
        // 查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookId);

        // 查询最新章节信息
        BookChapterRespDto bookChapter = bookChapterCacheManager.getChapter(bookInfo.getLastChapterId());

        // 查询章节内容
        String content = bookContentCacheManager.getBookContent(bookInfo.getLastChapterId());

        // 查询章节总数
        QueryWrapper<BookChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq(DatabaseConsts.BookChapterTable.ColumnEnum.BOOK_ID.getName(), bookId);
        Long chapterTotal = bookChapterMapper.selectCount(chapterQueryWrapper);

        // 组装数据并返回
        return RestResp.ok(BookChapterAboutRespDto.builder()
                .chapterInfo(bookChapter)
                .chapterTotal(chapterTotal)
                .contentSummary(content.substring(0, 30))
                .build());
    }

    @Override
    public RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException {
        Long categoryId = bookInfoCacheManager.getBookInfo(bookId).getCategoryId();
        List<Long> lastUpdateIdList = bookInfoCacheManager.getLastUpdateIdList(categoryId);
        List<BookInfoRespDto> respDtoList = new ArrayList<>();
        List<Integer> recIdIndexList = new ArrayList<>();
        int count = 0;
        Random rand = SecureRandom.getInstanceStrong();
        while (count < REC_BOOK_COUNT) {
            int recIdIndex = rand.nextInt(lastUpdateIdList.size());
            if (!recIdIndexList.contains(recIdIndex)) {
                recIdIndexList.add(recIdIndex);
                bookId = lastUpdateIdList.get(recIdIndex);
                BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookId);
                respDtoList.add(bookInfo);
                count++;
            }
        }
        return RestResp.ok(respDtoList);
    }

    @Override
    public RestResp<Void> addVisitCount(Long bookId) {
        bookInfoMapper.addVisitCount(bookId);
        return RestResp.ok();
    }

    @Override
    public RestResp<Long> getPreChapterId(Long chapterId) {
        // 查询小说ID 和 章节号
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // 查询上一章信息并返回章节ID
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.ColumnEnum.BOOK_ID.getName(), bookId)
                .lt(DatabaseConsts.BookChapterTable.ColumnEnum.CHAPTER_NUM.getName(), chapterNum)
                .orderByDesc(DatabaseConsts.BookChapterTable.ColumnEnum.CHAPTER_NUM.getName())
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<Long> nextChapterId(Long chapterId) {
        // 查询小说ID 和 章节号
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // 查询下一章信息并返回章节ID
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.ColumnEnum.BOOK_ID.getName(), bookId)
                .gt(DatabaseConsts.BookChapterTable.ColumnEnum.CHAPTER_NUM.getName(), chapterNum)
                .orderByAsc(DatabaseConsts.BookChapterTable.ColumnEnum.CHAPTER_NUM.getName())
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.ColumnEnum.BOOK_ID.getName(), bookId)
                .orderByAsc(DatabaseConsts.BookChapterTable.ColumnEnum.CHAPTER_NUM.getName());
        return RestResp.ok(bookChapterMapper.selectList(queryWrapper).stream().map(v -> BookChapterRespDto.builder()
                .id(v.getId())
                .chapterName(v.getChapterName())
                .build()).toList());
    }

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.ok(bookCategoryCacheManager.listCategory(workDirection));
    }

    @Override
    public RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId) {
        // 查询章节信息
        BookChapterRespDto bookChapter = bookChapterCacheManager.getChapter(chapterId);

        // 查询章节内容
        String content = bookContentCacheManager.getBookContent(chapterId);

        // 查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookChapter.getBookId());

        // 组装数据并返回
        return RestResp.ok(BookContentAboutRespDto.builder()
                .bookInfo(bookInfo)
                .chapterInfo(bookChapter)
                .bookContent(content)
                .build());
    }
}
