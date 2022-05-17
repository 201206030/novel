package io.github.xxyopen.novel.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户信息更新 请求DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
public class UserInfoUptReqDto {

    private Long userId;

    @Length(min = 2,max = 10)
    private String nickName;

    @Pattern(regexp="^/[^\s]{10,}\\.(png|jpg|jpeg|gif|bpm)$")
    private String userPhoto;

    @Min(value = 0)
    @Max(value = 1)
    private Integer userSex;

}
