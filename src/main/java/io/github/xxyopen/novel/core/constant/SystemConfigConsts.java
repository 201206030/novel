package io.github.xxyopen.novel.core.constant;

/**
 * 系统配置相关常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
public class SystemConfigConsts {

    private SystemConfigConsts() {
        throw new IllegalStateException("Constant class");
    }

    /**
     * 前台门户系统标识
     * */
    public static final String NOVEL_FRONT_KEY = "front";

    /**
     * 作家管理系统标识
     * */
    public static final String NOVEL_AUTHOR_KEY = "author";

    /**
     * 后台管理系统标识
     * */
    public static final String NOVEL_ADMIN_KEY = "admin";

    /**
     * 小说前台门户系统域
     * */
    public static final String NOVEL_FRONT_WEB_ORIGIN = "http://localhost:1024";

}
