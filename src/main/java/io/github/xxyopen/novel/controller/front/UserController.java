package io.github.xxyopen.novel.controller.front;

import io.github.xxyopen.novel.core.auth.UserHolder;
import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.dto.req.UserCommentReqDto;
import io.github.xxyopen.novel.dto.req.UserInfoUptReqDto;
import io.github.xxyopen.novel.dto.req.UserLoginReqDto;
import io.github.xxyopen.novel.dto.req.UserRegisterReqDto;
import io.github.xxyopen.novel.dto.resp.UserLoginRespDto;
import io.github.xxyopen.novel.service.BookService;
import io.github.xxyopen.novel.service.UserService;
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

    private final BookService bookService;

    /**
     * 用户注册接口
     */
    @PostMapping("register")
    public RestResp<String> register(@Valid @RequestBody UserRegisterReqDto dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录接口
     */
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto dto) {
        return userService.login(dto);
    }


    /**
     * 用户信息修改接口
     */
    @PutMapping
    public RestResp<Void> updateUserInfo(@Valid @RequestBody UserInfoUptReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return userService.updateUserInfo(dto);
    }

    /**
     * 用户反馈提交接口
     */
    @PostMapping("feedback")
    public RestResp<Void> submitFeedback(@RequestBody String content) {
        return userService.saveFeedback(UserHolder.getUserId(), content);
    }

    /**
     * 用户反馈删除接口
     * */
    @DeleteMapping("feedback/{id}")
    public RestResp<Void> deleteFeedback(@PathVariable Long id) {
        return userService.deleteFeedback(UserHolder.getUserId(), id);
    }

    /**
     * 发表评论接口
     * */
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return bookService.saveComment(dto);
    }

    /**
     * 查询书架状态接口
     * 0-不在书架
     * 1-已在书架
     * */
    @GetMapping("bookshelf_status")
    public RestResp<Integer> getBookshelfStatus(@RequestBody String bookId) {
        return userService.getBookshelfStatus(UserHolder.getUserId(),bookId);
    }

}
