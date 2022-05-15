package io.github.xxyopen.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小说内容 响应DTO
 * @author xiongxiaoyang
 * @date 2022/5/15
 */
@Data
@Builder
public class BookContentRespDto {

    /**
     * 小说 ID
     */
    private Long bookId;

    /**
     * 类别ID
     */
    private Long categoryId;

    /**
     * 类别名
     */
    private String categoryName;

    /**
     * 小说名
     */
    private String bookName;

    /**
     * 作家id
     */
    private Long authorId;

    /**
     * 作家名
     */
    private String authorName;

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
     * 章节内容
     */
    private String content;

}
