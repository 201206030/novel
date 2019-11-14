package xyz.zinglizingli.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import xyz.zinglizingli.common.filter.SearchFilter;

@Configuration
public class FilterConfig{

    //@Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new SearchFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }

}
