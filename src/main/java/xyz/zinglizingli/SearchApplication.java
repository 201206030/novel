package xyz.zinglizingli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan({"xyz.zinglizingli.*.mapper"})
public class SearchApplication {

    public static void main(String[] args) {


        SpringApplication.run(SearchApplication.class, args);
    }

    /**
     * 解决同一时间只能一个定时任务执行的问题
     * */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }

}
