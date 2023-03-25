package io.github.xxyopen.novel.manager.message;

import io.github.xxyopen.novel.core.config.MailProperties;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 抽象的邮件消息发送者
 *
 * @author xiongxiaoyang
 * @date 2023/3/24
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMailSender extends AbstractMessageSender {

    private final MailProperties mailProperties;

    private final JavaMailSender mailSender;

    @Override
    protected void sendMessage(Long toUserId, String messageTitle, String messageContent) {
        // TODO 根据消息接收方的用户ID查询出消息接收方的邮件地址
        String toEmail = "xxyopen@foxmail.com";
        // 开始发送邮件
        log.info("发送 HTML 邮件开始：{},{},{}", toEmail, messageTitle, messageContent);
        // 使用 MimeMessage，MIME 协议
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        // MimeMessageHelper 帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(mailProperties.username(), mailProperties.nickname(), "UTF-8"));
            helper.setTo(toEmail);
            helper.setSubject(messageTitle);
            // 第二个参数 true 代表支持 html
            helper.setText(messageContent, true);
            mailSender.send(message);
            log.info("发送 HTML 邮件 to {} 成功", toEmail);
        } catch (Exception e) {
            // 邮件发送失败不会重试
            log.error("发送 HTML 邮件 to {} 失败", toEmail, e);
        }
    }

}
