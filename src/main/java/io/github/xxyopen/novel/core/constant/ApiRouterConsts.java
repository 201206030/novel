package io.github.xxyopen.novel.core.constant;

/**
 * API 路由常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
public class ApiRouterConsts {

    private ApiRouterConsts() {
        throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * API请求路径前缀
     */
    public static final String API_URL_PREFIX = "/api";

    /**
     * 前台门户系统请求路径前缀
     */
    public static final String API_FRONT_URL_PREFIX = API_URL_PREFIX + "/front";

    /**
     * 作家管理系统请求路径前缀
     */
    public static final String API_AUTHOR_URL_PREFIX = API_URL_PREFIX + "/author";

    /**
     * 平台后台管理系统请求路径前缀
     */
    public static final String API_ADMIN_URL_PREFIX = API_URL_PREFIX + "/admin";

    /**
     * 首页模块请求路径前缀
     * */
    public static final String HOME_URL_PREFIX = "/home";

    /**
     * 首页模块请求路径前缀
     * */
    public static final String NEWS_URL_PREFIX = "/news";

    /**
     * 小说模块请求路径前缀
     * */
    public static final String BOOK_URL_PREFIX = "/book";

    /**
     * 会员模块请求路径前缀
     * */
    public static final String USER_URL_PREFIX = "/user";

    /**
     * 资源（图片/视频/文档）模块请求路径前缀
     * */
    public static final String RESOURCE_URL_PREFIX = "/resource";

    /**
     * 搜索模块请求路径前缀
     * */
    public static final String SEARCH_URL_PREFIX = "/search";

    /**
     * 前台门户首页API请求路径前缀
     */
    public static final String API_FRONT_HOME_URL_PREFIX = API_FRONT_URL_PREFIX + HOME_URL_PREFIX;

    /**
     * 前台门户新闻相关API请求路径前缀
     */
    public static final String API_FRONT_NEWS_URL_PREFIX = API_FRONT_URL_PREFIX + NEWS_URL_PREFIX;

    /**
     * 前台门户小说相关API请求路径前缀
     */
    public static final String API_FRONT_BOOK_URL_PREFIX = API_FRONT_URL_PREFIX + BOOK_URL_PREFIX;

    /**
     * 前台门户会员相关API请求路径前缀
     */
    public static final String API_FRONT_USER_URL_PREFIX = API_FRONT_URL_PREFIX + USER_URL_PREFIX;

    /**
     * 前台门户资源（图片/视频/文档）相关API请求路径前缀
     */
    public static final String API_FRONT_RESOURCE_URL_PREFIX = API_FRONT_URL_PREFIX + RESOURCE_URL_PREFIX;

    /**
     * 前台门户搜索相关API请求路径前缀
     * */
    public static final String API_FRONT_SEARCH_URL_PREFIX = API_FRONT_URL_PREFIX + SEARCH_URL_PREFIX;

}
