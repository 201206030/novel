package io.github.xxyopen.novel.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 小说发布 请求DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@Data
public class BookAddReqDto {

    /**
     * 作品方向;0-男频 1-女频
     */
    @NotNull
    private Integer workDirection;

    /**
     * 类别ID
     */
    @NotNull
    private Long categoryId;

    /**
     * 类别名
     */
    @NotBlank
    private String categoryName;

    /**
     * 小说封面地址
     */
    @NotBlank
    private String picUrl;

    /**
     * 小说名
     */
    @NotBlank
    private String bookName;

    /**
     * 书籍描述
     */
    @NotBlank
    private String bookDesc;

    /**
     * 是否收费;1-收费 0-免费
     */
    @NotNull
    private Integer isVip;
}
