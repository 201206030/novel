package io.github.xxyopen.novel.controller.front;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.core.constant.ApiRouterConsts;
import io.github.xxyopen.novel.dto.resp.ImgVerifyCodeRespDto;
import io.github.xxyopen.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 资源（图片/视频/文档）相关 控制器
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_RESOURCE_URL_PREFIX)
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    /**
     * 获取图片验证码接口
     */
    @GetMapping("img_verify_code")
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        return resourceService.getImgVerifyCode();
    }

}
