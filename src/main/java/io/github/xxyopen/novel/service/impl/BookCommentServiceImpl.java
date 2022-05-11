package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.BookComment;
import io.github.xxyopen.novel.dao.mapper.BookCommentMapper;
import io.github.xxyopen.novel.service.BookCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说评论 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class BookCommentServiceImpl extends ServiceImpl<BookCommentMapper, BookComment> implements BookCommentService {

}
