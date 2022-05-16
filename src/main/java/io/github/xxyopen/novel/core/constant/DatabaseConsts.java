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
     * 小说类别
     */
    public static class BookCategoryTable {

        @Getter
        public enum ColumnEnum {

            WORK_DIRECTION("work_direction");

            private String name;

            ColumnEnum(String name) {
                this.name = name;
            }

        }

    }

    /**
     * 小说表
     */
    public static class BookTable {

        @Getter
        public enum ColumnEnum {

            CATEGORY_ID("category_id"),
            VISIT_COUNT("visit_count"),
            LAST_CHAPTER_UPDATE_TIME("last_chapter_update_time");

            private String name;

            ColumnEnum(String name) {
                this.name = name;
            }

        }

    }

    /**
     * 小说章节表
     */
    public static class BookChapterTable {

        @Getter
        public enum ColumnEnum {

            BOOK_ID("book_id"),
            CHAPTER_NUM("chapter_num"),
            LAST_CHAPTER_UPDATE_TIME("last_chapter_update_time");

            private String name;

            ColumnEnum(String name) {
                this.name = name;
            }

        }

    }

    /**
     * 小说内容表
     */
    public static class BookContentTable {

        @Getter
        public enum ColumnEnum {

            CHAPTER_ID("chapter_id");

            private String name;

            ColumnEnum(String name) {
                this.name = name;
            }

        }

    }

    /**
     * 新闻内容表
     */
    public static class NewsContentTable {

        @Getter
        public enum ColumnEnum {

            NEWS_ID("news_id");

            private String name;

            ColumnEnum(String name) {
                this.name = name;
            }

        }

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
        LIMIT_30("limit 30"),
        LIMIT_500("limit 500");

        private String sql;

        SqlEnum(String sql) {
            this.sql = sql;
        }

    }

}
