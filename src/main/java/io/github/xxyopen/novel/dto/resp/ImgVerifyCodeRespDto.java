package io.github.xxyopen.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 图像验证码 响应DTO
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Data
@Builder
public class ImgVerifyCodeRespDto {

    /**
     * 当前会话ID，用于标识改图形验证码属于哪个会话
     * */
    private String sessionId;

    /**
     * Base64 编码的验证码图片
     * */
    private String img;

}
