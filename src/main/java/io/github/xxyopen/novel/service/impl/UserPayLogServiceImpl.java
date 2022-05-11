package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.UserPayLog;
import io.github.xxyopen.novel.dao.mapper.UserPayLogMapper;
import io.github.xxyopen.novel.service.UserPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户充值记录 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class UserPayLogServiceImpl extends ServiceImpl<UserPayLogMapper, UserPayLog> implements UserPayLogService {

}
