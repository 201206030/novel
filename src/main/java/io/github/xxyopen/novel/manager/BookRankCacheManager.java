package io.github.xxyopen.novel.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.xxyopen.novel.core.constant.CacheConsts;
import io.github.xxyopen.novel.dao.entity.BookInfo;
import io.github.xxyopen.novel.dao.mapper.BookInfoMapper;
import io.github.xxyopen.novel.dto.resp.BookRankRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 小说排行榜 缓存管理类
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
@Component
@RequiredArgsConstructor
public class BookRankCacheManager {

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询小说点击榜列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER
            , value = CacheConsts.BOOK_VISIT_RANK_CACHE_NAME)
    public List<BookRankRespDto> listVisitRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper
                .orderByDesc("visit_count");
        return getBookRankRespDtos(bookInfoQueryWrapper);
    }

    /**
     * 查询小说新书榜列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER
            , value = CacheConsts.BOOK_NEWEST_RANK_CACHE_NAME)
    public List<BookRankRespDto> listNewestRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper
                .orderByDesc("create_time");
        return getBookRankRespDtos(bookInfoQueryWrapper);
    }

    /**
     * 查询小说更新榜列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER
            , value = CacheConsts.BOOK_UPDATE_RANK_CACHE_NAME)
    public List<BookRankRespDto> listUpdateRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper
                .orderByDesc("update_time");
        return getBookRankRespDtos(bookInfoQueryWrapper);
    }

    private List<BookRankRespDto> getBookRankRespDtos(QueryWrapper<BookInfo> bookInfoQueryWrapper) {
        bookInfoQueryWrapper.last("limit 30");
        return bookInfoMapper.selectList(bookInfoQueryWrapper).stream().map(v -> {
            BookRankRespDto respDto = new BookRankRespDto();
            respDto.setId(v.getId());
            respDto.setCategoryId(v.getCategoryId());
            respDto.setCategoryName(v.getCategoryName());
            respDto.setBookName(v.getBookName());
            respDto.setAuthorName(v.getAuthorName());
            respDto.setPicUrl(v.getPicUrl());
            respDto.setBookDesc(v.getBookDesc());
            respDto.setLastChapterName(v.getLastChapterName());
            respDto.setLastChapterUpdateTime(v.getLastChapterUpdateTime());
            respDto.setWordCount(v.getWordCount());
            return respDto;
        }).toList();
    }

}
