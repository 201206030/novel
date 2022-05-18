package io.github.xxyopen.novel.core.auth;

import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.constant.SystemConfigConsts;
import io.github.xxyopen.novel.core.util.JwtUtils;
import io.github.xxyopen.novel.dao.entity.UserInfo;
import io.github.xxyopen.novel.dao.mapper.UserInfoMapper;

import java.util.Objects;

/**
 * 策略模式实现用户认证授权功能
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
public interface AuthStrategy {

    /**
     * 请求用户认证
     *
     * @param token 登录 token
     * @throws BusinessException 认证失败则抛出义务异常
     */
    void auth(String token) throws BusinessException;

    /**
     * 前台多系统单点登录统一账号认证（门户系统、作家系统以及后面会扩展的漫画系统和视频系统等）
     *
     * @param jwtUtils       jwt 工具
     * @param userInfoMapper 用户查询 Mapper
     * @param token          token 登录 token
     * @return 用户ID
     */
    default Long authSSO(JwtUtils jwtUtils, UserInfoMapper userInfoMapper, String token) {
        if (Objects.isNull(token)) {
            // token 为空
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        Long userId = jwtUtils.parseToken(token, SystemConfigConsts.NOVEL_FRONT_KEY);
        if (Objects.isNull(userId)) {
            // token 解析失败
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        return userId;
    }
}
