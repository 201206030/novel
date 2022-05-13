package io.github.xxyopen.novel.dto.resp;

import lombok.Data;

/**
 * 首页小说推荐 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/13
 */
@Data
public class HomeBookRespDto {

    /**
     * 类型;0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐
     */
    private Integer type;

    /**
     * 推荐小说ID
     */
    private Long bookId;

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

}
