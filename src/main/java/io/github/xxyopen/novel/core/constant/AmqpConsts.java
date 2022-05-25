package io.github.xxyopen.novel.core.constant;

/**
 * AMQP 相关常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/25
 */
public class AmqpConsts {

    /**
     * 小说信息改变 MQ
     * */
    public static class BookChangeMq{

        /**
         * 小说信息改变交换机
         * */
        public static final String EXCHANGE_NAME = "EXCHANGE-BOOK-CHANGE";

        /**
         * Elasticsearch book 索引更新的队列
         * */
        public static final String QUEUE_ES_UPDATE = "QUEUE-ES-BOOK-UPDATE";

        /**
         * Redis book 缓存更新的队列
         * */
        public static final String QUEUE_REDIS_UPDATE = "QUEUE-REDIS-BOOK-UPDATE";

    }

}
