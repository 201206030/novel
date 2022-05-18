package io.github.xxyopen.novel.core.auth;

import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.util.JwtUtils;
import io.github.xxyopen.novel.dao.mapper.UserInfoMapper;
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

    private final JwtUtils jwtUtils;

    private final UserInfoMapper userInfoMapper;

    @Override
    public void auth(String token) throws BusinessException {
        // TODO 平台后台 token 校验
    }
}