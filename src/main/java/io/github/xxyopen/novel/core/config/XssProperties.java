package io.github.xxyopen.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Xss 过滤配置属性
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@ConfigurationProperties(prefix = "novel.xss")
@Data
public class XssProperties {

    /**
     * 过滤开关
     * */
    private Boolean enabled;

    /**
     * 排除链接
     * */
    private List<String> excludes;

}
