package io.github.xxyopen.novel.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 章节发布 请求DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@Data
public class ChapterAddReqDto {

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 章节名
     */
    @NotBlank
    private String chapterName;

    /**
     * 章节内容
     */
    @NotBlank
    @Length(min = 50)
    private String chapterContent;

    /**
     * 是否收费;1-收费 0-免费
     */
    @NotNull
    private Integer isVip;

}
