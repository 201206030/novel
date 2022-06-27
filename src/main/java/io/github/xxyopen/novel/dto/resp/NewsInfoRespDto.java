package io.github.xxyopen.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新闻信息 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
@Data
@Builder
public class NewsInfoRespDto {

    /**
     * ID
     */
    @Schema(description = "新闻ID")
    private Long id;

    /**
     * 类别ID
     */
    @Schema(description = "类别ID")
    private Long categoryId;

    /**
     * 类别名
     */
    @Schema(description = "类别名")
    private String categoryName;

    /**
     * 新闻来源
     */
    @Schema(description = "新闻来源")
    private String sourceName;

    /**
     * 新闻标题
     */
    @Schema(description = "新闻标题")
    private String title;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;

    /**
     * 新闻内容
     * */
    @Schema(description = "新闻内容")
    private String content;


}
