package xyz.zinglizingli.common.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11797
 */
@Slf4j
public class RestTemplateUtil {

    private static Map<String,RestTemplate> restTemplateMap = new HashMap<>();

    @SneakyThrows
    public static RestTemplate getInstance(Charset charset) {
        RestTemplate restTemplate = restTemplateMap.get(charset.name());
        if(restTemplate == null) {

            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            //忽略证书
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", csf)
                    .build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

            //连接池大小
            connectionManager.setMaxTotal(1000);
            //最大并发连接
            connectionManager.setDefaultMaxPerRoute(300);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();


            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);

            requestFactory.setConnectionRequestTimeout(3000);
            requestFactory.setConnectTimeout(3000);
            requestFactory.setReadTimeout(10000);
            restTemplate = new RestTemplate(requestFactory);
            List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
            for (HttpMessageConverter<?> httpMessageConverter : list) {
                if (httpMessageConverter instanceof StringHttpMessageConverter) {
                    ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(charset);
                    break;
                }
            }
            restTemplateMap.put(charset.name(),restTemplate);
        }
        return restTemplate;
    }

    public static String getBodyByUtf8(String url) {
        try {
            ResponseEntity<String> forEntity = getInstance(Charsets.UTF_8).getForEntity(url, String.class);
            if (forEntity.getStatusCode() == HttpStatus.OK) {
                return forEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
