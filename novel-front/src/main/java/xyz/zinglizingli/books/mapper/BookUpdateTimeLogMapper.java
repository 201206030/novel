package xyz.zinglizingli.books.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.zinglizingli.books.po.BookUpdateTimeLog;
import xyz.zinglizingli.books.po.BookUpdateTimeLogExample;

public interface BookUpdateTimeLogMapper {
    int countByExample(BookUpdateTimeLogExample example);

    int deleteByExample(BookUpdateTimeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookUpdateTimeLog record);

    int insertSelective(BookUpdateTimeLog record);

    List<BookUpdateTimeLog> selectByExample(BookUpdateTimeLogExample example);

    BookUpdateTimeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookUpdateTimeLog record, @Param("example") BookUpdateTimeLogExample example);

    int updateByExample(@Param("record") BookUpdateTimeLog record, @Param("example") BookUpdateTimeLogExample example);

    int updateByPrimaryKeySelective(BookUpdateTimeLog record);

    int updateByPrimaryKey(BookUpdateTimeLog record);
}