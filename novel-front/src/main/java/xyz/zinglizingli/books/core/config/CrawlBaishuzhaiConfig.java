package xyz.zinglizingli.books.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import xyz.zinglizingli.books.core.crawl.BaseHtmlCrawlSource;
import xyz.zinglizingli.books.core.crawl.BiquCrawlSource;

/**
 * 爬虫源配置类
 * @author 11797
 */
@Slf4j
@Configuration
public class CrawlBaishuzhaiConfig {


    /**
     * 必须加此@Primary注解，不然报错，表示有多个爬虫源配置类被加载时默认加载此配置类
     * 下一个爬虫源配置类则不需要添加
     * */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "baishuzhai.crawlsource")
    @ConditionalOnProperty(prefix = "crawl.website",name = "type",havingValue = "4")
    public BaseHtmlCrawlSource dingdianCrawlSource() {
        return new BiquCrawlSource();
    }


}
