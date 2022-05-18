package io.github.xxyopen.novel.core.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;
import io.github.xxyopen.novel.core.common.exception.BusinessException;
import io.github.xxyopen.novel.core.constant.DatabaseConsts;
import io.github.xxyopen.novel.core.util.JwtUtils;
import io.github.xxyopen.novel.dao.entity.AuthorInfo;
import io.github.xxyopen.novel.dao.mapper.AuthorInfoMapper;
import io.github.xxyopen.novel.dao.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 作家后台管理系统 认证策略
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Component
@RequiredArgsConstructor
public class AuthorAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final UserInfoMapper userInfoMapper;

    private final AuthorInfoMapper authorInfoMapper;

    @Override
    public void auth(String token) throws BusinessException {
        // 统一账号认证
        Long userId = authSSO(jwtUtils, userInfoMapper, token);

        // 作家权限认证
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(DatabaseConsts.AuthorInfoTable.ColumnEnum.USER_ID.getName(), userId)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);
        if(Objects.isNull(authorInfo)){
            // 作家账号不存在，无权访问作家专区
            throw new BusinessException(ErrorCodeEnum.USER_UN_AUTH);
        }
    }
}