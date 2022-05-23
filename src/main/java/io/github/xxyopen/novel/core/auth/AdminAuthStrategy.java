package io.github.xxyopen.novel.core.auth;

import io.github.xxyopen.novel.core.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 平台后台管理系统 认证策略
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Component
@RequiredArgsConstructor
public class AdminAuthStrategy implements AuthStrategy {

    @Override
    public void auth(String token, String requestUri) throws BusinessException {
        // TODO 平台后台 token 校验
    }
}