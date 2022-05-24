package io.github.xxyopen.novel.core.constant;

/**
 * elasticsearch 相关常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
public class EsConsts {

    private EsConsts() {
        throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * 小说索引
     * */
    public static class BookIndex{

        private BookIndex() {
            throw new IllegalStateException(SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG);
        }

        /**
         * 索引名
         * */
        public static final String INDEX_NAME = "book";

        /**
         * id
         */
        public static final String FIELD_ID =  "id";

        /**
         * 作品方向;0-男频 1-女频
         */
        public static final String FIELD_WORK_DIRECTION =  "workDirection";

        /**
         * 类别ID
         */
        public static final String FIELD_CATEGORY_ID = "categoryId";

        /**
         * 类别名
         */
        public static final String FIELD_CATEGORY_NAME = "categoryName";

        /**
         * 小说名
         */
        public static final String FIELD_BOOK_NAME = "bookName";

        /**
         * 作家id
         */
        public static final String FIELD_AUTHOR_ID = "authorId";

        /**
         * 作家名
         */
        public static final String FIELD_AUTHOR_NAME = "authorName";

        /**
         * 书籍描述
         */
        public static final String FIELD_BOOK_DESC = "bookDesc";

        /**
         * 评分;总分:10 ，真实评分 = score/10
         */
        public static final String FIELD_SCORE = "score";

        /**
         * 书籍状态;0-连载中 1-已完结
         */
        public static final String FIELD_BOOK_STATUS = "bookStatus";

        /**
         * 点击量
         */
        public static final String FIELD_VISIT_COUNT = "visitCount";

        /**
         * 总字数
         */
        public static final String FIELD_WORD_COUNT = "wordCount";

        /**
         * 评论数
         */
        public static final String FIELD_COMMENT_COUNT = "commentCount";

        /**
         * 最新章节ID
         */
        public static final String FIELD_LAST_CHAPTER_ID = "lastChapterId";

        /**
         * 最新章节名
         */
        public static final String FIELD_LAST_CHAPTER_NAME = "lastChapterName";

        /**
         * 最新章节更新时间
         */
        public static final String FIELD_LAST_CHAPTER_UPDATE_TIME = "lastChapterUpdateTime";

        /**
         * 是否收费;1-收费 0-免费
         */
        public static final String FIELD_IS_VIP = "isVip";
        
    }

}
