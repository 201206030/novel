package io.github.xxyopen.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 用户注册 响应DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
@Builder
public class UserRegisterRespDto {

    private Long uid;

    private String token;
}
