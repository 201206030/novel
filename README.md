# 小说精品屋

#### 介绍
该项目包括了一个小说网站代理和一个独立的小说弹幕网站，包括小说在线阅读、TXT下载、小说弹幕、小说自动爬取、小说内容自动分享到微博、链接自动推送到百度搜索引擎等功能。现公布web端代码，需要移动端和小程序代码的也可以找我。

#### 软件架构
Springboot+mybatis+Mysql+Ehcache


#### 安装教程

1. 使用maven插件的package命令将项目打包成jar文件
2. 将jar文件和项目根路径下的runSearchJava.sh一起上传到服务器
3. 使用chmod +x runSearchJava.sh命令使文件可执行
4. 运行runSearchJava.sh脚本即可，如果新浪微博登录cookie过期，走5、6步；否则到此结束
5. 如果新浪微博登录cookie失效，去浏览器network获取登录cookie值
6. 修改runSearchJava.sh脚本中的启动参数browser.cookieJVM的值为新获取的值并保存
7. kill掉Javach程序，重新运行runSearchJava.sh脚本即可

#### 项目截图

1. 电脑端

   ![index](./assets/index.png)

2. 手机端

   ![微信图片_20190904181558](./assets/微信图片_20190904181558.png)

3. 小程序

![mini4](./assets/mini4.png)

#### 演示地址

[点击前往](https://www.zinglizingli.xyz)

#### 小程序二维码

![mini-code](./assets/mini-code.png)



