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
import xyz.zinglizingli.books.constant.CacheKeyConstans;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.books.service.MailService;
import xyz.zinglizingli.books.util.RandomValueUtil;
import xyz.zinglizingli.search.cache.CommonCacheUtil;
import xyz.zinglizingli.search.utils.RestTemplateUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;


/*
主动推送：最为快速的提交方式，
建议您将站点当天新产出链接立即通过此方式推送给百度，
以保证新链接可以及时被百度收录。
*/
@Service
public class SendEmaillSchedule {

    @Autowired
    private CommonCacheUtil cacheUtil;

    @Autowired
    private MailService mailService;


    private Logger log = LoggerFactory.getLogger(SendEmaillSchedule.class);


   // @Scheduled(fixedRate = 1000*60*60*24)
    public void sendEmaill() {
        System.out.println("SendEmaillSchedule。。。。。。。。。。。。。。。");

        for(int i = 0 ; i < 1000; i++){
            String email = RandomValueUtil.getEmail();
            if(cacheUtil.get(CacheKeyConstans.EMAIL_URL_PREFIX_KEY+email)!=null){
                continue;
            }
            cacheUtil.setObject(CacheKeyConstans.EMAIL_URL_PREFIX_KEY+email,email,60*60*24*30);
            String subject = "推荐一个看小说的弹幕网站";
            String content = "精品小说楼是国内优秀的<b style='color:red'>小说弹幕网站</b>,精品小说楼提供海量热门网络小说,日本轻小说,国产轻小说,动漫小说,轻小说<b style='color:red'>在线阅读</b>和<b style='color:red'>TXT小说下载</b>,致力于网络精品小说的收集,智能计算小说评分,打造小说<b style='color:red'>精品排行榜</b>,致力于<b style='color:red'>无广告无弹窗</b>的小说阅读环境。" +
                    "<br/><a href='https://www.zinglizingli.xyz/'>点击进入</a>"
                    +"<br/><img src='https://www.zinglizingli.xyz/me/assets/images/work001-01.jpg'>";
            mailService.sendHtmlMail(email, subject, content);
            try {
                Thread.sleep(new Random().nextInt(1000*60*10)+1000*60);
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
            }
        }

    }
}
