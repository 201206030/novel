package io.github.xxyopen.novel.core.common.constant;

/**
 * API 路由常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
public interface ApiRouterConsts {

    /**
     * API请求路径前缀
     */
    String API_URL_PREFIX = "/api";

    /**
     * 前台门户系统请求路径前缀
     */
    String API_FRONT_URL_PREFIX = API_URL_PREFIX + "/front";

    /**
     * 作家管理系统请求路径前缀
     */
    String API_AUTHOR_URL_PREFIX = API_URL_PREFIX + "/author";

    /**
     * 平台后台管理系统请求路径前缀
     */
    String API_ADMIN_URL_PREFIX = API_URL_PREFIX + "/admin";

    /**
     * 首页模块请求路径前缀
     * */
    String HOME_URL_PREFIX = "/home";

    /**
     * 小说模块请求路径前缀
     * */
    String BOOK_URL_PREFIX = "/book";

    /**
     * 会员模块请求路径前缀
     * */
    String USER_URL_PREFIX = "/user";

    /**
     * 前台门户首页API请求路径前缀
     */
    String API_FRONT_HOME_URL_PREFIX = API_FRONT_URL_PREFIX + HOME_URL_PREFIX;

    /**
     * 前台门户小说相关API请求路径前缀
     */
    String API_FRONT_BOOK_URL_PREFIX = API_FRONT_URL_PREFIX + BOOK_URL_PREFIX;

    /**
     * 前台门户会员相关API请求路径前缀
     */
    String API_FRONT_USER_URL_PREFIX = API_FRONT_URL_PREFIX + USER_URL_PREFIX;

}
