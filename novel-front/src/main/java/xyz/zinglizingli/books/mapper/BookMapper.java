package xyz.zinglizingli.books.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.po.BookExample;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookMapper {
    int countByExample(BookExample example);

    int deleteByExample(BookExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Book record);

    int insertSelective(Book record);

    List<Book> selectByExample(BookExample example);

    Book selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Book record, @Param("example") BookExample example);

    int updateByExample(@Param("record") Book record, @Param("example") BookExample example);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> search(@Param("userId") String userId, @Param("ids") String ids, @Param("keyword") String keyword, @Param("catId") Integer catId, @Param("softCat") Integer softCat,@Param("softTag") String softTag, @Param("bookStatus") String bookStatus);

    void addVisitCount(@Param("bookId") Long bookId);



    /**
     * 查询推荐书籍数据
     * */
    List<Book> queryRecBooks(List<Map<String, String>> configMap);

    /**
     * 清理无效书籍(1个月前更新的无章节书籍)
     * */
    void clearInvilidBook();

    /**
     * 查询网络图片的小说
     *
     * @param limit
     * @param offset*/
    List<Book> queryNetworkPicBooks(@Param("limit") Integer limit,@Param("offset") Integer offset);
}