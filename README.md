[![index]( https://youdoc.github.io/img/tencent.jpg )]( https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=736e609d66e0ac4e57813316cec6fd0b&from=console )

<p align="center">
    <a href='https://github.com/201206030/novel'><img alt="Github stars" src="https://img.shields.io/github/stars/201206030/novel?logo=github"></a>
    <a href='https://github.com/201206030/novel'><img alt="Github forks" src="https://img.shields.io/github/forks/201206030/novel?logo=github"></a>
    <a href='https://gitee.com/novel_dev_team/novel'><img alt="Gitee stars" src="https://gitee.com/novel_dev_team/novel/badge/star.svg?theme=gitee"></a>
    <a href='https://gitee.com/novel_dev_team/novel'><img alt="Gitee forks" src="https://gitee.com/novel_dev_team/novel/badge/fork.svg?theme=gitee"></a>
    <a href='https://gitcode.com/opennovel/novel'><img alt="GitCode star" src="https://gitcode.com/opennovel/novel/star/badge.svg"></a>
</p>

## 项目简介

novel 是一套基于时下**最新** Java 技术栈 Spring Boot 3 + Vue 3 开发的前后端分离**学习型**
小说项目，配备[保姆级教程](https://docs.xxyopen.com/course/novel)手把手教你**从零开始**开发上线一套生产级别的 Java
系统，由小说门户系统、作家后台管理系统、平台后台管理系统等多个子系统构成。包括小说推荐、作品检索、小说排行榜、小说阅读、小说评论、会员中心、作家专区、充值订阅、新闻发布等功能。

## 项目地址

- 后端项目（更新中）：[GitHub](https://github.com/201206030/novel) ｜ [码云](https://gitee.com/novel_dev_team/novel) ｜ [GitCode](https://gitcode.com/opennovel/novel)
- 前端项目（更新中）：[GitHub](https://github.com/201206030/novel-front-web) 
  ｜ [码云](https://gitee.com/novel_dev_team/novel-front-web) ｜ [GitCode](https://gitcode.com/opennovel/novel-front-web)
- 线上应用版：[GitHub](https://github.com/201206030/novel-plus) ｜ [码云](https://gitee.com/novel_dev_team/novel-plus)
- 微服务版：[GitHub](https://github.com/201206030/novel-cloud) ｜ [码云](https://gitee.com/novel_dev_team/novel-cloud)

## 开发环境

- MySQL 8.0
- Redis 7.0
- Elasticsearch 8.2.0（可选）
- RabbitMQ 3.10.2（可选）
- XXL-JOB 2.3.1（可选）
- JDK 21
- Maven 3.8
- IntelliJ IDEA（可选）
- Node 16.14

**注：Elasticsearch、RabbitMQ 和 XXL-JOB 默认关闭，可通过 application.yml 配置文件中相应的`enable`配置属性开启。**

## 后端技术选型

| 技术                  |      版本      | 说明                      | 官网                              |                                                            学习                                                            |
|---------------------|:------------:|-------------------------| ------------------------------------ |:------------------------------------------------------------------------------------------------------------------------:|
| Spring Boot         |    3.3.0     | 容器 + MVC 框架             | [进入](https://spring.io/projects/spring-boot) |                            [进入](https://docs.spring.io/spring-boot/docs/3.0.0/reference/html)                            |
| Spring AI           |    1.0.0-M6     | Spring 官方 AI 框架         | [进入](https://spring.io/projects/spring-ai) |                                                          [进入](https://docs.spring.io/spring-ai/reference/)                                                          |
| MyBatis             |    3.5.9     | ORM 框架                  | [进入](http://www.mybatis.org)               |                                    [进入](https://mybatis.org/mybatis-3/zh/index.html)                                     |
| MyBatis-Plus        |    3.5.3     | MyBatis 增强工具            | [进入](https://baomidou.com/)                |                                         [进入](https://baomidou.com/pages/24112f/)                                         |
| JJWT                |    0.11.5    | JWT 登录支持                | [进入](https://github.com/jwtk/jjwt)         |                                                            -                                                             |
| Lombok              |   1.18.24    | 简化对象封装工具                | [进入](https://github.com/projectlombok/lombok) |                                       [进入](https://projectlombok.org/features/all)                                       |
| Caffeine            |    3.1.0     | 本地缓存支持                  | [进入](https://github.com/ben-manes/caffeine)            |                               [进入](https://github.com/ben-manes/caffeine/wiki/Home-zh-CN)                                |
| Redis               |     7.0      | 分布式缓存支持                 | [进入](https://redis.io)                |                                               [进入](https://redis.io/docs)                                                |
| Redisson            |    3.17.4    | 分布式锁实现                  | [进入](https://github.com/redisson/redisson)               |                            [进入](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95)                            |
| MySQL               |     8.0      | 数据库服务                   | [进入](https://www.mysql.com)               |                     [进入](https://docs.oracle.com/en-us/iaas/mysql-database/doc/getting-started.html)                     |
| ShardingSphere-JDBC |    5.5.1     | 数据库分库分表支持               | [进入](https://shardingsphere.apache.org)               |                            [进入](https://shardingsphere.apache.org/document/5.1.1/cn/overview)                            |
| Elasticsearch       |    8.2.0     | 搜索引擎服务                  | [进入](https://www.elastic.co)               |                     [进入](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)                     |
| RabbitMQ            |    3.10.2    | 开源消息中间件                 | [进入](https://www.rabbitmq.com)               |                             [进入](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)                              |
| XXL-JOB             |    2.3.1     | 分布式任务调度平台               | [进入](https://www.xuxueli.com/xxl-job)               |                                          [进入](https://www.xuxueli.com/xxl-job)                                           |
| Sentinel            |    1.8.4     | 流量控制组件                  | [进入](https://github.com/alibaba/Sentinel)              |                            [进入](https://github.com/alibaba/Sentinel/wiki/%E4%B8%BB%E9%A1%B5)                             |
| Springdoc-openapi   |    2.0.0     | Swagger 3 接口文档自动生成      | [进入](https://github.com/springdoc/springdoc-openapi)              |                                               [进入](https://springdoc.org/)                                               |
| Spring Boot Admin   |   3.0.0-M1   | 应用管理和监控                 | [进入](https://github.com/codecentric/spring-boot-admin)              |                              [进入](https://codecentric.github.io/spring-boot-admin/3.0.0-M1)                              |
| Tomcat              | 10.1.24 | Spring Boot 默认内嵌 Web 容器 | [进入](https://tomcat.apache.org) |                                       [进入](https://tomcat.apache.org/tomcat-10.1-doc/index.html)                                       |
| Docker              |      -       | 应用容器引擎                  | [进入](https://www.docker.com/)             |                                                            -                                                             |
| Jenkins             |      -       | 自动化部署工具                 | [进入](https://github.com/jenkinsci/jenkins) |                                                            -                                                             |
| Sonarqube           |      -       | 代码质量控制                  | [进入](https://www.sonarqube.org/)          |                                                            -                                                             |

**注：更多热门新技术待集成。**

## 前端技术选型

| 技术               |  版本   | 说明                       | 官网                                |                        学习                         |
| :----------------- | :-----: | -------------------------- | --------------------------------------- | :-------------------------------------------------: |
| Vue.js        |  3.2.13  | 渐进式 JavaScript 框架 | [进入](https://vuejs.org)  |   [进入](https://staging-cn.vuejs.org/guide/introduction.html)    |
| Vue Router            |  4.0.15  | Vue.js 的官方路由                    | [进入](https://router.vuejs.org)                  | [进入](https://router.vuejs.org/zh/guide/) |
| axios       |  0.27.2  | 基于 promise 的网络请求库               | [进入](https://axios-http.com)                  |     [进入](https://axios-http.com/zh/docs/intro)      |
| element-plus               | 2.2.0  | 基于 Vue 3，面向设计师和开发者的组件库   | [进入](https://element-plus.org)            |   [进入](https://element-plus.org/zh-CN/guide/design.html)   |

## 编码规范

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
            |   +- annotation -- 自定义注解类
            |   +- aspect -- Spring AOP 切面
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

11. 接口文档

![img](https://youdoc.github.io/img/novel/SwaggerUI.png)

## 安装步骤

👉 [立即查看](https://docs.xxyopen.com/course/novel/#%E5%AE%89%E8%A3%85%E6%AD%A5%E9%AA%A4)

启动前请配置部署专用的 JWT 密钥，至少使用 32 字节随机值：

```bash
export NOVEL_JWT_SECRET="$(openssl rand -base64 32)"
```

## 联系我们

👉 [立即查看](https://novel.xxyopen.com/service.htm)

## 问题

### 为什么有 novel/novel-cloud 学习版？

最开始是没有学习版的，只有一个爬虫/原创小说项目（最终发展成为 [novel-plus](https://github.com/201206030/novel-plus)
项目），用户群体大部分是对小说有兴趣，想自建一个干净无广告的小说网站的个人和站长。

后面随着使用人数逐渐增加，想通过这个项目来学习 Java 技术的人数也多了起来，对这部分用户来说，之前的项目用来学习很困难，具体原因如下：

1. novel-plus 功能模块比较多，重复性的增删改查占了大部分，而用户时间是有限的，很难在有限的时间内筛选出对自己有帮助的功能模块来学习。
2. novel-plus 追求的是系统稳定，用户很难在其中学习到最新的技术。
3. novel-plus 代码规范性不够，受限于开发时间限制，代码开发时没有选择一个标准化的规范去参考。
4. novel-plus 文档缺失，由于功能比较多，整个系统的教程编写需要花费大量时间，即使教程最终上线成功，用户也不可能有那么多时间也没有意义去学习所有的功能。

最终，novel（单体架构） 和 novel-cloud（微服务架构）诞生了，这两个项目在保证核心流程完整的同时，从 novel-plus
中选用了一些有代表性的功能，使用最新技术栈（不间断地更新和集成新技术），在[保姆级教程](https://docs.xxyopen.com/course/novel)的帮助下，尽量保证每一个功能都能让你学到不重复的技术。

所以这两个项目我的重点是去堆技术而不是去堆功能，功能只是其中的辅助，堆太多的重复性增删改查功能没有意义，对学习的帮助也不大。

### 谁适合使用 novel/novel-cloud 学习版项目？

如果对下面任何一个问题你能回答 "是"：

1. 你没有项目经验，想学习如何从零开始开发上线一个生产级别的 Java 项目?
2. 你有项目经验，但是公司技术栈太落后，想学习最新的主流开发技术？

那么，本项目正是你需要的。

### 谁暂时还不适合使用 novel/novel-cloud 学习版项目？

如果对下面任何一个问题你能回答 "是"：

1. 你不懂 Java ？
2. 你只是想搭建一个小说网站使用？
3. 你想找一个完整的 Java 商用项目，有时间也有耐心去学习项目中的方方面面？

那么，太遗憾了，本项目暂时不适合你，请使用 [novel-plus](https://github.com/201206030/novel-plus)。
