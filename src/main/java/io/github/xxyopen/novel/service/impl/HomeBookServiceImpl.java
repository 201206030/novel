package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.HomeBook;
import io.github.xxyopen.novel.dao.mapper.HomeBookMapper;
import io.github.xxyopen.novel.service.HomeBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说推荐 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class HomeBookServiceImpl extends ServiceImpl<HomeBookMapper, HomeBook> implements HomeBookService {

}
