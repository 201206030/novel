package io.github.xxyopen.novel.controller.front;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.common.util.IpUtils;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.core.constant.SystemConfigConsts;
import io.github.xxyopen.novel.core.util.JwtUtils;
import io.github.xxyopen.novel.dto.req.UserLoginReqDto;
import io.github.xxyopen.novel.dto.req.UserRegisterReqDto;
import io.github.xxyopen.novel.dto.resp.UserLoginRespDto;
import io.github.xxyopen.novel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 会员模块相关 控制器
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    /**
     * 用户注册接口
     */
    @PostMapping("register")
    public RestResp<String> register(@Valid UserRegisterReqDto dto, HttpServletRequest request)  {
        dto.setUserKey(IpUtils.getRealIp(request));
        return userService.register(dto);
    }

    /**
     * 用户登录接口
     */
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid UserLoginReqDto dto)  {
        return userService.login(dto);
    }

    /**
     * 用户反馈
     */
    @PostMapping("feedBack")
    public RestResp<Void> submitFeedBack(String content,@RequestHeader("Authorization") String token)  {
        return userService.saveFeedBack(jwtUtils.parseToken(token, SystemConfigConsts.NOVEL_FRONT_KEY),content);
    }

}
