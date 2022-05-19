package io.github.xxyopen.novel.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户发表评论 请求DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
public class UserCommentReqDto {

    private Long userId;

    @NotBlank(message="小说ID不能为空！")
    private Long bookId;

    @NotBlank(message="评论不能为空！")
    @Length(min = 10,max = 512)
    private String commentContent;

}
