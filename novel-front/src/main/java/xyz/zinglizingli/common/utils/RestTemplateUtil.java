package xyz.zinglizingli.common.utils;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
