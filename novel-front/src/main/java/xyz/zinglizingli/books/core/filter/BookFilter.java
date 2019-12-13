package xyz.zinglizingli.books.core.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import xyz.zinglizingli.books.core.utils.Constants;
import xyz.zinglizingli.common.utils.RestTemplateUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 书籍内容过滤器
 * @author xxy
 */
@Slf4j
public class BookFilter implements Filter {


    /**
     * 图片后缀集合
     * */
    private final List<String> PIC_POSTFIX_LIST = Arrays.asList("jpg","pcx","emf","gif","bmp","tga","jpeg","tif","png","rle");

    /**
     * 本地图片保存路径前缀
     * */
    private  String picSavePath;


    @Override
    public void init(FilterConfig filterConfig){
        picSavePath = filterConfig.getInitParameter("picSavePath");
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestUrl = req.getRequestURL().toString();
        String requestUri = req.getRequestURI();

        String forObject;

        try {

            //本地图片
            if (requestUri.contains(Constants.LOCAL_PIC_PREFIX)) {
                OutputStream out = resp.getOutputStream();
                InputStream input = new FileInputStream(new File(picSavePath + requestUri));
                byte[] b = new byte[4096];
                for (int n; (n = input.read(b)) != -1; ) {
                    out.write(b, 0, n);
                }
                input.close();
                out.close();
                return;

            }

            //非漫画访问
            if (!requestUrl.contains(Constants.CARTOON_CONTENT_PREFIX)) {
                filterChain.doFilter(req, resp);
                return;
            }


            String method = req.getMethod();

            //请求html/css/js等文件
            RestTemplate restTemplate = RestTemplateUtil.getInstance(Charsets.UTF_8);

            resp.setCharacterEncoding("utf-8");

            String realPath = Constants.CRAWL_CARTOON_URL_PREFIX + requestUri.substring(Constants.CARTOON_CONTENT_PREFIX.length());


            if (HttpMethod.GET.name().equals(method)) {
                //漫画GET请求
                String postFix = requestUri.substring(requestUri.lastIndexOf(".") + 1);
                if (PIC_POSTFIX_LIST.contains(postFix)) {
                    // 请求图片
                    restTemplate = RestTemplateUtil.getInstance(Charsets.ISO_8859_1);
                    resp.setContentType("image/apng");
                }
                if (requestUri.startsWith(Constants.CARTOON_PIC_PREFIX)) {
                    //漫画图片处理
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Referer", Constants.CRAWL_CARTOON_URL_PREFIX);
                    HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
                    realPath = Constants.CRAWL_CARTOON_PIC_URL_PREFIX + requestUri.substring(Constants.CARTOON_PIC_PREFIX.length());
                    ResponseEntity<Resource> resEntity = restTemplate.exchange(realPath, HttpMethod.GET, requestEntity, Resource.class);
                    InputStream input = Objects.requireNonNull(resEntity.getBody()).getInputStream();
                    OutputStream out = resp.getOutputStream();
                    byte[] b = new byte[4096];
                    for (int n; (n = input.read(b)) != -1; ) {
                        out.write(b, 0, n);
                    }
                    input.close();
                    out.close();
                    return;


                } else if (requestUri.startsWith(Constants.CARTOON_STATIC_PREFIX)) {
                    //漫画静态文件处理
                    realPath = Constants.CRAWL_CARTOON_STATIC_URL_PREFIX + requestUri.substring(Constants.CARTOON_STATIC_PREFIX.length());
                    ResponseEntity<String> forEntity = restTemplate.getForEntity(realPath, String.class);
                    forObject = forEntity.getBody();
                    assert forObject != null;
                    forObject = forObject.replaceAll(Constants.CRAWL_CARTOON_PIC_URL_PREFIX, Constants.CARTOON_PIC_PREFIX);

                } else {
                    //其他漫画内容处理
                    ResponseEntity<String> forEntity = restTemplate.getForEntity(realPath, String.class);
                    forObject = forEntity.getBody();

                    assert forObject != null;

                    //漫画内容过滤
                    forObject = forObject.replace(Constants.CRAWL_CARTOON_URL_PREFIX+"js/ad/ad_12.js", "")
                            .replace(Constants.CRAWL_CARTOON_URL_PREFIX+"js/ad/ad_13.js", "")
                            .replace(Constants.CRAWL_CARTOON_STATIC_URL_PREFIX+"ocomic/js/dmzjMhFinally-new.js", "")
                            .replace(Constants.CRAWL_CARTOON_STATIC_URL_PREFIX+"ocomic/js/dmzjMhFinally-new.js", "")
                            .replace(Constants.CRAWL_CARTOON_STATIC_URL_PREFIX+"module/js/float_code.js", "")
                            .replaceAll(Constants.CRAWL_CARTOON_STATIC_URL_PREFIX+"public/js/dmzj-land-2015.6.js", "")
                            .replaceAll("<script type=\"text/javascript\">var cnzz_protocol =[^<]+</script>", "")
                            .replaceAll("globalNav.js", "")
                            .replaceAll("TSB.js", "")
                            .replaceAll(Constants.CRAWL_CARTOON_PIC_URL_PREFIX, Constants.CARTOON_PIC_PREFIX)
                            .replaceAll(Constants.CRAWL_CARTOON_STATIC_URL_PREFIX, Constants.CARTOON_STATIC_PREFIX)
                            + "<script>var browser={\n" +
                            "    versions:function(){\n" +
                            "    var u = window.navigator.userAgent;\n" +
                            "    return {\n" +
                            "        trident: u.indexOf('Trident') > -1, //IE内核\n" +
                            "        presto: u.indexOf('Presto') > -1, //opera内核\n" +
                            "        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核\n" +
                            "        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核\n" +
                            "        mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端\n" +
                            "        ios: !!u.match(/\\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端\n" +
                            "        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器\n" +
                            "        iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者安卓QQ浏览器\n" +
                            "        iPad: u.indexOf('iPad') > -1, //是否为iPad\n" +
                            "        webApp: u.indexOf('Safari') == -1 ,//是否为web应用程序，没有头部与底部\n" +
                            "        weixin: u.indexOf('MicroMessenger') == -1 //是否为微信浏览器\n" +
                            "        };\n" +
                            "    }()\n" +
                            "};" +
                            "</script>"
                            + "<script>$(function(){$(\"#app_manhua\").remove();" +
                            "$('.btmBtnBox').remove();$('.red_box').remove()" +
                            ";$('.mainNav').remove();$('.wrap_last_head').remove();$('.wrap_last_mid').remove()" +
                            ";$('.comic_gd').remove();$('.comic_last').remove();$('.side_bar').remove()" +
                            ";$('.point_wrap').remove();$('.show').remove();$('.light').remove()" +
                            ";$('#sidePublic').remove();$('.side_public').remove()" +
                            ";$('.foot-detail').remove();$('#float_nav_type').remove()})</script>";
                }


            } else {
                //漫画POST请求


                Map<String, String[]> oldParameterMap = req.getParameterMap();
                Set<Map.Entry<String, String[]>> entries = oldParameterMap.entrySet();
                Map<String, String> newParameterMap = new HashMap<>(entries.size());
                for (Map.Entry<String, String[]> entry : entries) {
                    newParameterMap.put(entry.getKey(), entry.getValue()[0]);
                }

                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.setAll(newParameterMap);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                forObject = restTemplate.postForEntity(realPath, request, String.class).getBody();
                assert forObject != null;
                forObject = forObject.replaceAll(Constants.CARTOON_CONTENT_PREFIX, Constants.CRAWL_CARTOON_URL_PREFIX)
                ;

            }


        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            if (e instanceof HttpClientErrorException && (((HttpClientErrorException) e).getStatusCode() == HttpStatus.NOT_FOUND)) {
                //404
                resp.sendRedirect(Constants.NOT_FOUND_PATH);
                return;
            } else {
                //500
                req.getRequestDispatcher(Constants.SERVER_ERROR_PATH).forward(servletRequest, servletResponse);
                return;
            }


        }

        resp.getWriter().print(forObject);
    }


    @Override
    public void destroy() {

    }


}
