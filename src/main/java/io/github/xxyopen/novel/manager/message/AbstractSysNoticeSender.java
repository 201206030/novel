package io.github.xxyopen.novel.manager.message;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 抽象的系统通知发送者
 *
 * @author xiongxiaoyang
 * @date 2023/3/24
 */
@Slf4j
public abstract class AbstractSysNoticeSender extends AbstractMessageSender {

    @Override
    protected void sendMessage(Long toUserId, String messageTitle, String messageContent) {
        // 生成消息的发送时间
        LocalDateTime messageDateTime = LocalDateTime.now();
        // TODO 在数据库系统通知表中插入一条记录
        log.info("系统通知发送成功，{},{},{},{}", toUserId, messageDateTime.format(DateTimeFormatter.ISO_DATE_TIME),
            messageTitle, messageContent);
    }

}
