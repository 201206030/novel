package io.github.xxyopen.novel.dto.req;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 作家注册 请求DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@Data
public class AuthorRegisterReqDto {

    private Long userId;

    /**
     * 笔名
     */
    @NotBlank(message="笔名不能为空！")
    private String penName;

    /**
     * 手机号码
     */
    @NotBlank(message="手机号不能为空！")
    @Pattern(regexp="^1[3|4|5|6|7|8|9][0-9]{9}$",message="手机号格式不正确！")
    private String telPhone;

    /**
     * QQ或微信账号
     */
    @NotBlank(message="QQ或微信账号不能为空！")
    private String chatAccount;

    /**
     * 电子邮箱
     */
    @NotBlank(message="电子邮箱不能为空！")
    @Email(message="邮箱格式不正确！")
    private String email;

    /**
     * 作品方向;0-男频 1-女频
     */
    @NotNull(message="作品方向不能为空！")
    @Min(0)
    @Max(1)
    private Integer workDirection;

}
