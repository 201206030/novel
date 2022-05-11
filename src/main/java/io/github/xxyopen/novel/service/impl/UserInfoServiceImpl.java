package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.UserInfo;
import io.github.xxyopen.novel.dao.mapper.UserInfoMapper;
import io.github.xxyopen.novel.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
