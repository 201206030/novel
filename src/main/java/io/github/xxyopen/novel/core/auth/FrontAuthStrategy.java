package io.github.xxyopen.novel.core.auth;

import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.util.JwtUtils;
import io.github.xxyopen.novel.dao.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 前台门户系统 认证策略
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Component
@RequiredArgsConstructor
public class FrontAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final UserInfoMapper userInfoMapper;

    @Override
    public void auth(String token) throws BusinessException {
        // 统一账号认证
        authSSO(jwtUtils,userInfoMapper,token);
    }
}