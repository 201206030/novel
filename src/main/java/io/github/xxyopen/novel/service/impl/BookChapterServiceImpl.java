package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.BookChapter;
import io.github.xxyopen.novel.dao.mapper.BookChapterMapper;
import io.github.xxyopen.novel.service.BookChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说章节 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class BookChapterServiceImpl extends ServiceImpl<BookChapterMapper, BookChapter> implements BookChapterService {

}
