package io.github.xxyopen.novel.core.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.url.core.ShardingSphereURL;
import org.apache.shardingsphere.infra.url.core.ShardingSphereURLLoadEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * ShardingSphere 配置类，控制是否开启 ShardingSphere
 *
 * @author xiongxiaoyang
 * @date 2023/12/21
 */
@Configuration
@ConditionalOnProperty(
    prefix = "spring.shardingsphere",
    name = {"enabled"},
    havingValue = "true"
)
@Slf4j
public class ShardingSphereConfiguration {

    private static final String URL = "classpath:shardingsphere-jdbc.yml";

    @Bean
    @SneakyThrows
    public DataSource shardingSphereDataSource() {
        log.info(">>>>>>>>>>> shardingSphereDataSource init.");
        ShardingSphereURLLoadEngine urlLoadEngine = new ShardingSphereURLLoadEngine(
            ShardingSphereURL.parse(URL));
        return YamlShardingSphereDataSourceFactory.createDataSource(urlLoadEngine.loadContent());
    }

}
