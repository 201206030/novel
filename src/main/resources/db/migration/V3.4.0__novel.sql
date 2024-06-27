/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : novel

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 16/05/2022 18:47:34
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for author_code
-- ----------------------------
CREATE TABLE `author_code`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `invite_code`   varchar(100) NOT NULL COMMENT '邀请码',
    `validity_time` datetime     NOT NULL COMMENT '有效时间',
    `is_used`       tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否使用过;0-未使用 1-使用过',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_code` (`invite_code`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作家邀请码';

-- ----------------------------
-- Table structure for author_income
-- ----------------------------
CREATE TABLE `author_income`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `author_id`        bigint(20) unsigned NOT NULL COMMENT '作家ID',
    `book_id`          bigint(20) unsigned NOT NULL COMMENT '小说ID',
    `income_month`     date NOT NULL COMMENT '收入月份',
    `pre_tax_income`   int(10) unsigned NOT NULL DEFAULT '0' COMMENT '税前收入;单位：分',
    `after_tax_income` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '税后收入;单位：分',
    `pay_status`       tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '支付状态;0-待支付 1-已支付',
    `confirm_status`   tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '稿费确认状态;0-待确认 1-已确认',
    `detail`           varchar(255) DEFAULT NULL COMMENT '详情',
    `create_time`      datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='稿费收入统计';

-- ----------------------------
-- Table structure for author_income_detail
-- ----------------------------
CREATE TABLE `author_income_detail`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `author_id`      bigint(20) unsigned NOT NULL COMMENT '作家ID',
    `book_id`        bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '小说ID;0表示全部作品',
    `income_date`    date NOT NULL COMMENT '收入日期',
    `income_account` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订阅总额',
    `income_count`   int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订阅次数',
    `income_number`  int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订阅人数',
    `create_time`    datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='稿费收入明细统计';

-- ----------------------------
-- Table structure for author_info
-- ----------------------------
CREATE TABLE `author_info`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`        bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `invite_code`    varchar(20) NOT NULL COMMENT '邀请码',
    `pen_name`       varchar(20) NOT NULL COMMENT '笔名',
    `tel_phone`      varchar(20) DEFAULT NULL COMMENT '手机号码',
    `chat_account`   varchar(50) DEFAULT NULL COMMENT 'QQ或微信账号',
    `email`          varchar(50) DEFAULT NULL COMMENT '电子邮箱',
    `work_direction` tinyint(3) unsigned DEFAULT NULL COMMENT '作品方向;0-男频 1-女频',
    `status`         tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0：正常;1-封禁',
    `create_time`    datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_userId` (`user_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作者信息';

-- ----------------------------
-- Table structure for book_category
-- ----------------------------
CREATE TABLE `book_category`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `work_direction` tinyint(3) unsigned NOT NULL COMMENT '作品方向;0-男频 1-女频',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名',
    `sort`           tinyint(3) unsigned NOT NULL DEFAULT '10' COMMENT '排序',
    `create_time`    datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `pk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说类别';

-- ----------------------------
-- Table structure for book_chapter
-- ----------------------------
CREATE TABLE `book_chapter`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `book_id`      bigint(20) unsigned NOT NULL COMMENT '小说ID',
    `chapter_num`  smallint(5) unsigned NOT NULL COMMENT '章节号',
    `chapter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '章节名',
    `word_count`   int(10) unsigned NOT NULL COMMENT '章节字数',
    `is_vip`       tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否收费;1-收费 0-免费',
    `create_time`  datetime DEFAULT NULL,
    `update_time`  datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_bookId_chapterNum` (`book_id`,`chapter_num`) USING BTREE,
    UNIQUE KEY `pk_id` (`id`) USING BTREE,
    KEY            `idx_bookId` (`book_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1445988184596992001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说章节';

-- ----------------------------
-- Table structure for book_comment
-- ----------------------------
CREATE TABLE `book_comment`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `book_id`         bigint(20) unsigned NOT NULL COMMENT '评论小说ID',
    `user_id`         bigint(20) unsigned NOT NULL COMMENT '评论用户ID',
    `comment_content` varchar(512) NOT NULL COMMENT '评价内容',
    `reply_count`     int(10) unsigned NOT NULL DEFAULT '0' COMMENT '回复数量',
    `audit_status`    tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
    `create_time`     datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_bookId_userId` (`book_id`,`user_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说评论';

-- ----------------------------
-- Table structure for book_comment_copy1
-- ----------------------------
CREATE TABLE `book_comment_copy1`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `book_id`         bigint(20) unsigned NOT NULL COMMENT '评论小说ID',
    `user_id`         bigint(20) unsigned NOT NULL COMMENT '评论用户ID',
    `comment_content` varchar(512) NOT NULL COMMENT '评价内容',
    `reply_count`     int(10) unsigned NOT NULL DEFAULT '0' COMMENT '回复数量',
    `audit_status`    tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
    `create_time`     datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_bookId_userId` (`book_id`,`user_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说评论';

-- ----------------------------
-- Table structure for book_comment_reply
-- ----------------------------
CREATE TABLE `book_comment_reply`
(
    `id`            bigint(20) unsigned NOT NULL COMMENT '主键',
    `comment_id`    bigint(20) unsigned NOT NULL COMMENT '评论ID',
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '回复用户ID',
    `reply_content` varchar(512) NOT NULL COMMENT '回复内容',
    `audit_status`  tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说评论回复';

-- ----------------------------
-- Table structure for book_content
-- ----------------------------
CREATE TABLE `book_content`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `chapter_id`  bigint(20) unsigned NOT NULL COMMENT '章节ID',
    `content`     mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说章节内容',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_chapterId` (`chapter_id`) USING BTREE,
    UNIQUE KEY `pk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4256332 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说内容';

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
CREATE TABLE `book_info`
(
    `id`                       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `work_direction`           tinyint(3) unsigned DEFAULT NULL COMMENT '作品方向;0-男频 1-女频',
    `category_id`              bigint(20) unsigned DEFAULT NULL COMMENT '类别ID',
    `category_name`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类别名',
    `pic_url`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '小说封面地址',
    `book_name`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '小说名',
    `author_id`                bigint(20) unsigned NOT NULL COMMENT '作家id',
    `author_name`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '作家名',
    `book_desc`                varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍描述',
    `score`                    tinyint(3) unsigned NOT NULL COMMENT '评分;总分:10 ，真实评分 = score/10',
    `book_status`              tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '书籍状态;0-连载中 1-已完结',
    `visit_count`              bigint(20) unsigned NOT NULL DEFAULT '103' COMMENT '点击量',
    `word_count`               int(10) unsigned NOT NULL DEFAULT '0' COMMENT '总字数',
    `comment_count`            int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
    `last_chapter_id`          bigint(20) unsigned DEFAULT NULL COMMENT '最新章节ID',
    `last_chapter_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最新章节名',
    `last_chapter_update_time` datetime                                                     DEFAULT NULL COMMENT '最新章节更新时间',
    `is_vip`                   tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否收费;1-收费 0-免费',
    `create_time`              datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `update_time`              datetime                                                     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_bookName_authorName` (`book_name`,`author_name`) USING BTREE,
    UNIQUE KEY `pk_id` (`id`) USING BTREE,
    KEY                        `idx_createTime` (`create_time`) USING BTREE,
    KEY                        `idx_lastChapterUpdateTime` (`last_chapter_update_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1431630596354977793 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说信息';

-- ----------------------------
-- Table structure for home_book
-- ----------------------------
CREATE TABLE `home_book`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `type`        tinyint(3) unsigned NOT NULL COMMENT '推荐类型;0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐',
    `sort`        tinyint(3) unsigned NOT NULL COMMENT '推荐排序',
    `book_id`     bigint(20) unsigned NOT NULL COMMENT '推荐小说ID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='小说推荐';

-- ----------------------------
-- Table structure for home_friend_link
-- ----------------------------
CREATE TABLE `home_friend_link`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `link_name`   varchar(50)  NOT NULL COMMENT '链接名',
    `link_url`    varchar(100) NOT NULL COMMENT '链接url',
    `sort`        tinyint(3) unsigned NOT NULL DEFAULT '11' COMMENT '排序号',
    `is_open`     tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否开启;0-不开启 1-开启',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='友情链接';

-- ----------------------------
-- Table structure for news_category
-- ----------------------------
CREATE TABLE `news_category`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(20) NOT NULL COMMENT '类别名',
    `sort`        tinyint(3) unsigned NOT NULL DEFAULT '10' COMMENT '排序',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻类别';

-- ----------------------------
-- Table structure for news_content
-- ----------------------------
CREATE TABLE `news_content`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `news_id`     bigint(20) unsigned NOT NULL COMMENT '新闻ID',
    `content`     mediumtext NOT NULL COMMENT '新闻内容',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_newsId` (`news_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻内容';

-- ----------------------------
-- Table structure for news_info
-- ----------------------------
CREATE TABLE `news_info`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id`   bigint(20) unsigned NOT NULL COMMENT '类别ID',
    `category_name` varchar(50)  NOT NULL COMMENT '类别名',
    `source_name`   varchar(50)  NOT NULL COMMENT '新闻来源',
    `title`         varchar(100) NOT NULL COMMENT '新闻标题',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻信息';

-- ----------------------------
-- Table structure for pay_alipay
-- ----------------------------
CREATE TABLE `pay_alipay`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `out_trade_no`   varchar(64) NOT NULL COMMENT '商户订单号',
    `trade_no`       varchar(64) NOT NULL COMMENT '支付宝交易号',
    `buyer_id`       varchar(16) DEFAULT NULL COMMENT '买家支付宝账号 ID',
    `trade_status`   varchar(32) DEFAULT NULL COMMENT '交易状态;TRADE_SUCCESS-交易成功',
    `total_amount`   int(10) unsigned NOT NULL COMMENT '订单金额;单位：分',
    `receipt_amount` int(10) unsigned DEFAULT NULL COMMENT '实收金额;单位：分',
    `invoice_amount` int(10) unsigned DEFAULT NULL COMMENT '开票金额',
    `gmt_create`     datetime    DEFAULT NULL COMMENT '交易创建时间',
    `gmt_payment`    datetime    DEFAULT NULL COMMENT '交易付款时间',
    `create_time`    datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`),
    KEY              `uk_outTradeNo` (`out_trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付宝支付';

-- ----------------------------
-- Table structure for pay_wechat
-- ----------------------------
CREATE TABLE `pay_wechat`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `out_trade_no`     varchar(32) NOT NULL COMMENT '商户订单号',
    `transaction_id`   varchar(32) NOT NULL COMMENT '微信支付订单号',
    `trade_type`       varchar(16)  DEFAULT NULL COMMENT '交易类型;JSAPI-公众号支付 NATIVE-扫码支付 APP-APP支付 MICROPAY-付款码支付 MWEB-H5支付 FACEPAY-刷脸支付',
    `trade_state`      varchar(32)  DEFAULT NULL COMMENT '交易状态;SUCCESS-支付成功 REFUND-转入退款 NOTPAY-未支付 CLOSED-已关闭 REVOKED-已撤销（付款码支付） USERPAYING-用户支付中（付款码支付） PAYERROR-支付失败(其他原因，如银行返回失败)',
    `trade_state_desc` varchar(255) DEFAULT NULL COMMENT '交易状态描述',
    `amount`           int(10) unsigned NOT NULL COMMENT '订单总金额;单位：分',
    `payer_total`      int(10) unsigned DEFAULT NULL COMMENT '用户支付金额;单位：分',
    `success_time`     datetime     DEFAULT NULL COMMENT '支付完成时间',
    `payer_openid`     varchar(128) DEFAULT NULL COMMENT '支付者用户标识;用户在直连商户appid下的唯一标识',
    `create_time`      datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`),
    KEY                `uk_outTradeNo` (`out_trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='微信支付';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE `sys_log`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
    `username`    varchar(50)   DEFAULT NULL COMMENT '用户名',
    `operation`   varchar(50)   DEFAULT NULL COMMENT '用户操作',
    `time`        int(10) unsigned DEFAULT NULL COMMENT '响应时间',
    `method`      varchar(200)  DEFAULT NULL COMMENT '请求方法',
    `params`      varchar(5000) DEFAULT NULL COMMENT '请求参数',
    `ip`          varchar(64)   DEFAULT NULL COMMENT 'IP地址',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统日志';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE `sys_menu`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父菜单ID;一级菜单为0',
    `name`        varchar(50) NOT NULL COMMENT '菜单名称',
    `url`         varchar(200) DEFAULT NULL COMMENT '菜单URL',
    `type`        tinyint(3) unsigned NOT NULL COMMENT '类型;0-目录   1-菜单',
    `icon`        varchar(50)  DEFAULT NULL COMMENT '菜单图标',
    `sort`        tinyint(3) unsigned DEFAULT NULL COMMENT '排序',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE `sys_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(100) NOT NULL COMMENT '角色名称',
    `role_sign`   varchar(100) DEFAULT NULL COMMENT '角色标识',
    `remark`      varchar(100) DEFAULT NULL COMMENT '备注',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE `sys_role_menu`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `role_id`     bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `menu_id`     bigint(20) unsigned NOT NULL COMMENT '菜单ID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) NOT NULL COMMENT '用户名',
    `password`    varchar(50) NOT NULL COMMENT '密码',
    `name`        varchar(100) DEFAULT NULL COMMENT '真实姓名',
    `sex`         tinyint(3) unsigned DEFAULT NULL COMMENT '性别;0-男 1-女',
    `birth`       datetime     DEFAULT NULL COMMENT '出身日期',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(100) DEFAULT NULL COMMENT '手机号',
    `status`      tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态;0-禁用 1-正常',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE `sys_user_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `role_id`     bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与角色对应关系';

-- ----------------------------
-- Table structure for test
-- ----------------------------
CREATE TABLE `test`
(
    `id`    int(11) NOT NULL,
    `test`  tinyint(1) unsigned zerofill DEFAULT NULL,
    `test2` tinyint(4) unsigned zerofill DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_bookshelf
-- ----------------------------
CREATE TABLE `user_bookshelf`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`        bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `book_id`        bigint(20) unsigned NOT NULL COMMENT '小说ID',
    `pre_content_id` bigint(20) unsigned DEFAULT NULL COMMENT '上一次阅读的章节内容表ID',
    `create_time`    datetime DEFAULT NULL COMMENT '创建时间;',
    `update_time`    datetime DEFAULT NULL COMMENT '更新时间;',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_userId_bookId` (`user_id`,`book_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户书架';

-- ----------------------------
-- Table structure for user_comment
-- ----------------------------
CREATE TABLE `user_comment`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`         bigint(20) unsigned NOT NULL COMMENT '评论用户ID',
    `book_id`         bigint(20) unsigned NOT NULL COMMENT '评论小说ID',
    `comment_content` varchar(512) NOT NULL COMMENT '评价内容',
    `reply_count`     int(10) unsigned NOT NULL DEFAULT '0' COMMENT '回复数量',
    `audit_status`    tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
    `create_time`     datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_bookId_userId` (`book_id`,`user_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户评论';

-- ----------------------------
-- Table structure for user_comment_reply
-- ----------------------------
CREATE TABLE `user_comment_reply`
(
    `id`            bigint(20) unsigned NOT NULL COMMENT '主键',
    `comment_id`    bigint(20) unsigned NOT NULL COMMENT '评论ID',
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '回复用户ID',
    `reply_content` varchar(512) NOT NULL COMMENT '回复内容',
    `audit_status`  tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '审核状态;0-待审核 1-审核通过 2-审核不通过',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户评论回复';

-- ----------------------------
-- Table structure for user_consume_log
-- ----------------------------
CREATE TABLE `user_consume_log`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`      bigint(20) unsigned NOT NULL COMMENT '消费用户ID',
    `amount`       int(10) unsigned NOT NULL COMMENT '消费使用的金额;单位：屋币',
    `product_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '消费商品类型;0-小说VIP章节',
    `product_id`   bigint(20) unsigned DEFAULT NULL COMMENT '消费的的商品ID;例如：章节ID',
    `produc_name`  varchar(50) DEFAULT NULL COMMENT '消费的的商品名;例如：章节名',
    `produc_value` int(10) unsigned DEFAULT NULL COMMENT '消费的的商品值;例如：1',
    `create_time`  datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户消费记录';

-- ----------------------------
-- Table structure for user_feedback
-- ----------------------------
CREATE TABLE `user_feedback`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '反馈用户id',
    `content`     varchar(512) NOT NULL COMMENT '反馈内容',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户反馈';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
CREATE TABLE `user_info`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `username`        varchar(50)  NOT NULL COMMENT '登录名',
    `password`        varchar(100) NOT NULL COMMENT '登录密码-加密',
    `salt`            varchar(8)   NOT NULL COMMENT '加密盐值',
    `nick_name`       varchar(50)  DEFAULT NULL COMMENT '昵称',
    `user_photo`      varchar(100) DEFAULT NULL COMMENT '用户头像',
    `user_sex`        tinyint(3) unsigned DEFAULT NULL COMMENT '用户性别;0-男 1-女',
    `account_balance` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '账户余额',
    `status`          tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户状态;0-正常',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';

-- ----------------------------
-- Table structure for user_pay_log
-- ----------------------------
CREATE TABLE `user_pay_log`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '充值用户ID',
    `pay_channel`   tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '充值方式;0-支付宝 1-微信',
    `out_trade_no`  varchar(64)  NOT NULL COMMENT '商户订单号',
    `amount`        int(10) unsigned NOT NULL COMMENT '充值金额;单位：分',
    `product_type`  tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '充值商品类型;0-屋币 1-包年VIP',
    `product_id`    bigint(20) unsigned DEFAULT NULL COMMENT '充值商品ID',
    `product_name`  varchar(255) NOT NULL COMMENT '充值商品名;示例值：屋币',
    `product_value` int(10) unsigned DEFAULT NULL COMMENT '充值商品值;示例值：255',
    `pay_time`      datetime     NOT NULL COMMENT '充值时间',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户充值记录';

-- ----------------------------
-- Table structure for user_read_history
-- ----------------------------
CREATE TABLE `user_read_history`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`        bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `book_id`        bigint(20) unsigned NOT NULL COMMENT '小说ID',
    `pre_content_id` bigint(20) unsigned NOT NULL COMMENT '上一次阅读的章节内容表ID',
    `create_time`    datetime DEFAULT NULL COMMENT '创建时间;',
    `update_time`    datetime DEFAULT NULL COMMENT '更新时间;',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_userId_bookId` (`user_id`,`book_id`),
    UNIQUE KEY `pk_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户阅读历史';

SET
FOREIGN_KEY_CHECKS = 1;

