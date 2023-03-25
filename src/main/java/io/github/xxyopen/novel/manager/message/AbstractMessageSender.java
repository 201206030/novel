package io.github.xxyopen.novel.manager.message;

/**
 * 抽象的消息发送器
 * <p>
 * 遵循松耦合的设计原则，所有的属性都使用构造函数注入，与 Spring 框架解藕
 * <p>
 * 所有的消息发送器既可以注册到 Spring 容器中，作为 Spring 的一个组件使用，也可以直接通过 new 对象的方式使用
 * <p>
 * 每种类型的消息发送时机可能都不一样，不同类型和发送时机的消息格式可能也不一样，所以由各个子类去拓展消息的格式
 *
 * @author xiongxiaoyang
 * @date 2023/3/24
 */
public abstract class AbstractMessageSender implements MessageSender {

    private static final String PLACEHOLDER = "{}";

    /**
     * 定义消息发送的模版，子类不能修改此模版
     */
    @Override
    public final void sendMessage(Long toUserId, Object... args) {
        // 1.获取消息标题模版
        String titleTemplate = getTitleTemplate();
        // 2.获取消息内容模版
        String contentTemplate = getContentTemplate();
        // 3.解析消息模版，得到最终需要发送的消息标题
        String title = resolveTitle(titleTemplate, args);
        // 4.解析消息内容，得到最终需要发送的消息内容
        String content = resolveContent(contentTemplate, args);
        // 5.发送消息
        sendMessage(toUserId, title, content);
    }

    /**
     * 发送消息，具体发送到哪里由子类决定
     *
     * @param toUserId       消息接收方的用户ID
     * @param messageTitle   消息标题
     * @param messageContent 消息内容
     */
    protected abstract void sendMessage(Long toUserId, String messageTitle, String messageContent);

    /**
     * 获取消息标题的模版，具体如何制定模版由子类决定
     *
     * @return 消息标题
     */
    protected abstract String getTitleTemplate();

    /**
     * 获取消息内容的模版，具体如何制定模版由子类决定
     *
     * @return 消息内容
     */
    protected abstract String getContentTemplate();

    /**
     * 通过给定的参数列表解析消息标题模版，默认固定标题，不需要解析，可以由子类来拓展它的功能
     *
     * @param titleTemplate 消息标题模版
     * @param arguments     用来解析的参数列表
     * @return 解析后的消息标题
     */
    protected String resolveTitle(String titleTemplate, Object... arguments) {
        return titleTemplate;
    }

    /**
     * 通过给定的参数列表解析消息内容模版，默认实现是使用参数列表来替换消息内容模版中的占位符，可以由子类来拓展它的功能
     * <p>
     * 子类可以根据第一个/前几个参数去数据库中查询动态内容，然后重组参数列表
     *
     * @param contentTemplate 消息内容模版
     * @param args            用来解析的参数列表
     * @return 解析后的消息内容
     */
    protected String resolveContent(String contentTemplate, Object... args) {
        if (args.length > 0) {
            StringBuilder formattedContent = new StringBuilder(contentTemplate);
            for (Object arg : args) {
                int start = formattedContent.indexOf(PLACEHOLDER);
                formattedContent.replace(start, start + PLACEHOLDER.length(),
                    String.valueOf(arg));
            }
            return formattedContent.toString();
        }
        return contentTemplate;
    }

}
