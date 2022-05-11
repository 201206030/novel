package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.UserFeedback;
import io.github.xxyopen.novel.dao.mapper.UserFeedbackMapper;
import io.github.xxyopen.novel.service.UserFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户反馈 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class UserFeedbackServiceImpl extends ServiceImpl<UserFeedbackMapper, UserFeedback> implements UserFeedbackService {

}
