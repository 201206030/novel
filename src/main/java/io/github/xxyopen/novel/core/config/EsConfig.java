package io.github.xxyopen.novel.core.config;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * Elasticsearch 相关配置
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@ConditionalOnProperty(value = "spring.elasticsearch.enabled", havingValue = "true")
@Configuration
@Slf4j
public class EsConfig {

    /**
     * 解决 ElasticsearchClientConfigurations 修改默认 ObjectMapper 配置的问题
     */
    @Bean
    JacksonJsonpMapper jacksonJsonpMapper() {
        return new JacksonJsonpMapper();
    }

    /**
     * fix `sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
     * unable to find valid certification path to requested target`
     */
    @Bean
    RestClient elasticsearchRestClient(RestClientBuilder restClientBuilder,
        ObjectProvider<RestClientBuilderCustomizer> builderCustomizers) {
        restClientBuilder.setHttpClientConfigCallback((HttpAsyncClientBuilder clientBuilder) -> {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sc = null;
            try {
                sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                log.error("Elasticsearch RestClient 配置失败！", e);
            }
            assert sc != null;
            clientBuilder.setSSLContext(sc);
            clientBuilder.setSSLHostnameVerifier((hostname, session) -> true);

            builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(clientBuilder));
            return clientBuilder;
        });
        return restClientBuilder.build();
    }

}
