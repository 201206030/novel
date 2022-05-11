package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.NewsContent;
import io.github.xxyopen.novel.dao.mapper.NewsContentMapper;
import io.github.xxyopen.novel.service.NewsContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻内容 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class NewsContentServiceImpl extends ServiceImpl<NewsContentMapper, NewsContent> implements NewsContentService {

}
