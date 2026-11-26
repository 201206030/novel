package io.github.xxyopen.novel.dao.mapper;

import io.github.xxyopen.novel.dao.entity.BookChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 小说章节 Mapper 接口
 * </p>
 *
 * @author xiongxiaoyang
 * @date 2022/05/11
 */
public interface BookChapterMapper extends BaseMapper<BookChapter> {

    /**
     * 根据数据库中的章节重新计算作品总字数。
     *
     * @param bookId 作品ID
     * @return 章节总字数；作品没有章节时返回0
     */
    @Select("SELECT COALESCE(SUM(word_count), 0) FROM book_chapter WHERE book_id = #{bookId}")
    Integer selectTotalWordCountByBookId(@Param("bookId") Long bookId);

}
