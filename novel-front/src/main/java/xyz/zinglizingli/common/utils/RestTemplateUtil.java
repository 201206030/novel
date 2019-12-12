package xyz.zinglizingli.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11797
 */
@Slf4j
public class RestTemplateUtil {

    private static Map<String,RestTemplate> restTemplateMap = new HashMap<>();

    public static RestTemplate getInstance(Charset charset) {
        RestTemplate restTemplate = restTemplateMap.get(charset.name());
        if(restTemplate == null) {

            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            httpRequestFactory.setConnectionRequestTimeout(3000);
            httpRequestFactory.setConnectTimeout(3000);
            httpRequestFactory.setReadTimeout(10000);
            restTemplate = new RestTemplate(httpRequestFactory);
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
