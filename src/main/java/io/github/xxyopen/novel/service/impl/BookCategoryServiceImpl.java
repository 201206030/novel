package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.BookCategory;
import io.github.xxyopen.novel.dao.mapper.BookCategoryMapper;
import io.github.xxyopen.novel.service.BookCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说类别 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {

}
