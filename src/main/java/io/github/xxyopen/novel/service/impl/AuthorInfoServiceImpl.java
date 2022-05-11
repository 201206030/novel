package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.AuthorInfo;
import io.github.xxyopen.novel.dao.mapper.AuthorInfoMapper;
import io.github.xxyopen.novel.service.AuthorInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作者信息 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class AuthorInfoServiceImpl extends ServiceImpl<AuthorInfoMapper, AuthorInfo> implements AuthorInfoService {

}
