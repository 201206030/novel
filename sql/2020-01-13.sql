
DROP TABLE IF EXISTS `book_parse_log`;
CREATE TABLE `book_parse_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_url` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `book_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `score` float NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_key_bookurl` (`book_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


