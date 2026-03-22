package io.github.xxyopen.novel.core.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 阿里云OSS文件上传工具类
 * 封装了将字节数组上传到指定Bucket并返回公开访问URL的功能
 * 使用Spring的组件扫描，可被自动注入到其他Bean中
 */
@Component
public class AliyunOSSOperator {

    // 通过注入的自定义配置类获取OSS相关参数（endpoint、bucketName、region）
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    /**
     * 上传字节数组到阿里云OSS
     * @param content          文件的字节内容（例如图片的byte[]）
     * @param originalFilename 原始文件名（用于提取扩展名）
     * @return 文件的公开访问URL（可直接在浏览器中访问）
     * @throws Exception 当上传过程中出现任何错误时抛出（如网络、凭证无效等）
     */
    public String upload(byte[] content, String originalFilename) throws Exception {
        // 从配置类中获取OSS必要参数
        String endpoint = aliyunOSSProperties.getEndpoint();       // OSS服务端点，如 "https://oss-cn-hangzhou.aliyuncs.com"
        String bucketName = aliyunOSSProperties.getBucketName();   // 存储空间名称
        String region = aliyunOSSProperties.getRegion();           // 地域ID，如 "cn-hangzhou"

        // 1. 构建凭证提供者（从环境变量读取AccessKey和SecretKey）
        // 要求环境变量中已设置 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET
        CredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();

        // 2. 生成文件在OSS中的存储路径（对象键）
        // 按年月分类存放，格式：yyyy/MM/ 例如 "2025/03/"
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        // 生成新的文件名：UUID + 原始文件扩展名，避免文件名冲突
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 完整对象名（路径+文件名）
        String objectName = dir + "/" + newFileName;

        // 3. 创建OSSClient实例
        // 需要设置签名版本为V4（阿里云OSS目前推荐V4），并指定region
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4); // 使用V4签名
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)  // 指定region，与V4签名配合使用
                .build();

        try {
            // 4. 执行上传操作
            // 将字节数组包装为ByteArrayInputStream，直接上传
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            // 5. 关闭OSSClient释放资源（无论成功或失败都要关闭）
            ossClient.shutdown();
        }

        // 6. 构造文件的公开访问URL
        // 格式：协议 + bucket名称 + 端点域名（去除协议部分） + 对象名
        // 示例：https://my-bucket.oss-cn-hangzhou.aliyuncs.com/2025/03/uuid.jpg
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
        return url;
    }
}