package io.github.xxyopen.novel.core.constant;

/**
 * 缓存相关常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
public class CacheConsts {

    /**
     * 本项目 Redis 缓存前缀
     * */
    public static final String REDIS_CACHE_PREFIX = "Cache::Novel::";


    /**
     * Caffeine 缓存管理器
     * */
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    /**
     * Redis 缓存管理器
     * */
    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    /**
     * 首页小说推荐缓存
     * */
    public static final String HOME_BOOK_CACHE_NAME = "homeBookCache";

    /**
     * 首页友情链接缓存
     * */
    public static final String HOME_FRIEND_LINK_CACHE_NAME = "homeFriendLinkCache";

    /**
     * 缓存配置常量
     */
    public enum CacheEnum {

        HOME_BOOK_CACHE(1,HOME_BOOK_CACHE_NAME,0,1),

        HOME_FRIEND_LINK_CACHE(2,HOME_FRIEND_LINK_CACHE_NAME,1000,1)

        ;

        /**
         * 缓存类型 0-本地 1-本地和远程 2-远程
         */
        private int type;
        /**
         * 缓存的名字
         */
        private String name;
        /**
         * 失效时间（秒） 0-永不失效
         */
        private int ttl;
        /**
         * 最大容量
         */
        private int maxSize;

        CacheEnum(int type, String name, int ttl, int maxSize) {
            this.type = type;
            this.name = name;
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        public boolean isLocal() {
            return type <= 1;
        }

        public boolean isRemote() {
            return type >= 1;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }

        public int getMaxSize() {
            return maxSize;
        }

    }

}
