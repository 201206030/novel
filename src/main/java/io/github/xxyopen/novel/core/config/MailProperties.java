package io.github.xxyopen.novel.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mail 配置属性
 *
 * @author xiongxiaoyang
 * @date 2023/3/25
 */
@ConfigurationProperties(prefix = "spring.mail")
public record MailProperties(String nickname, String username) {

}
