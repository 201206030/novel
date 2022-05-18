package io.github.xxyopen.novel.core.intercepter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xxyopen.novel.core.auth.AuthStrategy;
import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.core.constant.SystemConfigConsts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 认证 拦截器
 * 为了注入其它的 Spring beans，需要通过 @Component 注解将该拦截器注册到 Spring 上下文
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final Map<String,AuthStrategy> authStrategy;

    private final ObjectMapper objectMapper;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取登录 JWT
        String token = request.getHeader(SystemConfigConsts.HTTP_AUTH_HEADER_NAME);

        // 获取请求的 URI
        String requestUri = request.getRequestURI();

        // 根据请求的 URI 得到认证策略
        String authStrategyName = requestUri.substring(ApiRouterConsts.API_URL_PREFIX.length() + 1);
        authStrategyName = authStrategyName.substring(0,authStrategyName.indexOf("/"));
        authStrategyName = String.format("%sAuthStrategy",authStrategyName);

        // 开始认证
        try {
            authStrategy.get(authStrategyName).auth(token);
        }catch (BusinessException exception){
            // 认证失败
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(RestResp.fail(ErrorCodeEnum.USER_LOGIN_EXPIRED)));
            return false;
        }
        // 认证成功
        return HandlerInterceptor.super.preHandle(request, response, handler);

    }
}
