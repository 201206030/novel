package io.github.xxyopen.novel.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.xxyopen.novel.core.annotation.ValidateSortOrder;
import io.github.xxyopen.novel.dao.entity.BookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.xxyopen.novel.dto.req.BookSearchReqDto;
import io.github.xxyopen.novel.dto.resp.BookInfoRespDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 小说信息 Mapper 接口
 * </p>
 *
 * @author xiongxiaoyang
 * @date 2022/05/11
 */
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    /** 更新章节汇总信息前查询并锁定作品行。
     *
     * @param bookId 作品ID
     * @return 被锁定的作品记录；作品不存在时返回 {@code null}
     */
    @Select("SELECT * FROM book_info WHERE id = #{bookId} FOR UPDATE")
    BookInfo selectByIdForUpdate(@Param("bookId") Long bookId);

    /** 更新章节汇总字段；作品没有章节时也会清空可空字段。 */
    @Update("UPDATE book_info SET word_count = #{bookInfo.wordCount}, "
        + "last_chapter_id = #{bookInfo.lastChapterId}, "
        + "last_chapter_name = #{bookInfo.lastChapterName}, "
        + "last_chapter_update_time = #{bookInfo.lastChapterUpdateTime}, "
        + "update_time = #{bookInfo.updateTime} WHERE id = #{bookInfo.id}")
    int updateChapterMetadata(@Param("bookInfo") BookInfo bookInfo);

    /**
     * 增加小说点击量
     *
     * @param bookId 小说ID
     */
    void addVisitCount(@Param("bookId") Long bookId);

    /**
     * 小说搜索
     * @param page mybatis-plus 分页对象
     * @param condition 搜索条件
     * @return 返回结果
     * */
    List<BookInfo> searchBooks(IPage<BookInfoRespDto> page, @ValidateSortOrder BookSearchReqDto condition);

}
