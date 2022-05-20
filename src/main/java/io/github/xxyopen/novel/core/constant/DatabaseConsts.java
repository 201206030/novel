package io.github.xxyopen.novel.core.constant;

import lombok.Getter;

/**
 * 数据库 常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
public class DatabaseConsts {


    /**
     * 用户信息表
     */
    public static class UserInfoTable {

        private UserInfoTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_USERNAME = "username";

    }

    /**
     * 用户反馈表
     */
    public static class UserFeedBackTable {

        private UserFeedBackTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_USER_ID = "user_id";

    }

    /**
     * 用户书架表
     */
    public static class UserBookshelfTable {

        private UserBookshelfTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_USER_ID = "user_id";

        public static final String COLUMN_BOOK_ID = "book_id";

    }

    /**
     * 作家信息表
     */
    public static class AuthorInfoTable {

        private AuthorInfoTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_USER_ID = "user_id";

    }

    /**
     * 小说类别表
     */
    public static class BookCategoryTable {

        private BookCategoryTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_WORK_DIRECTION = "work_direction";

    }

    /**
     * 小说表
     */
    public static class BookTable {

        private BookTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_CATEGORY_ID = "category_id";

        public static final String COLUMN_VISIT_COUNT = "visit_count";

        public static final String COLUMN_LAST_CHAPTER_UPDATE_TIME = "last_chapter_update_time";

    }

    /**
     * 小说章节表
     */
    public static class BookChapterTable {

        private BookChapterTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_BOOK_ID = "book_id";

        public static final String COLUMN_CHAPTER_NUM = "chapter_num";

        public static final String COLUMN_LAST_CHAPTER_UPDATE_TIME = "last_chapter_update_time";

    }

    /**
     * 小说内容表
     */
    public static class BookContentTable {

        private BookContentTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_CHAPTER_ID = "chapter_id";

    }

    /**
     * 小说评论表
     */
    public static class BookCommentTable {

        private BookCommentTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_BOOK_ID = "book_id";

    }

    /**
     * 新闻内容表
     */
    public static class NewsContentTable {

        private NewsContentTable() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        public static final String COLUMN_NEWS_ID = "news_id";

    }

    /**
     * 通用列枚举类
     */
    @Getter
    public enum CommonColumnEnum {

        ID("id"),
        SORT("sort"),
        CREATE_TIME("create_time"),
        UPDATE_TIME("update_time");

        private String name;

        CommonColumnEnum(String name) {
            this.name = name;
        }

    }


    /**
     * SQL语句枚举类
     */
    @Getter
    public enum SqlEnum {

        LIMIT_1("limit 1"),
        LIMIT_2("limit 2"),
        LIMIT_5("limit 5"),
        LIMIT_30("limit 30"),
        LIMIT_500("limit 500");

        private String sql;

        SqlEnum(String sql) {
            this.sql = sql;
        }

    }

}
