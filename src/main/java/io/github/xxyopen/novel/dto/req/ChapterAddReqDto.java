package io.github.xxyopen.novel.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "小说ID", required = true)
    private Long bookId;

    /**
     * 章节名
     */
    @NotBlank
    @Schema(description = "章节名", required = true)
    private String chapterName;

    /**
     * 章节内容
     */
    @Schema(description = "章节内容", required = true)
    @NotBlank
    @Length(min = 50)
    private String chapterContent;

    /**
     * 是否收费;1-收费 0-免费
     */
    @Schema(description = "是否收费;1-收费 0-免费", required = true)
    @NotNull
    private Integer isVip;

}
