package io.github.xxyopen.novel.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.xxyopen.novel.core.common.req.PageReqDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    /**
     * 最小更新时间
     * 如果使用Get请求，直接使用对象接收，则可以使用@DateTimeFormat注解进行格式化；
     * 如果使用Post请求，@RequestBody接收请求体参数，默认解析日期格式为yyyy-MM-dd HH:mm:ss ,
     * 如果需要接收其他格式的参数，则可以使用@JsonFormat注解
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeMin;

    /**
     * 排序字段
     */
    private String sort = "last_chapter_update_time desc";
}
