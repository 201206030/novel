package io.github.xxyopen.novel.service;

import io.github.xxyopen.novel.core.common.req.PageReqDto;
import io.github.xxyopen.novel.core.common.resp.PageRespDto;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.req.BookAddReqDto;
import io.github.xxyopen.novel.dto.req.ChapterAddReqDto;
import io.github.xxyopen.novel.dto.req.UserCommentReqDto;
import io.github.xxyopen.novel.dto.resp.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 小说模块 服务类
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
public interface BookService {

    /**
     * 小说点击榜查询
     *
     * @return 小说点击排行列表
     */
    RestResp<List<BookRankRespDto>> listVisitRankBooks();

    /**
     * 小说新书榜查询
     *
     * @return 小说新书排行列表
     */
    RestResp<List<BookRankRespDto>> listNewestRankBooks();

    /**
     * 小说更新榜查询
     *
     * @return 小说更新排行列表
     */
    RestResp<List<BookRankRespDto>> listUpdateRankBooks();

    /**
     * 小说信息查询
     *
     * @param bookId 小说ID
     * @return 小说信息
     */
    RestResp<BookInfoRespDto> getBookById(Long bookId);

    /**
     * 小说内容相关信息查询
     *
     * @param chapterId 章节ID
     * @return 内容相关联的信息
     */
    RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId);

    /**
     * 小说最新章节相关信息查询
     *
     * @param bookId 小说ID
     * @return 章节相关联的信息
     */
    RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId);

    /**
     * 小说推荐列表查询
     *
     * @param bookId 小说ID
     * @return 小说信息列表
     */
    RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException;

    /**
     * 增加小说点击量
     *
     * @param bookId 小说ID
     * @return 成功状态
     */
    RestResp<Void> addVisitCount(Long bookId);

    /**
     * 获取上一章节ID
     *
     * @param chapterId 章节ID
     * @return 上一章节ID
     */
    RestResp<Long> getPreChapterId(Long chapterId);

    /**
     * 获取下一章节ID
     *
     * @param chapterId 章节ID
     * @return 下一章节ID
     */
    RestResp<Long> getNextChapterId(Long chapterId);

    /**
     * 小说章节列表查询
     *
     * @param bookId 小说ID
     * @return 小说章节列表
     */
    RestResp<List<BookChapterRespDto>> listChapters(Long bookId);

    /**
     * 小说分类列表查询
     *
     * @param workDirection 作品方向;0-男频 1-女频
     * @return 分类列表
     */
    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);

    /**
     * 发表评论
     *
     * @param dto 评论相关 DTO
     * @return void
     */
    RestResp<Void> saveComment(UserCommentReqDto dto);

    /**
     * 小说最新评论查询
     *
     * @param bookId 小说ID
     * @return 小说最新评论数据
     */
    RestResp<BookCommentRespDto> listNewestComments(Long bookId);

    /**
     * 删除评论
     *
     * @param userId    评论用户ID
     * @param commentId 评论ID
     * @return void
     */
    RestResp<Void> deleteComment(Long userId, Long commentId);

    /**
     * 修改评论
     *
     * @param userId  用户ID
     * @param id      评论ID
     * @param content 修改后的评论内容
     * @return void
     */
    RestResp<Void> updateComment(Long userId, Long id, String content);

    /**
     * 小说信息保存
     *
     * @param dto 小说信息
     * @return void
     */
    RestResp<Void> saveBook(BookAddReqDto dto);

    /**
     * 小说章节信息保存
     *
     * @param dto 章节信息
     * @return void
     */
    RestResp<Void> saveBookChapter(ChapterAddReqDto dto);

    /**
     * 查询作家发布小说列表
     *
     * @param dto 分页请求参数
     * @return 小说分页列表数据
     */
    RestResp<PageRespDto<BookInfoRespDto>> listAuthorBooks(PageReqDto dto);
}
