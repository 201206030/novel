package io.github.xxyopen.novel;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@SpringBootApplication
@MapperScan("io.github.xxyopen.novel.dao.mapper")
@EnableCaching
@EnableScheduling
@Slf4j
public class NovelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context, DataSource dataSource) {
        return args -> {
            Map<String, CacheManager> beans = context.getBeansOfType(CacheManager.class);
            log.info("加载了如下缓存管理器：");
            beans.forEach((k, v) -> {
                log.info("{}:{}", k, v.getClass().getName());
                log.info("缓存：{}", v.getCacheNames());
            });
            // 提前创建连接池，而不是在第一次访问数据库时才创建，提高第一次访问接口的速度
            log.info("创建连接池...");
            try (Connection connection = dataSource.getConnection()) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                log.info("最小空闲连接数：{}", hikariDataSource.getMinimumIdle());
                log.info("最大连接数：{}", hikariDataSource.getMaximumPoolSize());
                log.info("创建连接池完成.");
                log.info("数据库：{}", connection.getMetaData().getDatabaseProductName());
                log.info("数据库版本：{}", connection.getMetaData().getDatabaseProductVersion());
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(requests -> requests.anyRequest().hasRole("ENDPOINT_ADMIN"));
        http.httpBasic();
        return http.build();
    }

}
