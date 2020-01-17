
DROP TABLE IF EXISTS `book_update_time_log`;
CREATE TABLE `book_update_time_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_cat_id` int(11) NOT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_key_catid` (`book_cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;


INSERT INTO `book_update_time_log` VALUES ('1', '1', NOW());
INSERT INTO `book_update_time_log` VALUES ('2', '2', NOW());
INSERT INTO `book_update_time_log` VALUES ('3', '3', NOW());
INSERT INTO `book_update_time_log` VALUES ('4', '4', NOW());
INSERT INTO `book_update_time_log` VALUES ('5', '5', NOW());
INSERT INTO `book_update_time_log` VALUES ('6', '6', NOW());
INSERT INTO `book_update_time_log` VALUES ('7', '7', NOW());


ALTER  TABLE  book_parse_log  drop  INDEX  uq_key_bookurl;
