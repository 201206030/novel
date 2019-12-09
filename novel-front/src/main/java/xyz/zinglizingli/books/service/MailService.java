package xyz.zinglizingli.books.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author XXY
 */
@Service
@RequiredArgsConstructor
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    /**
     * 使用@Value注入application.properties中指定的用户名
     * */
    @Value("${spring.mail.username}")
    private String from;

    private String nickName = "精品小说楼";

    /**
     * 用于发送文件
     * */
    private final JavaMailSender mailSender;


    /**
     * 发送简单邮件
     * */
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        //收信人
        message.setTo(to);
        //主题
        message.setSubject(subject);
        //内容
        message.setText(content);
        //发信人
        message.setFrom(from);

        mailSender.send(message);
    }


    /**
     * 发送html邮件
     * */
    public void sendHtmlMail(String to, String subject, String content){

        logger.info("发送HTML邮件开始：{},{},{}", to, subject, content);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
            helper.setTo(to);
            helper.setSubject(subject);
            //true代表支持html
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("发送HTMLto"+to+"邮件成功");
        } catch (Exception e) {
            logger.error("发送HTML邮件失败：", e);
        }
    }

    /**
     * 发送带附件的邮件
     * */
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

        logger.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            //添加附件，可多次调用该方法添加多个附件
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("发送带附件邮件成功");
        } catch (MessagingException e) {
            logger.error("发送带附件邮件失败", e);
        }


    }

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

        logger.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            //重复使用添加多个图片
            helper.addInline(rscId, res);
            mailSender.send(message);
            logger.info("发送带图片邮件成功");
        } catch (Exception e) {
            logger.error("发送带图片邮件失败", e);
        }
    }
}
