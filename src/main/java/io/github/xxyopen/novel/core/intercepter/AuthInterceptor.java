package io.github.xxyopen.novel.core.intercepter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.core.constant.SystemConfigConsts;
import io.github.xxyopen.novel.core.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

    private final JwtUtils jwtUtils;

    private final ObjectMapper objectMapper;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 校验登录JWT
        String token = request.getHeader(SystemConfigConsts.HTTP_AUTH_HEADER_NAME);
        if (!Objects.isNull(token)) {
            String requestUri = request.getRequestURI();
            if (requestUri.contains(ApiRouterConsts.API_FRONT_USER_URL_PREFIX)
                    || requestUri.contains(ApiRouterConsts.API_AUTHOR_URL_PREFIX)) {
                // 校验会员和作家的登录权限
                Long userId = jwtUtils.parseToken(token, SystemConfigConsts.NOVEL_FRONT_KEY);
                if (!Objects.isNull(userId)) {
                    // TODO 查询用户信息并校验账号状态是否正常
                    // 认证成功
                    return HandlerInterceptor.super.preHandle(request, response, handler);
                }
            }else{
                // TODO 校验后台的登录权限
            }

        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(RestResp.fail(ErrorCodeEnum.USER_LOGIN_EXPIRED)));
        return false;
    }
}
