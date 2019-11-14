package xyz.zinglizingli.books.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.zinglizingli.books.po.ScreenBullet;
import xyz.zinglizingli.books.po.ScreenBulletExample;

import java.util.List;

public interface ScreenBulletMapper {
    int countByExample(ScreenBulletExample example);

    int deleteByExample(ScreenBulletExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ScreenBullet record);

    int insertSelective(ScreenBullet record);

    List<ScreenBullet> selectByExample(ScreenBulletExample example);

    ScreenBullet selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ScreenBullet record, @Param("example") ScreenBulletExample example);

    int updateByExample(@Param("record") ScreenBullet record, @Param("example") ScreenBulletExample example);

    int updateByPrimaryKeySelective(ScreenBullet record);

    int updateByPrimaryKey(ScreenBullet record);
}