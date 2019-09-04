package xyz.zinglizingli.search.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.zinglizingli.search.filter.SearchFilter;

@Configuration
public class FilterConfig{

    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new SearchFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }

}
