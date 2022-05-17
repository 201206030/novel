package io.github.xxyopen.novel.service;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.resp.ImgVerifyCodeRespDto;

import java.io.IOException;

/**
 * 资源（图片/视频/文档）相关服务类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
public interface ResourceService {

    /**
     * 获取图片验证码
     * @return Base64编码的图片
     * */
    RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException;
}
