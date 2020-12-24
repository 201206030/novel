[![index]( https://s1.ax1x.com/2020/07/03/NOSA5q.jpg )](https://cloud.tencent.com/act/cps/redirect?redirect=1052&cps_key=736e609d66e0ac4e57813316cec6fd0b&from=console)

# 小说精品屋（老项目，已停止更新和维护，请使用下面推荐的新项目来替代）

#### 官网

https://xiongxyang.gitee.io/home/

#### 新项目：小说精品屋-plus（增强版，建站/学习推荐使用这个）

小说精品屋-plus专注于小说，去除了小说精品屋中的漫画模块，是一个多端（PC、WAP）阅读、功能完善的原创文学CMS系统，由前台门户系统、作家后台管理系统、平台后台管理系统、爬虫管理系统等多个子系统构成，支持会员充值、订阅模式、新闻发布和实时统计报表等功能，新书自动入库，老书自动更新。

小说精品屋-plus是在小说精品屋的基础上，重新进行了数据库设计、代码重构和功能增强，提升了程序整体的可读性和性能，增加了很多商用特性。 

GitHub仓库地址： https://github.com/201206030/novel-plus

Gitee仓库地址： https://gitee.com/novel_dev_team/novel-plus

#### 新项目：小说精品屋-微服务版（微服务版，学习推荐使用这个）

基于小说精品屋-plus构建的SpringCloud微服务小说门户平台。

GitHub仓库地址： https://github.com/201206030/novel-cloud

Gitee仓库地址： https://gitee.com/novel_dev_team/novel-cloud

#### 前言

安装前请先阅读完此文档，了解项目基础配置和模块功能，再根据安装文档安装项目，避免一些不必要的错误。

#### 安装文档

源码安装文档（适合有一定技术基础的人）：[点击前往](https://my.oschina.net/java2nb/blog/3145593)  

包安装文档（适合非技术人员）：[点击前往](https://my.oschina.net/java2nb/blog/3146627)  

宝塔安装教程：[点击前往](https://www.daniao.org/7822.html )

#### 项目介绍

小说精品屋是一个多平台（web、安卓app、微信小程序）、功能完善的小说弹幕网站，包含精品小说专区、轻小说专区和漫画专区。包括小说/漫画分类、小说/漫画搜索、小说/漫画排行、完本小说/漫画、小说/漫画评分、小说/漫画在线阅读、小说/漫画书架、小说/漫画阅读记录、小说下载、小说弹幕、小说/漫画自动爬取、小说内容自动分享到微博、邮件自动推广、链接自动推送到百度搜索引擎等功能。包含电脑端、移动端、微信小程序等多个平台，现已开源web端、安卓端、小程序端源码。 

#### 目录结构

novel-front ： 前台web网站源码（独立项目，按需安装） 

novel-admin ：平台后台管理系统源码（独立项目，按需安装） 

#### 软件架构
前台web网站架构：Springboot+Mybatis+Mysql+Ehcache+Thymeleaf+Layui

平台后台管理系统架构 : Springboot+Mybatis+Mysql+Redis+Thymeleaf+Layui+Bootstrap

#### 前台web网站截图

1. 电脑端（首页）

   ![index](https://static.oschina.net/uploads/img/202006/24151811_wIus.png)

2. 移动端（首页）

   ![index](https://static.oschina.net/uploads/img/202006/24151812_OOob.jpg)

3. 移动端（轻小说专区）

   ![index](https://static.oschina.net/uploads/img/202006/24151812_X6vD.jpg)

4. 移动端（动漫专区）

   ![index](https://static.oschina.net/uploads/img/202006/24151812_HK99.png)

5. 移动端（小说详情页）

   ![微信图片_20190904181558](https://static.oschina.net/uploads/img/202006/24151812_ZosF.png)

6. 移动端（目录页）

   ![QQ图片20191018161901](https://static.oschina.net/uploads/img/202006/24151812_Krva.png)

7. 移动端（小说阅读页）

   ![QQ图片20191018161901](https://static.oschina.net/uploads/img/202006/24151813_fDgT.png)

   8.电脑端（漫画阅读页）

   ![index](https://oscimg.oschina.net/oscnet/up-66339ecf71dd1db7745e3350170a46ca070.png)


#### 后台管理系统截图

1. 登录界面

   ![](./assets/login.png)

2. 爬虫管理界面

   ![](./assets/crawl_pic.png)

   3.爬虫配置界面。

   ![](./assets/crawl_config.png)

   3.小说管理页面。

   ![](./assets/novel_list.png)
   
   4.小说发布页面。
   
   ![](./assets/novel_pub.png)
   
   5.小说章节发布页面
   
   ![](./assets/chapter_pub.png)
   
   6.小说章节管理页面。
   
   ![](./assets/chapter_manager.png)



#### 微信小程序截图

![mini4](https://static.oschina.net/uploads/img/202006/24151814_fVcW.png)



#### 安卓App截图

![mini4](https://static.oschina.net/uploads/img/202006/24151814_Vvwg.png)

#### 安装说明

数据库安装：

1. 安装MySQL软件。
2. 修改MySQL`max_allowed_packet `配置（建议100M）。
3. 新建数据库books:create database books default character set utf8mb4 collate utf8mb4_general_ci 。
4. 执行sql/books.sql文件。

小说数据爬取的两种方式 ：

1.  运行script/crawlbook/crawlbook.bat脚本文件。（适用于本地多机器运行） 
2. 安装后台管理系统后，打开爬虫管理菜单，点击爬虫运行按钮。（适用于线上环境运行，会占用较多服务器资源）

平台后台管理系统安装（独立项目，按需安装）（**<u>后台代码已删除</u>**，爬虫功能请使用crawlbook.bat） ：

1. 修改application.yml文件中数据库配置。

2. 修改application.yml文件中Redis配置。

3. 修改application.yml文件中文件上传路径配置。

   ![](./assets/upload_config.png)

4. 启动程序，登录后台系统，运行爬虫程序爬取小说数据。

   ![](./assets/crawl_pic.png)

前台web网站安装（独立项目，按需安装） ：

1. 修改项目application.yml配置文件中的数据库配置。

   ![](./assets/database_config.png)

2. 修改项目application.yml配置文件中的首页本站推荐小说配置（修改的小说需要在数据库中存在）。

   ![](./assets/index_config.png)

3. 根据需求，修改项目application.yml配置文件中的爬取小说最低评分配置（建议和爬虫程序中的最低评分配置保持一致）。

   ![](./assets/score_config.png)

4. 根据需求，修改项目application.yml配置文件中的小说爬虫源配置。

   ![](./assets/craw_config.png)

5. 根据需求，修改项目application.yml配置文件中的图片保存方式。

   ![](./assets/pic_save_type.png)

6. 本地直接运行或使用maven插件打包成jar文件上传到服务器上。

7. `http://ip:port`访问首页。

8. `http://ip:port/books`访问精品小说模块。

9. `http://ip:port/book/searchSoftBook.html`访问轻小说模块。

10. `http://ip:port/book/searchMhBook.html`访问漫画模块。

**喜欢此项目的可以给我的GitHub和Gitee加个Star支持一下 。**

#### 演示地址（服务器成本过高，暂停演示地址）

[点击前往](http://47.106.243.172:8888)（前台）

[点击前往 ](http://47.106.243.172:8888) （后台）   (**后台爬虫程序运行会占用大量服务器资源，试用人数过多，服务器压力大，现暂停演示** )

演示账号：admin/admin123

#### 小程序二维码

![mini-code](./assets/mini-code.png)

#### 代码仓库

 GitHub仓库地址：  https://github.com/201206030/fiction_house 

 Gitee仓库地址：  https://gitee.com/novel_dev_team/fiction_house

#### QQ交流群

[点击前往官网查看](https://xiongxyang.gitee.io/home/service.htm)

#### 微信公众号（发布最新更新资讯）

![mini-code](https://s3.ax1x.com/2020/12/03/DoImOx.png)

#### 

#### 捐赠支持

开源项目不易，若此项目能得到你的青睐，可以捐赠服务器费用及支持作者持续开发与维护。 

![mini-code](https://s1.ax1x.com/2020/10/31/BUQJwq.png)


#### 备注

精品小说屋所有相关项目均已在开源中国公开，感兴趣的可进入[开源中国](https://www.oschina.net/p/fiction_house)按关键字`精品小说屋`搜索。

[![index]( https://s1.ax1x.com/2020/07/03/NOSuMF.jpg)](https://www.aliyun.com/minisite/goods?userCode=uf4nasee )

# 部分截图在github上可能无法正常显示，请下载到本地查看。
