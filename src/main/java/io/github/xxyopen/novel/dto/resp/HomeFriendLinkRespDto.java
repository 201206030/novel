package io.github.xxyopen.novel.dto.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 首页友情链接 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
@Data
public class HomeFriendLinkRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接url
     */
    private String linkUrl;
}
