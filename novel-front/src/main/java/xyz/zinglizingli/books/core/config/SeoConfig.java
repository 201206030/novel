package xyz.zinglizingli.books.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author 11797
 */
@Data
@Component
@ConfigurationProperties(prefix="website")
public class SeoConfig {

    private Map<String,String> page;
}
