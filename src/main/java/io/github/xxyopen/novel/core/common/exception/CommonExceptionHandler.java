package io.github.xxyopen.novel.core.common.exception;

import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 通用的异常处理器
 *
 * @author xiongxiaoyang
 * @date 2022/5/11
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 处理404异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerNotFound() {
        return "404";
    }

    /**
     * 处理数据校验异常
     */
    @ExceptionHandler(BindException.class)
    public RestResp<Void> handlerBindException(BindException e) {
        log.error(e.getMessage(), e);
        return RestResp.fail(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public RestResp<Void> handlerBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return RestResp.fail(e.getErrorCodeEnum());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public RestResp<Void> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return RestResp.error();
    }

}
