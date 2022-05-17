package io.github.xxyopen.novel.service;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.req.UserLoginReqDto;
import io.github.xxyopen.novel.dto.req.UserRegisterReqDto;
import io.github.xxyopen.novel.dto.resp.UserLoginRespDto;

/**
 * 会员模块 服务类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
public interface UserService {

    /**
     * 用户注册
     * @param dto 注册参数
     * @return JWT
     * */
    RestResp<String> register(UserRegisterReqDto dto);

    /**
     * 用户登录
     * @param dto 登录参数
     * @return JWT + 昵称
     * */
    RestResp<UserLoginRespDto> login(UserLoginReqDto dto);
}
