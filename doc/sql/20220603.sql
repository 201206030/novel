DROP PROCEDURE IF EXISTS createBookChapterTable;
-- 创建小说章节表的存储过程
CREATE PROCEDURE createBookChapterTable()
BEGIN

	 -- 定义变量
     DECLARE i int DEFAULT 0;
	 DECLARE tableName char(13) DEFAULT NULL;

	 while i < 10 do

			set tableName = concat('book_chapter',i);

			set @stmt = concat('create table ',tableName,'(
					`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
					`book_id` bigint(20) unsigned NOT NULL COMMENT \'小说ID\',
					`chapter_num` smallint(5) unsigned NOT NULL COMMENT \'章节号\',
					`chapter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT \'章节名\',
					`word_count` int(10) unsigned NOT NULL COMMENT \'章节字数\',
					`is_vip` tinyint(3) unsigned NOT NULL DEFAULT \'0\' COMMENT \'是否收费;1-收费 0-免费\',
					`create_time` datetime DEFAULT NULL,
					`update_time` datetime DEFAULT NULL,
					PRIMARY KEY (`id`) USING BTREE,
					UNIQUE KEY `uk_bookId_chapterNum` (`book_id`,`chapter_num`) USING BTREE,
					UNIQUE KEY `pk_id` (`id`) USING BTREE,
					KEY `idx_bookId` (`book_id`) USING BTREE
				) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT=\'小说章节\'');
            prepare stmt from @stmt;
            execute stmt;
            deallocate prepare stmt;

            set i = i + 1;

    end while;

END;
call createBookChapterTable();


DROP PROCEDURE IF EXISTS createBookContentTable;
-- 创建小说内容表的存储过程
CREATE PROCEDURE createBookContentTable()
BEGIN

	 -- 定义变量
   DECLARE i int DEFAULT 0;
	 DECLARE tableName char(13) DEFAULT NULL;

	 while i < 10 do

			set tableName = concat('book_content',i);

			set @stmt = concat('create table ',tableName,'(
				`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT \'主键\',
				`chapter_id` bigint(20) unsigned NOT NULL COMMENT \'章节ID\',
				`content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT \'小说章节内容\',
				`create_time` datetime DEFAULT NULL,
				`update_time` datetime DEFAULT NULL,
				PRIMARY KEY (`id`) USING BTREE,
				UNIQUE KEY `uk_chapterId` (`chapter_id`) USING BTREE,
				UNIQUE KEY `pk_id` (`id`) USING BTREE
			) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT=\'小说内容\'');
            prepare stmt from @stmt;
            execute stmt;
            deallocate prepare stmt;

            set i = i + 1;

     end while;

END;
call createBookContentTable();


DROP PROCEDURE IF EXISTS copyBookChapterData;
-- 迁移小说章节数据的存储过程
CREATE PROCEDURE copyBookChapterData()
BEGIN

    -- 定义变量
    DECLARE s int DEFAULT 0;
    DECLARE chapterId bigint;
    DECLARE bookId bigint;
    DECLARE chapterNum smallint;
    DECLARE chapterName varchar(100);
    DECLARE wordCount int DEFAULT 0;
    DECLARE isVip tinyint(64) DEFAULT 0;
    DECLARE createTime datetime DEFAULT NULL;
    DECLARE updateTime datetime DEFAULT NULL;
	DECLARE tableNumber int DEFAULT 0;
	DECLARE tableName char(13) DEFAULT NULL;


    -- 定义游标
    DECLARE report CURSOR FOR select id,book_id,chapter_num, chapter_name, word_count, is_vip,create_time,update_time from book_chapter;

    -- 声明当游标遍历完后将标志变量置成某个值
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET s=1;

    -- 打开游标
    open report;

    -- 将游标中的值赋值给变量，注意：变量名不要和返回的列名同名，变量顺序要和sql结果列的顺序一致
    fetch report into chapterId,bookId,chapterNum, chapterName, wordCount,isVip,createTime,updateTime;

    -- 循环遍历
    while s<>1 do
        -- 执行业务逻辑
        set tableNumber = bookId % 10;
		set tableName = concat('book_chapter',tableNumber);
		set @stmt = concat('insert into ',tableName,'(`id`, `book_id`, `chapter_num`, `chapter_name`, `word_count`, `is_vip`, `create_time`, `update_time`) VALUES (',chapterId,', ',bookId,', ',chapterNum,', \'',chapterName,'\', ',wordCount,', ',isVip,', \'',createTime,'\', \'',updateTime,'\')');
        prepare stmt from @stmt;
        execute stmt;
        deallocate prepare stmt;

        fetch report into chapterId,bookId,chapterNum, chapterName, wordCount,isVip,createTime,updateTime;
    end while;
     -- 关闭游标
    close report;

END;
call copyBookChapterData();


DROP PROCEDURE IF EXISTS copyBookContentData;
-- 迁移小说内容数据的存储过程
CREATE PROCEDURE copyBookContentData()
BEGIN

    -- 定义变量
    DECLARE s int DEFAULT 0;
	DECLARE contentId bigint;
    DECLARE chapterId bigint;
    DECLARE bookContent mediumtext;
    DECLARE createTime datetime DEFAULT NULL;
    DECLARE updateTime datetime DEFAULT NULL;
	DECLARE tableNumber int DEFAULT 0;
    DECLARE tableName char(13) DEFAULT NULL;


    -- 定义游标
    DECLARE report CURSOR FOR select id,chapter_id,content,create_time,update_time from book_content;

    -- 声明当游标遍历完后将标志变量置成某个值
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET s=1;

    -- 打开游标
    open report;

    -- 将游标中的值赋值给变量，注意：变量名不要和返回的列名同名，变量顺序要和sql结果列的顺序一致
    fetch report into contentId,chapterId,bookContent,createTime,updateTime;

    -- 循环遍历
    while s<>1 do
        -- 执行业务逻辑
        set tableNumber = chapterId % 10;
		set tableName = concat('book_content',tableNumber);
        set bookContent = REPLACE(bookContent,'\'',"\\'");
		set @stmt = concat('insert into ',tableName,'(`id`, `chapter_id`, `content`) VALUES (',contentId,', ',chapterId,',\'',bookContent,'\')');

        prepare stmt from @stmt;
        execute stmt;
        deallocate prepare stmt;

        fetch report into contentId,chapterId,bookContent,createTime,updateTime;
    end while;
    -- 关闭游标
    close report;

END;
call copyBookContentData();