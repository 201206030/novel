package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.dao.entity.PayWechat;
import io.github.xxyopen.novel.dao.mapper.PayWechatMapper;
import io.github.xxyopen.novel.service.PayWechatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信支付 服务实现类
 * </p>
 *
 * @author xiongxiaoyang
 * @since 2022/05/11
 */
@Service
public class PayWechatServiceImpl extends ServiceImpl<PayWechatMapper, PayWechat> implements PayWechatService {

}
