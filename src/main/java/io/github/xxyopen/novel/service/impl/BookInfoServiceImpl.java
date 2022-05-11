package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.BookInfo;
import io.github.xxyopen.novel.dao.mapper.BookInfoMapper;
import io.github.xxyopen.novel.service.BookInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说信息 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {

}
