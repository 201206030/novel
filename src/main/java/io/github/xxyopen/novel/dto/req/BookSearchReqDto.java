package io.github.xxyopen.novel.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.xxyopen.novel.core.common.req.PageReqDto;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Parameter(description = "搜索关键字")
    private String keyword;

    /**
     * 作品方向
     */
    @Parameter(description = "作品方向")
    private Integer workDirection;

    /**
     * 分类ID
     */
    @Parameter(description = "分类ID")
    private Integer categoryId;

    /**
     * 是否收费，1：收费，0：免费
     */
    @Parameter(description = "是否收费，1：收费，0：免费")
    private Integer isVip;

    /**
     * 小说更新状态，0：连载中，1：已完结
     */
    @Parameter(description = "小说更新状态，0：连载中，1：已完结")
    private Integer bookStatus;

    /**
     * 字数最小值
     */
    @Parameter(description = "字数最小值")
    private Integer wordCountMin;

    /**
     * 字数最大值
     */
    @Parameter(description = "字数最大值")
    private Integer wordCountMax;

    /**
     * 最小更新时间
     * 如果使用Get请求，直接使用对象接收，则可以使用@DateTimeFormat注解进行格式化；
     * 如果使用Post请求，@RequestBody接收请求体参数，默认解析日期格式为yyyy-MM-dd HH:mm:ss ,
     * 如果需要接收其他格式的参数，则可以使用@JsonFormat注解
     * */
    @Parameter(description = "最小更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeMin;

}
