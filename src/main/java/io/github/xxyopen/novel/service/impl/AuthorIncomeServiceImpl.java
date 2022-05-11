package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.AuthorIncome;
import io.github.xxyopen.novel.dao.mapper.AuthorIncomeMapper;
import io.github.xxyopen.novel.service.AuthorIncomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 稿费收入统计 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class AuthorIncomeServiceImpl extends ServiceImpl<AuthorIncomeMapper, AuthorIncome> implements AuthorIncomeService {

}
