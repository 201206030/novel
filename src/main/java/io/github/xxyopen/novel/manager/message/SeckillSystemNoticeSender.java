package io.github.xxyopen.novel.manager.message;

import io.github.xxyopen.novel.core.constant.MessageSenderTypeConsts;
import org.springframework.stereotype.Component;

/**
 * 秒杀活动的系统通知发送器
 *
 * @author xiongxiaoyang
 * @date 2023/3/24
 */
@Component(value = MessageSenderTypeConsts.SECKILL_SYS_NOTICE_SENDER)
public class SeckillSystemNoticeSender extends AbstractSysNoticeSender {

    @Override
    protected String getTitleTemplate() {
        return "秒杀即将开始";
    }

    @Override
    protected String getContentTemplate() {
        return "{}秒杀，{}即将开始，不要错过哦！点击 {} 前往。";
    }

}
