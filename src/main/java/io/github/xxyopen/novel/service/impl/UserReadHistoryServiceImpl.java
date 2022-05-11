package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.UserReadHistory;
import io.github.xxyopen.novel.dao.mapper.UserReadHistoryMapper;
import io.github.xxyopen.novel.service.UserReadHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户阅读历史 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class UserReadHistoryServiceImpl extends ServiceImpl<UserReadHistoryMapper, UserReadHistory> implements UserReadHistoryService {

}
