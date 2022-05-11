package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.NewsInfo;
import io.github.xxyopen.novel.dao.mapper.NewsInfoMapper;
import io.github.xxyopen.novel.service.NewsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻信息 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class NewsInfoServiceImpl extends ServiceImpl<NewsInfoMapper, NewsInfo> implements NewsInfoService {

}
