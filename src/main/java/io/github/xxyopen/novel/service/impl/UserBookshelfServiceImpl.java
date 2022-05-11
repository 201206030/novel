package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.UserBookshelf;
import io.github.xxyopen.novel.dao.mapper.UserBookshelfMapper;
import io.github.xxyopen.novel.service.UserBookshelfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户书架 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class UserBookshelfServiceImpl extends ServiceImpl<UserBookshelfMapper, UserBookshelf> implements UserBookshelfService {

}
