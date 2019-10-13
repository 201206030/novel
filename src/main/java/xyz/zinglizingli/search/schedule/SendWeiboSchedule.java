package xyz.zinglizingli.search.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.search.cache.CommonCacheUtil;
import xyz.zinglizingli.search.utils.RestTemplateUtil;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SendWeiboSchedule {

    @Autowired
    private CommonCacheUtil cacheUtil;

    @Autowired
    private BookService bookService;

    private Logger log = LoggerFactory.getLogger(SendWeiboSchedule.class);

    private boolean isExcuting = false;//是否正在执行

    private long excuteNum = 0;

    @Value("${search.schedule.isRunExcute}")
    private String isRunExcute;//是否在运行时就执行sendAtNight定时器

    @Value("${browser.cookie}")
    private String cookieStr;

    private static final String BOOKNAME_CACHE_PREFIX = "bookName_Cache_Prefix:";


    @Scheduled(fixedRate = 1000 * 60 * 35)
    public void sendAtDay() {
        log.debug("sendWeoboSchedule执行中。。。。。。。。。。。。");
        if (!isExcuting) {
            isExcuting = true;
            excuteNum++;
            //long sleepMillis = 1000 * 60 * 5;
            long sleepMillis = 1000 * 60 * 5;
            try {

                String name;
                String desc;
                String author;
                String bookNum;
                String resultCode;
                long realSleepMillis;

                RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");

                //发送数据库中的一篇文章
                Map<String, Object> dataMap = bookService.queryNewstBook();
                log.debug("dataMap大小：" + dataMap.size());
                if (dataMap.size() > 1) {
                    Book book = (Book) dataMap.get("book");
                    String newstIndexName = (String) dataMap.get("newstIndexName");
                    name = newstIndexName + "_" + book.getBookName();
                    desc = book.getBookDesc();
                    author = book.getAuthor();
                    bookNum = new Random().nextInt(100) + "";
                    realSleepMillis = sleepMillis + (new Random().nextInt(15)) * 60 * 1000;
                    log.debug("发送微博书籍名：" + book.getBookName());
                    if (!name.equals(cacheUtil.get(BOOKNAME_CACHE_PREFIX + name))) {
                        resultCode = sendOneSiteWeibo(restTemplate, book.getBookName(), newstIndexName, author, desc, "精品小说楼", bookNum, "https://www.zinglizingli.xyz/book/" + book.getId() + ".html");
                        log.debug("发送微博书籍名：" + book.getBookName() + " 状态码：" + resultCode);
                        if ("{\"code\":\"A00006\"}".equals(resultCode)) {
                            cacheUtil.set(BOOKNAME_CACHE_PREFIX + name, name, 60 * 60 * 24 * 30);
                        }
                        Thread.sleep(realSleepMillis + 32);
                    }
                }


                //分享喜羊羊小说网
                String url2 = "http://m.zinglizingli.xyz/class/0/1.html";

                ResponseEntity<String> forEntity2 = restTemplate.getForEntity(url2, String.class);


                String forObject2 = forEntity2.getBody();
                Pattern pattern = Pattern.compile("<div class=\"bookinfo\">" +
                        "\\s*<a href=\"/(\\d*_\\d*)\\.html\">" +
                        "\\s*<div class=\"detail\">" +
                        "\\s*<p class=\"title\">(.*)</p>" +
                        "\\s*<p class=\"author\">(.*)</p>" +
                        "\\s*</div>" +
                        "\\s*<div class=\"score\">(\\d*\\.\\d*)分</div>" +
                        "\\s*</a>" +
                        "\\s*</div>" +
                        "\\s*<p class=\"review\"><span class=\"longview\"></span>((.*))</p>\\s*" +
                        "</div>");
                Matcher match = pattern.matcher(forObject2);
                boolean isFind = match.find();
                if (isFind) {
                    while (isFind) {

                        float score = Float.parseFloat(match.group(4));

                        if (score >= 7.0) {

                            bookNum = match.group(1);
                            String href = "http://m.zinglizingli.xyz/" + bookNum + ".html";
                            name = match.group(2);
                            if (!name.equals(cacheUtil.get(BOOKNAME_CACHE_PREFIX + name))) {
                                author = match.group(3);
                                desc = match.group(5);

                                resultCode = sendOneSiteWeibo(restTemplate, name, "", author, desc, "看小说吧", bookNum, href);
                                if ("{\"code\":\"A00006\"}".equals(resultCode)) {
                                    cacheUtil.set(BOOKNAME_CACHE_PREFIX + name, name, 60 * 60 * 24 * 30);
                                }

                                realSleepMillis = sleepMillis + (new Random().nextInt(15)) * 60 * 1000;
                                Thread.sleep(realSleepMillis);

                            }
                        }
                        isFind = match.find();
                    }


                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);

            } finally {
                isExcuting = false;
            }

        }

    }

    //@Scheduled(fixedRate = 1000 * 60 * 35)
    //@Scheduled(fixedRate = 1000 * 60 * 5)
    /*public void sendAtDay() {
        if (!isExcuting) {
            isExcuting = true;
            excuteNum++;
            //long sleepMillis = 1000 * 60 * 5;
            long sleepMillis = 1000 * 60 * 25;
            try {
                RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
                //分享酸味书屋
                String url = "http://www.zinglizingli.xyz/paihangbang_lastupdate/1.html";

                //分享喜羊羊小说网
                String url2 = "http://m.zinglizingli.xyz/class/0/1.html";

                ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
                ResponseEntity<String> forEntity2 = restTemplate.getForEntity(url2, String.class);

                String forObject = forEntity.getBody();
                Pattern pattern = Pattern.compile("<li class=\"tjxs\">\n" +
                        "<span class=\"xsm\"><a href=\"/(\\d*_\\d*)\\.html\">(.*)</a></span>\n" +
                        "<span class=\"\">(.*)</span>\n" +
                        "<span class=\"\">(.*)\\.\\.\\.</span>\n" +
                        "<span class=\"tjrs\"><i>(\\d*)</i>人在看</span>\n" +
                        "</li>");
                Matcher match = pattern.matcher(forObject);
                boolean isFind = match.find();
                if (isFind) {
                    while (isFind) {

                        int lookNum = Integer.parseInt(match.group(5));
                        if (lookNum > 5000) {
                            String bookNum = match.group(1);
                            String href = "http://www.zinglizingli.xyz/" + bookNum + ".html";
                            String name = match.group(2);
                            log.debug(excuteNum + ":" + name + "_BOOKNAME_CACHE:" + cacheUtil.get(BOOKNAME_CACHE_PREFIX + name));
                            if (!name.equals(cacheUtil.get(BOOKNAME_CACHE_PREFIX + name))) {

                                String author = match.group(3);
                                String desc = match.group(4);
                                log.debug(excuteNum + ":" + name);
                                String resultCode = sendOneSiteWeibo(restTemplate, name, author, desc, "酸味书屋", bookNum, href);
                                log.debug(excuteNum + ":" + name + ":" + resultCode);
                                log.debug(excuteNum + ":resultCode=={\"code\":\"A00006\"}" + "{\"code\":\"A00006\"}".equals(resultCode));

                                if ("{\"code\":\"A00006\"}".equals(resultCode)) {
                                    cacheUtil.set(BOOKNAME_CACHE_PREFIX + name, name, 60 * 60 * 24 * 30);
                                }
                                long realSleepMillis = sleepMillis + (new Random().nextInt(15)) * 60 * 1000;
                                Thread.sleep(realSleepMillis);
                            }

                        }
                        isFind = match.find();
                    }


                    String forObject2 = forEntity2.getBody();
                    pattern = Pattern.compile("<div class=\"bookinfo\">" +
                            "\\s*<a href=\"/(\\d*_\\d*)\\.html\">" +
                            "\\s*<div class=\"detail\">" +
                            "\\s*<p class=\"title\">(.*)</p>" +
                            "\\s*<p class=\"author\">(.*)</p>" +
                            "\\s*</div>" +
                            "\\s*<div class=\"score\">(\\d*\\.\\d*)分</div>" +
                            "\\s*</a>" +
                            "\\s*</div>" +
                            "\\s*<p class=\"review\"><span class=\"longview\"></span>((.*))</p>\\s*" +
                            "</div>");
                    match = pattern.matcher(forObject2);
                    isFind = match.find();
                    if (isFind) {
                        while (isFind) {

                            float score = Float.parseFloat(match.group(4));

                            if (score >= 7.0) {

                                String bookNum = match.group(1);
                                String href = "http://m.zinglizingli.xyz/" + bookNum + ".html";
                                String name = match.group(2);
                                if (!name.equals(cacheUtil.get(BOOKNAME_CACHE_PREFIX + name))) {
                                    String author = match.group(3);
                                    String desc = match.group(5);

                                    String resultCode = sendOneSiteWeibo(restTemplate, name, author, desc, "喜羊羊小说网", bookNum, href);
                                    if ("{\"code\":\"A00006\"}".equals(resultCode)) {
                                        cacheUtil.set(BOOKNAME_CACHE_PREFIX + name, name, 60 * 60 * 24 * 30);
                                    }

                                    long realSleepMillis = sleepMillis + (new Random().nextInt(15)) * 60 * 1000;
                                    Thread.sleep(realSleepMillis);

                                }
                            }
                            isFind = match.find();
                        }


                    }
                }

            } catch (Exception e) {
                    log.error(e.getMessage(),e);

            } finally {
                isExcuting = false;
            }

        }

    }
*/
    //19点到23点，1点到4点每隔50分钟执行一次,20本书*2分钟+空闲时间
    //@Scheduled(cron = "0 */50 19-23,1-4 * * ?")

   /* public void sendAtNight() throws InterruptedException, IOException {
        if (!isExcuting) {
            isExcuting = true;
            log.info("sendAtNight定时器开始执行。。。。");
            long sleepMillis = 1000 * 60 * 2;
            sendAllSiteWeibo(sleepMillis);
            Thread.sleep(1000 * 60 * 10);
            isExcuting = false;
        }


    }*/


    //6点到17点每隔1小时执行一次，20本书*5分钟+空闲时间
    //@Scheduled(cron = "0 0 6-17/1 * * ?")
   /* public void sendAtDayTime() throws InterruptedException, IOException {

        if (!isExcuting) {
            isExcuting = true;
            log.info("sendAtDayTime定时器开始执行。。。。");
            long sleepMillis = 1000 * 60 * 5;
            sendAllSiteWeibo(sleepMillis);
            Thread.sleep(1000 * 60 * 10);
            isExcuting = false;
        }

    }*/

    /*private void sendAllSiteWeibo(long sleepMillis) throws InterruptedException {
        RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
        //分享酸味书屋
        String url = "http://www.zinglizingli.xyz/paihangbang_lastupdate/1.html";

        //分享喜羊羊小说网
        String url2 = "http://m.zinglizingli.xyz/class/0/1.html";

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        ResponseEntity<String> forEntity2 = restTemplate.getForEntity(url2, String.class);

        String forObject = forEntity.getBody();
        Pattern pattern = Pattern.compile("<li class=\"tjxs\">\n" +
                "<span class=\"xsm\"><a href=\"/(\\d*_\\d*)/\">(.*)</a></span>\n" +
                "<span class=\"\">(.*)</span>\n" +
                "<span class=\"\">(.*)\\.\\.\\.</span>\n" +
                "<span class=\"tjrs\"><i>(\\d*)</i>人在看</span>\n" +
                "</li>");
        Matcher match = pattern.matcher(forObject);
        boolean isFind = match.find();
        if (isFind) {
            while (isFind) {

                int lookNum = Integer.parseInt(match.group(5));
                if (lookNum > 5000) {
                    String bookNum = match.group(1);
                    String href = "http://www.zinglizingli.xyz/" + bookNum;
                    String name = match.group(2);
                    String author = match.group(3);
                    String desc = match.group(4);
                    sendOneSiteWeibo(restTemplate, name, author, desc, "酸味书屋", bookNum, href);
                    long realSleepMillis = sleepMillis + (new Random().nextInt(15)) * 60 * 1000;
                    Thread.sleep(realSleepMillis);
                }

                isFind = match.find();

            }
        }


        String forObject2 = forEntity2.getBody();
        pattern = Pattern.compile("<div class=\"bookinfo\">" +
                "\\s*<a href=\"/(\\d*_\\d*)/\">" +
                "\\s*<div class=\"detail\">" +
                "\\s*<p class=\"title\">(.*)</p>" +
                "\\s*<p class=\"author\">(.*)</p>" +
                "\\s*</div>" +
                "\\s*<div class=\"score\">(\\d*\\.\\d*)分</div>" +
                "\\s*</a>" +
                "\\s*</div>" +
                "\\s*<p class=\"review\"><span class=\"longview\"></span>((.*))</p>\\s*" +
                "</div>");
        match = pattern.matcher(forObject2);
        isFind = match.find();
        if (isFind) {
            while (isFind) {

                float score = Float.parseFloat(match.group(4));

                if (score >= 7.0) {

                    String bookNum = match.group(1);
                    String href = "http://m.zinglizingli.xyz/" + bookNum;
                    String name = match.group(2);
                    String author = match.group(3);
                    String desc = match.group(5);

                    sendOneSiteWeibo(restTemplate, name, author, desc, "喜羊羊小说网", bookNum, href);

                    long realSleepMillis = sleepMillis + (new Random().nextInt(15)) * 60 * 1000;
                    Thread.sleep(realSleepMillis);
                }

                isFind = match.find();


            }
        }
    }*/


    public static void main(String[] args) throws Exception {
        RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
        //分享酸味书屋
        String url = "http://www.zinglizingli.xyz/paihangbang_lastupdate/1.html";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String forObject = forEntity.getBody();
        Pattern pattern = Pattern.compile("<li class=\"tjxs\">\n" +
                "<span class=\"xsm\"><a href=\"/(\\d*_\\d*)/\">(.*)</a></span>\n" +
                "<span class=\"\">(.*)</span>\n" +
                "<span class=\"\">(.*)\\.\\.\\.</span>\n" +
                "<span class=\"tjrs\"><i>\\d*</i>人在看</span>\n" +
                "</li>");
        Matcher match = pattern.matcher(forObject);
        boolean isFind = match.find();
        if (isFind) {
            while (isFind) {
                String bookNum = match.group(1);
                String href = "http://www.zinglizingli.xyz/" + bookNum;
                String name = match.group(2);
                String author = match.group(3);
                String desc = match.group(4);


                isFind = match.find();

            }
        }

        //分享喜羊羊小说网
        url = "http://m.zinglizingli.xyz/class/0/1.html";
        forEntity = restTemplate.getForEntity(url, String.class);
        forObject = forEntity.getBody();
        pattern = Pattern.compile("<div class=\"bookinfo\">" +
                "\\s*<a href=\"/(\\d*_\\d*)/\">" +
                "\\s*<div class=\"detail\">" +
                "\\s*<p class=\"title\">(.*)</p>" +
                "\\s*<p class=\"author\">(.*)</p>" +
                "\\s*</div>" +
                "\\s*<div class=\"score\">\\d*\\.\\d*分</div>" +
                "\\s*</a>" +
                "\\s*</div>" +
                "\\s*<p class=\"review\"><span class=\"longview\"></span>((.*))</p>\\s*" +
                "</div>");
        match = pattern.matcher(forObject);
        isFind = match.find();
        if (isFind) {
            while (isFind) {
                String bookNum = match.group(1);
                String href = "http://m.zinglizingli.xyz/" + bookNum;
                String name = match.group(2);
                String author = match.group(3);
                String desc = match.group(4);
                // sendOneSiteWeibo(restTemplate, name, author, desc, "喜羊羊小说网", bookNum, href);


                isFind = match.find();


            }
        }


    }

    private String sendOneSiteWeibo(RestTemplate template, String bookName, String indexName, String author, String desc, String wapName, String bookNum, String href) {
        String baseUrl = "http://service.weibo.com/share/aj_share.php";
        Map<String, String> param = new HashMap<>();
        /*String content = bookName + "小说最新章节列表," + bookName + "小说免费在线阅读," + bookName +
                "小说TXT下载,尽在" + wapName +href+ "\n";
        if(indexName != null){
            content+=("最新章节："+indexName+"\n");
        }
        content = content + "作者："+(author.replace("作者：","")) + "\n";
        content += ("简介："+desc.replace("简介：",""));*/
        String content = bookName+"最新章节,小说"+bookName+"("+author.replace("作者：","")+")手机阅读,小说"+bookName+"TXT下载 - "+href;
        param.put("content", content );
        param.put("styleid", "1");
        param.put("from", "share");
        param.put("appkey", "2351975812");
        param.put("refer", "http://www.zinglizingli.xyz/" + bookNum + "/");
        param.put("url_type", "0");
        param.put("visible", "0");
        //byte[] bytes = sendPostRequest(baseUrl, param);


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.setAll(param);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");
        headers.add("Accept-Encoding", "gzip, deflate");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9");
        headers.add("Connection", "keep-alive");
        headers.add("Content-Length", "1146");
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        String[] cookieArr = cookieStr.split(";");
        List<String> cookies = Arrays.asList(cookieArr);
        headers.put(HttpHeaders.COOKIE, cookies);

        headers.add("Host", "service.weibo.com");
        headers.add("Origin", "http://service.weibo.com");
        headers.add("Referer", "http://service.weibo.com/share/share.php?appkey=2351975812&searchPic=true&title=%C2%A1%C2%BE%E4%BF%AE%E7%9C%9F%E8%81%8A%E5%A4%A9%E7%BE%A4%E6%9C%80%E6%96%B0%E7%AB%A0%E8%8A%82%E5%88%97%E8%A1%A8_%E4%BF%AE%E7%9C%9F%E8%81%8A%E5%A4%A9%E7%BE%A4%E6%9C%80%E6%96%B0%E7%AB%A0%E8%8A%82%E7%9B%AE%E5%BD%95_%E9%85%B8%E5%91%B3%E4%B9%A6%E5%B1%8B%C2%A1%C2%BF%E4%BF%AE%E7%9C%9F%E8%81%8A%E5%A4%A9%E7%BE%A4%E6%9C%80%E6%96%B0%E7%AB%A0%E8%8A%82%E7%94%B1%E7%BD%91%E5%8F%8B%E6%8F%90%E4%BE%9B%EF%BC%8C%E3%80%8A%E4%BF%AE%E7%9C%9F%E8%81%8A%E5%A4%A9%E7%BE%A4%E3%80%8B%E6%83%85%E8%8A%82%E8%B7%8C%E5%AE%95%E8%B5%B7%E4%BC%8F%E3%80%81%E6%89%A3%E4%BA%BA%E5%BF%83%E5%BC%A6%EF%BC%8C%E6%98%AF%E4%B8%80%E6%9C%AC%E6%83%85%E8%8A%82%E4%B8%8E%E6%96%87%E7%AC%94%E4%BF%B1%E4%BD%B3%E7%9A%84%E9%83%BD%E5%B8%82%E5%B0%8F%E8%AF%B4%E5%B0%8F%E8%AF%B4%EF%BC%8C%E9%85%B8%E5%91%B3%E4%B9%A6%E5%B1%8B%E5%85%8D%E8%B4%B9%E6%8F%90%E4%BE%9B%E5%94%90%E7%A0%96%E6%9C%80%E6%96%B0%E6%B8%85%E7%88%BD%E5%B9%B2%E5%87%80%E7%9A%84%E6%96%87%E5%AD%97%E7%AB%A0%E8%8A%82%E5%9C%A8%E7%BA%BF%E9%98%85%E8%AF%BB.&url=http%3A//www.zinglizingli.xyz/" + bookNum + "/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE");
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add("Accept-Encoding", "gzip, deflate");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9");
        headers.add("Connection", "keep-alive");
        headers.add("Content-Length", "1146");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = template.postForEntity(baseUrl, request, String.class, map);

        return stringResponseEntity.getBody();
    }


}
