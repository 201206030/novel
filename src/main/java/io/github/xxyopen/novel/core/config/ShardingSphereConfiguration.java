package io.github.xxyopen.novel.core.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * ShardingSphere 配置类，控制是否开启 ShardingSphere
 *
 * @author xiongxiaoyang
 * @date 2023/12/21
 */
@Configuration
@EnableAutoConfiguration(exclude = {
    org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration.class
})
@ConditionalOnProperty(
    prefix = "spring.shardingsphere",
    name = {"enabled"},
    havingValue = "false"
)
public class ShardingSphereConfiguration {

}
