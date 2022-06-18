package io.github.xxyopen.novel.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 跨域配置属性
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@ConfigurationProperties(prefix = "novel.cors")
public record CorsProperties(List<String> allowOrigins) {

}
