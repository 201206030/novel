package io.github.xxyopen.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 小说内容相关 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/15
 */
@Data
@Builder
public class BookContentAboutRespDto {

    /**
     * 小说信息
     */
    private BookInfoRespDto bookInfo;

    /**
     * 章节信息
     */
    private BookChapterRespDto chapterInfo;

    /**
     * 章节内容
     */
    private String bookContent;

}
