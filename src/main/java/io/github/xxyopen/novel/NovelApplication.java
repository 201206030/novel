package io.github.xxyopen.novel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.github.xxyopen.novel.dao.mapper")
public class NovelApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovelApplication.class, args);
	}

}
