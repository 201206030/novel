package io.github.xxyopen.novel.dto.req;

import io.github.xxyopen.novel.core.common.req.PageReqDto;
import lombok.Data;

import java.util.Date;

/**
 * 小说搜索 请求DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/16
 */
@Data
public class BookSearchReqDto extends PageReqDto {

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 作品方向
     */
    private Byte workDirection;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 是否收费，1：收费，0：免费
     */
    private Byte isVip;

    /**
     * 小说更新状态，0：连载中，1：已完结
     */
    private Byte bookStatus;

    /**
     * 字数最小值
     */
    private Integer wordCountMin;

    /**
     * 字数最大值
     */
    private Integer wordCountMax;

    private Date updateTimeMin;

    /**
     * 更新时间（单位：天）
     */
    private Long updatePeriod;

    /**
     * 排序字段
     */
    private String sort = "last_chapter_update_time desc";
}
