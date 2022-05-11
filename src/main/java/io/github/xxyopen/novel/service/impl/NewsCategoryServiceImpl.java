package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.NewsCategory;
import io.github.xxyopen.novel.dao.mapper.NewsCategoryMapper;
import io.github.xxyopen.novel.service.NewsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻类别 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class NewsCategoryServiceImpl extends ServiceImpl<NewsCategoryMapper, NewsCategory> implements NewsCategoryService {

}
