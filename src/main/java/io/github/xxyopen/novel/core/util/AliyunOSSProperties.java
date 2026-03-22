package io.github.xxyopen.novel.core.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置属性类
 * 负责从Spring Boot配置文件中读取以 "aliyun.oss" 为前缀的配置项，
 * 并通过Lombok的@Data自动生成getter/setter方法。
 * 被Spring容器管理，可注入到其他Bean中使用。
 *
 * @author Silver-kite
 * @date 2022/5/17
 */
@Data                // Lombok注解：自动生成所有字段的getter/setter、toString、equals、hashCode
@Component           // 声明为Spring组件，使其能被自动扫描并实例化为Bean
@ConfigurationProperties(prefix = "aliyun.oss")  // 绑定配置文件中前缀为 "aliyun.oss" 的属性
public class AliyunOSSProperties {
    private String endpoint;      // OSS 服务端点，例如：https://oss-cn-hangzhou.aliyuncs.com
    private String bucketName;    // 存储空间名称，例如：my-bucket
    private String region;        // 地域 ID，例如：cn-hangzhou
    private Boolean enabled;      // 是否启用，默认为 false

}