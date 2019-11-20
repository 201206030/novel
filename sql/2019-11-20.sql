INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('212', '209', '发布管理', 'books/book', 'books:book:book', '1', '', '2', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('4087', '1', '212');


INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('203', '202', '订单管理', 'test/order', 'test:order:order', '3', '', '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('75', '73', '刪除', 'system/sysDept/remove', 'system:sysDept:remove', '2', NULL, '2', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('6', '3', '用户管理', 'sys/user/', 'sys:user:user', '3', 'fa fa-user', '3', '2017-08-10 14:12:11', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('7', '3', '角色管理', 'sys/role', 'sys:role:role', '3', 'fa fa-paw', '3', '2017-08-10 14:13:19', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('92', '91', '在线用户', 'sys/online', '', '3', 'fa fa-user', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('2', '3', '系统菜单', 'sys/menu/', 'sys:menu:menu', '3', 'fa fa-th-list', '2', '2017-08-09 22:55:15', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('27', '91', '系统日志', 'common/log', 'common:log', '3', 'fa fa-warning', '3', '2017-08-14 22:11:53', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('48', '77', '代码生成', 'common/generator', 'common:generator', '3', 'fa fa-code', '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('76', '73', '编辑', '/system/sysDept/edit', 'system:sysDept:edit', '2', NULL, '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('74', '73', '增加', '/system/sysDept/add', 'system:sysDept:add', '2', NULL, '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('73', '3', '部门管理', '/system/sysDept', 'system:sysDept:sysDept', '3', 'fa fa-users', '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('104', '77', 'swagger', '/swagger-ui.html', '', '3', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('57', '91', '运行监控', '/druid/index.html', '', '3', 'fa fa-caret-square-o-right', '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('71', '3', '文件管理', '/common/sysFile', 'common:sysFile:sysFile', '3', 'fa fa-folder-open', '2', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('81', '78', '删除', '/common/dict/remove', 'common:dict:remove', '2', '', '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('80', '78', '编辑', '/common/dict/edit', 'common:dict:edit', '2', NULL, '2', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('83', '78', '批量删除', '/common/dict/batchRemove', 'common:dict:batchRemove', '2', '', '4', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('79', '78', '增加', '/common/dict/add', 'common:dict:add', '2', NULL, '2', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('78', '3', '数据字典', '/common/dict', 'common:dict:dict', '3', 'fa fa-book', '3', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('12', '6', '新增', '', 'sys:user:add', '2', '', '3', '2017-08-14 10:51:35', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('202', '3', '测试管理', '', '', '3', 'fa fa-s15', '12', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('204', '203', '新增', '', 'test:order:add', '2', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('205', '203', '编辑', '', 'test:order:edit', '2', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('13', '6', '编辑', '', 'sys:user:edit', '2', '', '3', '2017-08-14 10:52:06', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('91', '3', '系统监控', '', '', '3', 'fa fa-video-camera', '4', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('206', '203', '删除', '', 'test:order:remove', '2', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('207', '203', '批量删除', '', 'test:order:batchRemove', '2', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('208', '203', '详情', '', 'test:order:detail', '2', '', '3', NULL, NULL);

INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('3', '3', '基础管理', '', '', '3', 'fa fa-bars', '3', '2017-08-09 22:49:47', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('77', '3', '研发工具', '', '', '3', 'fa fa-gear', '5', NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('21', '2', '编辑', '', 'sys:menu:edit', '2', '', '3', '2017-08-14 10:59:56', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('62', '7', '批量删除', '', 'sys:role:batchRemove', '2', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('61', '2', '批量删除', '', 'sys:menu:batchRemove', '2', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('56', '7', '删除', '', 'sys:role:remove', '2', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('55', '7', '编辑', '', 'sys:role:edit', '2', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('15', '7', '新增', '', 'sys:role:add', '2', '', '3', '2017-08-14 10:56:37', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('20', '2', '新增', '', 'sys:menu:add', '2', '', '3', '2017-08-14 10:59:32', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('26', '6', '重置密码', '', 'sys:user:resetPwd', '2', '', '3', '2017-08-14 17:28:34', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('24', '6', '批量删除', '', 'sys:user:batchRemove', '2', '', '3', '2017-08-14 17:27:18', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('22', '2', '删除', '', 'sys:menu:remove', '2', '', '3', '2017-08-14 11:00:26', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('14', '6', '删除', NULL, 'sys:user:remove', '2', NULL, '3', '2017-08-14 10:52:24', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('30', '27', '清空', NULL, 'sys:log:clear', '2', NULL, '3', '2017-08-14 22:31:02', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('29', '27', '删除', NULL, 'sys:log:remove', '2', NULL, '3', '2017-08-14 22:30:43', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('28', '27', '刷新', NULL, 'sys:log:list', '2', NULL, '3', '2017-08-14 22:30:22', NULL);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`, `gmt_create`, `gmt_modified`) VALUES ('25', '6', '停用', NULL, 'sys:user:disable', '2', NULL, '3', '2017-08-14 17:27:43', NULL);


INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('123', '玄幻奇幻', '1', 'novel_category', '小说分类', '1', NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('124', '武侠仙侠', '2', 'novel_category', '小说分类', '2', NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('125', '都市言情', '3', 'novel_category', '小说分类', '3', NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('126', '历史军事', '4', 'novel_category', '小说分类', '4', NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('127', '科幻灵异', '5', 'novel_category', '小说分类', '5', NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('128', '网游竞技', '6', 'novel_category', '小说分类', '6', NULL, NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `sys_dict` (`id`, `name`, `value`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('129', '女生频道', '7', 'novel_category', '小说分类', '7', NULL, NULL, NULL, NULL, NULL, '', NULL);



