package io.github.xxyopen.novel.core.config;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elasticsearch 相关配置
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@Configuration
public class EsConfig {

    /**
     * 解决 ElasticsearchClientConfigurations 修改默认 ObjectMapper 配置的问题
     */
    @Bean
    JacksonJsonpMapper jacksonJsonpMapper() {
        return new JacksonJsonpMapper();
    }

}
