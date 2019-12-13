package xyz.zinglizingli.books.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.zinglizingli.books.core.filter.BookFilter;

@Configuration
public class FilterConfig{

    @Value("${pic.save.path}")
    private String picSavePath;

    @Bean
    public FilterRegistrationBean<BookFilter> filterRegist() {
        FilterRegistrationBean<BookFilter> frBean = new FilterRegistrationBean<>();
        frBean.setFilter(new BookFilter());
        frBean.addUrlPatterns("/*");
        frBean.addInitParameter("picSavePath",picSavePath);
        return frBean;
    }

}
