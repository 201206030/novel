package io.github.xxyopen.novel.manager.message;

/**
 * 消息发送器接口，用来发送各种消息
 * <p>
 * 消息按类型分系统通知、邮件、短信、小程序通知等，按发送时机分注册成功消息、充值成功消息、活动通知消息、账户封禁消息、小说下架消息等
 *
 * @author xiongxiaoyang
 * @date 2023/3/25
 */
public interface MessageSender {

    /**
     * 发送消息，支持动态消息标题和动态消息内容
     *
     * @param toUserId 消息接收方的用户ID
     * @param args     用来动态生成消息标题和消息内容的参数列表
     */
    void sendMessage(Long toUserId, Object... args);

}
