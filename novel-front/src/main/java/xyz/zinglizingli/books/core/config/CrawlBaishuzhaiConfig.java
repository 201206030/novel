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
 * @author 11797
 */
@Slf4j
@Configuration
public class CrawlBaishuzhaiConfig {


    @Bean
    @Primary //必须加此注解，不然报错，下一个类则不需要添加
    @ConfigurationProperties(prefix = "baishuzhai.crawlsource") // prefix值必须是application.yml中对应属性的前缀
    @ConditionalOnProperty(prefix = "crawl.website",name = "type",havingValue = "4")
    public BaseHtmlCrawlSource dingdianCrawlSource() {
        return new BiquCrawlSource();
    }


}
