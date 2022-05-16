package io.github.xxyopen.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 小说分类 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/16
 */
@Data
@Builder
public class BookCategoryRespDto {

    /**
     * 类别ID
     */
    private Long id;

    /**
     * 类别名
     */
    private String name;

}
