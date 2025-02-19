package io.github.xxyopen.novel.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Ai 相关配置
 *
 * @author xiongxiaoyang
 * @date 2025/2/19
 */
@Configuration
@Slf4j
public class AiConfig {

    /**
     * 目的：配置自定义的 RestClientBuilder 对象
     * <p>
     * 原因：Spring AI 框架的 ChatClient 内部通过 RestClient（Spring Framework 6 和 Spring Boot 3 中引入） 发起 HTTP REST 请求与远程的大模型服务进行通信，
     * 如果项目中没有配置自定义的 RestClientBuilder 对象， 那么在 RestClient 的自动配置类 org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration
     * 中配置的 RestClientBuilder 对象会使用 Spring 容器中提供的 HttpMessageConverters， 由于本项目中配置了 spring.jackson.generator.write-numbers-as-strings
     * = true， 所以 Spring 容器中的 HttpMessageConverters 在 RestClient 发起 HTTP REST 请求转换 Java 对象为 JSON 字符串时会自动将 Number 类型的
     * Java 对象属性转换为字符串而导致请求参数错误
     * <p>
     * 示例："temperature": 0.7 =》"temperature": "0.7"
     * {"code":20015,"message":"The parameter is invalid. Please check again.","data":null}
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 连接超时时间
        factory.setConnectTimeout(5000);
        // 读取超时时间
        factory.setReadTimeout(60000);
        return RestClient.builder().requestFactory(factory);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }

}
