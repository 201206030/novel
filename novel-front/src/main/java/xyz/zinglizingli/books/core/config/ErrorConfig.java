package xyz.zinglizingli.books.core.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import xyz.zinglizingli.books.core.utils.Constants;

/**
 *定义配置类
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[2];
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, Constants.NOT_FOUND_PATH);
        errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, Constants.SERVER_ERROR_PATH);

        registry.addErrorPages(errorPages);
    }
}
