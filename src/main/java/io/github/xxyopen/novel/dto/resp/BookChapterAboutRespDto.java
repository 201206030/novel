package io.github.xxyopen.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 小说章节相关 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/15
 */
@Data
@Builder
public class BookChapterAboutRespDto {

    private BookChapterRespDto chapterInfo;

    /**
     * 章节总数
     */
    private Long chapterTotal;

    /**
     * 内容概要（30字）
     */
    private String contentSummary;

}
