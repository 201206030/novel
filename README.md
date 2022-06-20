[![index]( https://s1.ax1x.com/2022/05/17/O5tgbR.png )]( https://curl.qcloud.com/kgMaOjoq )

[![Github stars](https://img.shields.io/github/stars/201206030/novel?logo=github)](https://github.com/201206030/novel)
[![Github forks](https://img.shields.io/github/forks/201206030/novel?logo=github)](https://github.com/201206030/novel)
[![Gitee star](https://gitee.com/novel_dev_team/novel/badge/star.svg?theme=gitee)](https://gitee.com/novel_dev_team/novel)
[![Gitee fork](https://gitee.com/novel_dev_team/novel/badge/fork.svg?theme=gitee)](https://gitee.com/novel_dev_team/novel)
<a href="https://github.com/201206030/novel"><img src="https://visitor-badge.glitch.me/badge?page_id=201206030.novel" alt="visitors"></a>
## 项目简介

novel 是一套基于时下**最新** Java 技术栈 Spring Boot 3 + Vue 3 开发的前后端分离的**学习型**小说项目，配备详细的项目教程手把手教你**从零开始**开发上线一个生产级别的 Java 系统，由小说门户系统、作家后台管理系统、平台后台管理系统、爬虫管理系统等多个子系统构成。包括小说推荐、作品检索、小说排行榜、小说阅读、小说评论、充值订阅、新闻发布等功能。

## 项目地址

- 后端项目（更新中）：[GitHub](https://github.com/201206030/novel) ｜ [码云](https://gitee.com/novel_dev_team/novel)
- 前端项目（更新中）：[GitHub](https://github.com/201206030/novel-front-web) ｜ [码云](https://gitee.com/novel_dev_team/novel-front-web)
- 线上应用版：[GitHub](https://github.com/201206030/novel-plus) ｜ [码云](https://gitee.com/novel_dev_team/novel-plus) ｜[演示地址](http://47.106.243.172:8888/)
- 微服务版：[GitHub](https://github.com/201206030/novel-cloud) ｜ [码云](https://gitee.com/novel_dev_team/novel-cloud)

## 开发环境

- MySQL 8.0
- Redis 7.0
- Elasticsearch 8.2.0（可选）
- RabbitMQ 3.10.2（可选）
- XXL-JOB 2.3.1（可选）
- JDK 17
- Maven 3.8
- IntelliJ IDEA 2021.3（可选）
- Node 16.14

**注：Elasticsearch、RabbitMQ 和 XXL-JOB 默认关闭，可通过 application.yml 配置文件中相应的`enable`配置属性开启。**

## 后端技术选型

| 技术                  |       版本       | 说明                  | 官网                                |                                           学习                                            |
|---------------------|:--------------:|---------------------| --------------------------------------- |:---------------------------------------------------------------------------------------:|
| Spring Boot         | 3.0.0-SNAPSHOT | 容器 + MVC 框架         | https://spring.io/projects/spring-boot  |                   [进入](https://youdoc.github.io/course/novel/11.html)                   |
| MyBatis             |     3.5.9      | ORM 框架              | http://www.mybatis.org                  |                    [进入](https://mybatis.org/mybatis-3/zh/index.html)                    |
| MyBatis-Plus        |     3.5.1      | MyBatis 增强工具        | https://baomidou.com/                   |                        [进入](https://baomidou.com/pages/24112f/)                         |
| JJWT                |     0.11.5     | JWT 登录支持            | https://github.com/jwtk/jjwt            |                                            -                                            |
| Lombok              |    1.18.24     | 简化对象封装工具            | https://github.com/projectlombok/lombok |                      [进入](https://projectlombok.org/features/all)                       |
| Caffeine            |     3.1.0      | 本地缓存支持              | https://github.com/ben-manes/caffeine                |               [进入](https://github.com/ben-manes/caffeine/wiki/Home-zh-CN)               |
| Redis               |      7.0       | 分布式缓存支持             | https://redis.io                   |                               [进入](https://redis.io/docs)                               |
| MySQL               |      8.0       | 数据库服务               | https://www.mysql.com                   |    [进入](https://docs.oracle.com/en-us/iaas/mysql-database/doc/getting-started.html)     |
| Redisson            |      3.17.4       | 分布式锁实现              | https://github.com/redisson/redisson                   |                               [进入](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95)                               |
| ShardingSphere-JDBC |      5.1.1       | 数据库分库分表支持           | https://shardingsphere.apache.org                   |           [进入](https://shardingsphere.apache.org/document/5.1.1/cn/overview)            |
| Elasticsearch       |     8.2.0      | 搜索引擎服务              | https://www.elastic.co                   |    [进入](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)     |
| RabbitMQ            |     3.10.2     | 开源消息中间件             | https://www.rabbitmq.com                   |             [进入](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)             |
| XXL-JOB             |     2.3.1      | 分布式任务调度平台           | https://www.xuxueli.com/xxl-job                   |                          [进入](https://www.xuxueli.com/xxl-job)                          |
| Sentinel            |     1.8.4      | 流量控制组件              | https://github.com/alibaba/Sentinel                  |            [进入](https://github.com/alibaba/Sentinel/wiki/%E4%B8%BB%E9%A1%B5)            |
| Spring Boot Admin   |     3.0.0-M1      | 应用管理和监控             | https://github.com/codecentric/spring-boot-admin                  |             [进入](https://codecentric.github.io/spring-boot-admin/3.0.0-M1)              |
| Undertow            |  2.2.17.Final  | Java 开发的高性能 Web 服务器 | https://undertow.io |                      [进入](https://undertow.io/documentation.html)                       |
| Docker              |       -        | 应用容器引擎              | https://www.docker.com/                 |                                            -                                            |
| Jenkins             |       -        | 自动化部署工具             | https://github.com/jenkinsci/jenkins    |                                            -                                            |
| Sonarqube           |       -        | 代码质量控制              | https://www.sonarqube.org/              |                                            -                                            |

**注：更多热门新技术待集成。**
## 前端技术选型

| 技术               |  版本   | 说明                       | 官网                                |                        学习                         |
| :----------------- | :-----: | -------------------------- | --------------------------------------- | :-------------------------------------------------: |
| Vue.js        |  3.2.13  | 渐进式 JavaScript 框架 | https://v3.cn.vuejs.org  |   [进入](https://v3.cn.vuejs.org/guide/introduction.html)    |
| Vue Router            |  4.0.15  | Vue.js 的官方路由                    | https://router.vuejs.org/zh/index.html                  | [进入](https://router.vuejs.org/zh/guide/) |
| axios       |  0.27.2  | 基于 promise 的网络请求库               | https://axios-http.com/zh                  |     [进入](https://axios-http.com/zh/docs/intro)      |
| element-plus               | 2.2.0  | 基于 Vue 3，面向设计师和开发者的组件库   | https://element-plus.org/zh-CN/            |   [进入](https://element-plus.org/zh-CN/guide/design.html)   |

##  编码规范

- 规范方式：严格遵守阿里编码规约。
- 命名统一：简介最大程度上达到了见名知意。
- 分包明确：层级分明可快速定位到代码位置。
- 注释完整：描述性高大量减少了开发人员的代码阅读工作量。
- 工具规范：使用统一jar包避免出现内容冲突。
- 代码整洁：可读性、维护性高。
- 依赖版本：所有依赖均使用当前最新可用版本以便新技术学习。

## 包结构

```
io
 +- github
     +- xxyopen   
        +- novel
            +- NovelApplication.java -- 项目启动类
            |
            +- core -- 项目核心模块，包括各种工具、配置和常量等
            |   +- common -- 业务无关的通用模块
            |   |   +- exception -- 通用异常处理
            |   |   +- constant -- 通用常量   
            |   |   +- req -- 通用请求数据格式封装，例如分页请求数据  
            |   |   +- resp -- 接口响应工具及响应数据格式封装 
            |   |   +- util -- 通用工具   
            |   | 
            |   +- auth -- 用户认证授权相关
            |   +- config -- 业务相关配置
            |   +- constant -- 业务相关常量         
            |   +- filter -- 过滤器 
            |   +- interceptor -- 拦截器
            |   +- json -- JSON 相关的包，包括序列化器和反序列化器
            |   +- task -- 定时任务
            |   +- util -- 业务相关工具 
            |   +- wrapper -- 装饰器
            |
            +- dto -- 数据传输对象，包括对各种 Http 请求和响应数据的封装
            |   +- req -- Http 请求数据封装
            |   +- resp -- Http 响应数据封装
            |
            +- dao -- 数据访问层，与底层 MySQL 进行数据交互
            +- manager -- 通用业务处理层，对第三方平台封装、对 Service 层通用能力的下沉以及对多个 DAO 的组合复用 
            +- service -- 相对具体的业务逻辑服务层  
            +- controller -- 主要是处理各种 Http 请求，各类基本参数校验，或者不复用的业务简单处理，返回 JSON 数据等
            |   +- front -- 小说门户相关接口
            |   +- author -- 作家管理后台相关接口
            |   +- admin -- 平台管理后台相关接口
            |   +- app -- app 接口
            |   +- applet -- 小程序接口
            |   +- open -- 开放接口，供第三方调用 
```

## 截图

1. 首页

![img](https://s3.ax1x.com/2020/12/27/r5400A.png)

2. 分类索引页

![img](https://oscimg.oschina.net/oscnet/up-d0b2e03129bfae47b8bb96a491b73d383c5.png)

3. 搜索页

![img](https://s3.ax1x.com/2020/12/27/r5TO8x.png)

![img](https://oscimg.oschina.net/oscnet/up-ed5f689557718924acac76bc3ebca36afcb.png)

4. 排行榜

![img](https://oscimg.oschina.net/oscnet/up-78d5a68586cd92a86c669311f414508f922.png)

5. 详情页

![img](https://oscimg.oschina.net/oscnet/up-8be2495a2869f93626b0c9c1df6f329747a.png)

6. 阅读页

![img](https://oscimg.oschina.net/oscnet/up-517c84148d2db8e11717a8bbecc57fa1be7.png)

7. 用户中心

![img](https://oscimg.oschina.net/oscnet/up-805a30e7a663a3fd5cb39a7ea26bc132a01.png)

8. 充值

![img](https://oscimg.oschina.net/oscnet/up-5a601b0b3af3224d0bebcfe12fc15075d34.png)

![img](https://oscimg.oschina.net/oscnet/up-face25d02c07b05b2ce954cc4bf4ee6a0cc.png)

9. 作家专区

![img](https://oscimg.oschina.net/oscnet/up-30766372cc7f56480ff1d7d55198204f6ea.png)

![img](https://s3.ax1x.com/2020/11/17/DVFiQI.png)

![img](https://s1.ax1x.com/2020/11/09/B7X5oF.png)

![img](https://s1.ax1x.com/2020/11/09/B7XLsx.png)

10. 购买

![img](https://oscimg.oschina.net/oscnet/up-ce0f585efd82a9670335f118cf5897c85e9.png)

![img](https://oscimg.oschina.net/oscnet/up-f849960f4c1303fea77d26e64fc505a7180.png)


## 安装步骤

此安装步骤的前提是需要保证上一节的开发环境可用。

- 下载后端源码

```
git clone https://gitee.com/novel_dev_team/novel.git
```

- 数据库文件导入

    1. 新建数据库（建议 novel）

    2. 解压后端源码`doc/sql/novel.sql.zip`压缩包，得到数据库结构文件`novel_struc.sql`和数据库小说数据文件`novel_data.sql`

    3. 导入`novel_struct.sql`数据库结构文件

    4. 导入`novel_data.sql`数据库小说数据文件

- novel 后端服务安装

    1. 修改`src/resources/application.yml`配置文件中的数据源配置

    ```
    spring:
        datasource:
            url: jdbc:mysql://localhost:3306/novel_test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
            username: root
            password: test123456
    ```

    2. 修改`src/resources/application.yml` 和 `src/resources/redisson.yml` 配置文件中的`redis`连接配置

    ```
    spring:
        redis:
            host: 127.0.0.1
            port: 6379
            password: 123456
    ```

    ```
    singleServerConfig:
       address: "redis://127.0.0.1:6379"
       password: 123456
    ```

    3. 项目根目录下运行如下命令来启动后端服务（有安装 IDE 的可以导入源码到 IDE 中运行）

    ```
    mvn spring-boot:run
    ```

    4. 根据前后端的实际部署情况，修改`application.yml`中的跨域配置（默认情况可忽略此步骤）

- 下载前端前台门户系统源码

```
git clone https://gitee.com/novel_dev_team/novel-front-web.git
```

- novel-front-web 前端前台门户系统安装

    1. 根据前后端的实际部署情况，修改`.env.development`中的`VUE_APP_BASE_API_URL`属性（默认情况可忽略此步骤）

    2. `yarn`安装

    ```
    npm install -g yarn
    ```

    3. 项目根目录下运行如下命令来安装项目依赖

    ```
    yarn install
    ```
    4. 项目根目录下运行如下命令启动

    ```
    yarn serve
    ```
    5. 浏览器通过`http://localhost:1024`来访问

## 项目教程

[手把手教你从零开始开发上线一个生产级别的小说系统](https://youdoc.github.io/course/novel/3.html)
    
## 公众号

- 关注公众号接收`项目`和`文档`的更新动态

- 加微信群学习交流，公众号后台回复「**微信群**」即可

- 回复「**资料**」获取`Java 学习面试资料`

- 回复「**笔记**」获取`Spring Boot 3 学习笔记`

![xxyopen](https://youdoc.gitee.io/img/qrcode_for_gh.jpg)

## 赞赏支持

开源项目不易，若此项目能得到你的青睐，那么你可以赞赏支持作者持续开发与维护。

- 更完善的文档教程
- 服务器的费用也是一笔开销
- 为用户提供更好的开发环境
- 一杯咖啡

![mini-code](https://s1.ax1x.com/2020/10/31/BUQJwq.png)
