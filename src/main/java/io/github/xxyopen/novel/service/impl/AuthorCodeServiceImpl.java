package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.AuthorCode;
import io.github.xxyopen.novel.dao.mapper.AuthorCodeMapper;
import io.github.xxyopen.novel.service.AuthorCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作家邀请码 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class AuthorCodeServiceImpl extends ServiceImpl<AuthorCodeMapper, AuthorCode> implements AuthorCodeService {

}
