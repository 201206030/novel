package io.github.xxyopen.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小说章节 响应DTO
 * @author xiongxiaoyang
 * @date 2022/5/15
 */
@Data
@Builder
public class BookChapterRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 章节字数
     */
    private Integer chapterWordCount;

    /**
     * 章节更新时间
     * */
    @JsonFormat(pattern = "yyyy/MM/dd HH:dd")
    private LocalDateTime chapterUpdateTime;

    /**
     * 内容概要（20字）
     */
    private String contentSummary;

}
