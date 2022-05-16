package io.github.xxyopen.novel.service;

import io.github.xxyopen.novel.core.common.resp.RestResp;
import io.github.xxyopen.novel.dto.resp.NewsInfoRespDto;

import java.util.List;

/**
 * 新闻模块 服务类
 *
 * @author xiongxiaoyang
 * @date 2022/5/14
 */
public interface NewsService {

    /**
     * 最新新闻列表查询
     * @return 新闻列表
     * */
    RestResp<List<NewsInfoRespDto>> listLatestNews();

    /**
     * 新闻信息查询
     * @param id 新闻ID
     * @return 新闻信息
     * */
    RestResp<NewsInfoRespDto> getNews(Long id);
}
