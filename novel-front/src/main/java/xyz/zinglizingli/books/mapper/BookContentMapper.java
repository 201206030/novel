package xyz.zinglizingli.books.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.zinglizingli.books.po.BookContent;
import xyz.zinglizingli.books.po.BookContentExample;

import java.util.List;

public interface BookContentMapper {
    int countByExample(BookContentExample example);

    int deleteByExample(BookContentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookContent record);

    int insertSelective(BookContent record);

    List<BookContent> selectByExample(BookContentExample example);

    BookContent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookContent record, @Param("example") BookContentExample example);

    int updateByExample(@Param("record") BookContent record, @Param("example") BookContentExample example);

    int updateByPrimaryKeySelective(BookContent record);

    int updateByPrimaryKey(BookContent record);

    void insertBatch(List<BookContent> bookContent);

    /**
     * 清除无效内容
     * */
    void clearInvilidContent();
}