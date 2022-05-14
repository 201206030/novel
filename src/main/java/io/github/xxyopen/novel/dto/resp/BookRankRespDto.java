package io.github.xxyopen.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小说排行榜 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
@Data
public class BookRankRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 类别ID
     */
    private Long categoryId;

    /**
     * 类别名
     */
    private String categoryName;

    /**
     * 小说封面地址
     */
    private String picUrl;

    /**
     * 小说名
     */
    private String bookName;

    /**
     * 作家名
     */
    private String authorName;

    /**
     * 书籍描述
     */
    private String bookDesc;

    /**
     * 总字数
     */
    private Integer wordCount;

    /**
     * 最新章节名
     */
    private String lastChapterName;

    /**
     * 最新章节更新时间
     */
    @JsonFormat(pattern = "MM/dd HH:mm")
    private LocalDateTime lastChapterUpdateTime;

}
