package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.UserComment;
import io.github.xxyopen.novel.dao.mapper.UserCommentMapper;
import io.github.xxyopen.novel.service.UserCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户评论 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentMapper, UserComment> implements UserCommentService {

}
