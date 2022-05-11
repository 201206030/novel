package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.BookContent;
import io.github.xxyopen.novel.dao.mapper.BookContentMapper;
import io.github.xxyopen.novel.service.BookContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说内容 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class BookContentServiceImpl extends ServiceImpl<BookContentMapper, BookContent> implements BookContentService {

}
