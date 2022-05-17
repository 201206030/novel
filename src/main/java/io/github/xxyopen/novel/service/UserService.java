package io.github.xxyopen.novel.service;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.req.UserRegisterReqDto;

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
    RestResp<String> getImgVerifyCode(UserRegisterReqDto dto);
}
