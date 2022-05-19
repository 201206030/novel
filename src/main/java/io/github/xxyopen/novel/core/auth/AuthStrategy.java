package io.github.xxyopen.novel.core.auth;

import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.constant.SystemConfigConsts;
import io.github.xxyopen.novel.core.util.JwtUtils;
import io.github.xxyopen.novel.dto.UserInfoDto;
import io.github.xxyopen.novel.manager.UserInfoCacheManager;
import org.springframework.util.StringUtils;

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
     * 如果后面需要扩展到对每一个URI都进行权限控制，那么此方法可以加一个参数来接收用户请求的URI
     *
     * @param token 登录 token
     * @throws BusinessException 认证失败则抛出业务异常
     */
    void auth(String token) throws BusinessException;

    /**
     * 前台多系统单点登录统一账号认证（门户系统、作家系统以及后面会扩展的漫画系统和视频系统等）
     *
     * @param jwtUtils             jwt 工具
     * @param userInfoCacheManager 用户缓存管理对象
     * @param token                token 登录 token
     * @return 用户ID
     */
    default Long authSSO(JwtUtils jwtUtils, UserInfoCacheManager userInfoCacheManager, String token) {
        if (!StringUtils.hasText(token)) {
            // token 为空
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        Long userId = jwtUtils.parseToken(token, SystemConfigConsts.NOVEL_FRONT_KEY);
        if (Objects.isNull(userId)) {
            // token 解析失败
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        UserInfoDto userInfo = userInfoCacheManager.getUser(userId);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        // 设置 userId 到当前线程
        UserHolder.setUserId(userId);
        // 返回 userId
        return userId;
    }
}
