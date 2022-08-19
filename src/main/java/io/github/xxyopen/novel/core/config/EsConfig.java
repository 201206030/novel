package io.github.xxyopen.novel.core.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elasticsearch 相关配置
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class EsConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

        // And create the API client
        return new ElasticsearchClient(transport);
    }

}
