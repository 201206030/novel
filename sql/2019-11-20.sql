INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('212', '209', '发布管理', 'books/book', 'books:book:book', '1', '', '2', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('4087', '1', '212');
