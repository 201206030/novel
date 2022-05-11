package io.github.xxyopen.novel.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 *
 * @author xiongxiaoyang
 * @date 2022/5/11
 */
public class Generator {

    private static final String USERNAME = System.getenv().get("USER");

    /**
     * 项目信息
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String JAVA_PATH = "/src/main/java";
    private static final String RESOURCE_PATH = "/src/main/resources";
    private static final String BASE_PACKAGE = "io.github.xxyopen.novel";

    /**
     * 数据库信息
     */
    private static final String DATABASE_IP = "127.0.0.1";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_NAME = "novel";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "test123456";

    /**
     * 生成的表名，多个用英文逗号分隔，所有用 all 表示
     */
    private static final String GEN_TABLES = "all";


    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {

        FastAutoGenerator.create(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai", DATABASE_IP, DATABASE_PORT, DATABASE_NAME), DATABASE_USERNAME, DATABASE_PASSWORD)
                .globalConfig(builder -> {
                    builder.author(USERNAME) // 设置作者
                            .fileOverride()
                            // kotlin
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy/MM/dd")
                            .outputDir(PROJECT_PATH + JAVA_PATH); // 指定输出目录

                })
                .packageConfig(builder -> builder.parent(BASE_PACKAGE) // 设置父包名
                        .entity("dao.entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("dao.mapper")
                        .controller("controller.front")
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, PROJECT_PATH + RESOURCE_PATH + "/mapper")))
                .strategyConfig(builder -> builder.addInclude(getTables(GEN_TABLES)) // 设置需要生成的表名
                        .controllerBuilder()
                        .enableRestStyle()
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                ) // 开启生成@RestController 控制器
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
