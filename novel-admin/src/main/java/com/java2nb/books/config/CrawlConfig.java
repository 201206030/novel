package com.java2nb.books.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix="crawl")
public class CrawlConfig {

    private Integer threadCount;
    private Integer priority;
    private Float lowestScore;
    private String minUptTime;
    private Integer maxNumber;
}
