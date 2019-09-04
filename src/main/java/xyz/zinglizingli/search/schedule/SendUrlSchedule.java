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
import org.springframework.web.client.RestTemplate;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.search.cache.CommonCacheUtil;
import xyz.zinglizingli.search.utils.RestTemplateUtil;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
主动推送：最为快速的提交方式，
建议您将站点当天新产出链接立即通过此方式推送给百度，
以保证新链接可以及时被百度收录。
*/
@Service
public class SendUrlSchedule {

    @Autowired
    private CommonCacheUtil cacheUtil;

    @Autowired
    private BookService bookService;

    @Value("${baidu.record.ids}")
    private String recordedIds;

    private Logger log = LoggerFactory.getLogger(SendUrlSchedule.class);


    @Scheduled(cron = "0 0 1 * * 5")
    public void sendAllBookToBaidu() {
        System.out.println("sendAllBookToBaidu。。。。。。。。。。。。。。。");

        List<String> recordedIdsList = Arrays.asList(recordedIds.split(","));
        List<String> idList = bookService.queryEndBookIdList();
        RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");


        String reqBody = "";
        for (String id : idList) {
            try {
                if (!recordedIdsList.contains(id)) {
                    reqBody += ("https://www.zinglizingli.xyz/book/" + id + ".html" + "\n");
                    //reqBody+=("http://www.zinglizingli.xyz/book/"+id+".html"+"\n");
                    if (reqBody.length() > 2000) {
                        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.TEXT_PLAIN);
                        //headers.add("User-Agent","curl/7.12.1");
                        headers.add("Host", "data.zz.baidu.com");
                        headers.setContentLength(reqBody.length());
                        HttpEntity<String> request = new HttpEntity<>(reqBody, headers);
                        System.out.println("推送数据：" + reqBody);
                        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://data.zz.baidu.com/urls?site=www.zinglizingli.xyz&token=IuK7oVrPKe3U606x", request, String.class);
                        System.out.println("推送URL结果：code:" + stringResponseEntity.getStatusCode().value() + ",body:" + stringResponseEntity.getBody());
                        reqBody = "";
                        Thread.sleep(1000 * 10);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

    }
}
