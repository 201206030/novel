/*
Navicat MySQL Data Transfer

Source Server         : 148.70.59.92
Source Server Version : 80013
Source Host           : 148.70.59.92:3306
Source Database       : books

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-10-23 17:43:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `catId` int(10) DEFAULT NULL,
  `pic_url` varchar(200) NOT NULL,
  `book_name` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `book_desc` varchar(2000) NOT NULL,
  `score` float NOT NULL,
  `book_status` varchar(10) NOT NULL,
  `visit_count` bigint(20) DEFAULT '103',
  `update_time` datetime NOT NULL,
  `soft_cat` int(10) DEFAULT '0',
  `soft_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_uniq_name_author` (`book_name`,`author`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6117 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for book_content
-- ----------------------------
DROP TABLE IF EXISTS `book_content`;
CREATE TABLE `book_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) NOT NULL,
  `index_id` bigint(20) DEFAULT NULL,
  `index_num` int(5) NOT NULL,
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_bookid_indexnum` (`book_id`,`index_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3148778 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for book_index
-- ----------------------------
DROP TABLE IF EXISTS `book_index`;
CREATE TABLE `book_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) NOT NULL,
  `index_num` int(5) NOT NULL,
  `index_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_bookid_indexNum` (`book_id`,`index_num`)
) ENGINE=InnoDB AUTO_INCREMENT=3563133 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `sort` tinyint(2) NOT NULL DEFAULT '10',
  `get_url` varchar(100) DEFAULT NULL,
  `req_url` varchar(100) DEFAULT '本站请求的URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for screen_bullet
-- ----------------------------
DROP TABLE IF EXISTS `screen_bullet`;
CREATE TABLE `screen_bullet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_id` bigint(20) NOT NULL,
  `screen_bullet` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `key_content_id` (`content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_ref_book
-- ----------------------------
DROP TABLE IF EXISTS `user_ref_book`;
CREATE TABLE `user_ref_book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `book_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
