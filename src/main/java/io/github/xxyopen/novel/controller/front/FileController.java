package io.github.xxyopen.novel.controller.front;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.common.util.IpUtils;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 文件相关 控制器
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_FILE_URL_PREFIX)
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 获取图片验证码接口
     */
    @GetMapping("imgVerifyCode")
    public RestResp<String> getImgVerifyCode(HttpServletRequest request) throws IOException {
        return RestResp.ok(fileService.getImgVerifyCode(IpUtils.getRealIp(request)));
    }

}
