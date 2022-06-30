package io.github.xxyopen.novel.core.config;

import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置类
 *
 * @author xiongxiaoyang
 * @date 2022/6/20
 */
@Configuration
public class RedissonConfig {

    @Bean
    @SneakyThrows
    public RedissonClient redissonClient() {
        Config config = Config.fromYAML(getClass().getResource("/redisson.yml"));
        return Redisson.create(config);
    }

}
