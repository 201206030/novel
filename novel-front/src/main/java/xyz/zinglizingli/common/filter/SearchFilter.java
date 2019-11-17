package xyz.zinglizingli.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import xyz.zinglizingli.common.cache.CommonCacheUtil;
import xyz.zinglizingli.common.utils.RestTemplateUtil;
import xyz.zinglizingli.common.utils.SpringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SearchFilter.class);

    private CommonCacheUtil cacheUtil;

    private static List<String> picPostFix;

    private static List<String> localFileFix;

    private static List<String> staticFileFix;

    private static List<String> noteURI;

    private RestTemplate restTemplate;


    private final String SUANWEI_BOOK_REGEX = "<a\\s+href=\"/(\\d+_\\d+)/\">";
    private final String SUANWEI_BOOK_HTML_REGEX = "/\\d+_\\d+\\.html";

    private final String XIYANGYANG_BOOK_REGEX = "<a\\s+href=\"/(\\d+_\\d+)/\">";
    private final String XIYANGYANG_BOOK_HTML_REGEX = "/\\d+_\\d+\\.html";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        picPostFix = new ArrayList<>();
        picPostFix.add("jpg");
        picPostFix.add("pcx");
        picPostFix.add("emf");
        picPostFix.add("gif");
        picPostFix.add("bmp");
        picPostFix.add("tga");
        picPostFix.add("jpeg");
        picPostFix.add("tif");
        picPostFix.add("png");
        picPostFix.add("rle");
        localFileFix = new ArrayList<>();
        localFileFix.add("IMG_1470.JPG");
        localFileFix.add("baidu_verify_Ep8xaWQJAI.html");
        localFileFix.add("baidu_verify_L6sR9GjEtg.html");
        localFileFix.add("shenma-site-verification.txt");
        localFileFix.add("favicon.ico");
        localFileFix.add("headerbg.jpg");
        localFileFix.add("mang.png");
        localFileFix.add("HotBook.apk");
        localFileFix.add("wap_collect.js");
        localFileFix.add("note_1.html");
        localFileFix.add("note_2.html");
        localFileFix.add("note_3.html");
        localFileFix.add("note_4.html");
        staticFileFix = new ArrayList<>();
        staticFileFix.add("jpg");
        staticFileFix.add("gif");
        staticFileFix.add("bmp");
        staticFileFix.add("jpeg");
        staticFileFix.add("png");
        staticFileFix.add("js");
        staticFileFix.add("css");
        noteURI = new ArrayList<>();
        noteURI.add("/html/note_1.html");
        noteURI.add("/html/note_2.html");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String forObject = null;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestURL = req.getRequestURL().toString();
        String requestURI = req.getRequestURI();

        try {

            if (!requestURL.contains("/manhua/")) {
                filterChain.doFilter(req, resp);
                return;
            }


            final String method = req.getMethod();


                // 案例：充当客户端通过restTemplate请求网络数据，并充当服务端将数据返回给浏览器
                // 客户端请求数据：输入流（byte[]）==》字符串
                // 服务端响应数据：字符串 == 》 输出流（byte[]）

                //默认方式：
                //RestTemplate restTemplate = new RestTemplate();
                // ①当返回的response-header的content-type属性有charset值时，
                // restTemplate的StringHttpMessageConverter会设置默认charset为content-type属性
                // charset值
                // StringHttpMessageConverter.setDefaultCharset(Charset.forName(charset));
                // ②当返回的response-header的content-type属性没有charset值时
                // restTemplate的StringHttpMessageConverter会使用默认的charset即ISO-8859-1

                    // 对服务端请求返回的输入流（byte[]）采用何种编码转换成字符串（String）
                    restTemplate = RestTemplateUtil.getInstance("utf-8");//请求html/css/js等文件
                    // 对客户端响应返回的字符串（String）采用何种编码转换成输出流（byte[]）
                    resp.setCharacterEncoding("utf-8");

                        /*//=====现在浏览器有编码自动识别功能，所以上面的代码没有加content-type的Header也没有问题==========
                        //=====正确做法应该是下面代码片段1和代码片段2二选一==========

                        //===============================================代码片段1===============================
                        // 对客户端响应返回的字符串（String）采用何种编码转换成输出流（byte[]）
                        resp.setCharacterEncoding("utf-8");
                        // 告诉浏览器对服务端请求返回的输入流（byte[]）采用何种编码转换成字符串（String）显示
                        resp.setHeader("content-type", "text/html;charset=utf-8");
                        //===============================================代码片段1===============================


                        //===============================================代码片段2===============================
                        //对客户端响应返回的字符串（String）采用何种编码转换成输出流（byte[]）
                        //并且告诉浏览器对服务端请求返回的输入流（byte[]）采用何种编码转换成字符串（String）显示
                        resp.setContentType("text/html;charset=utf-8");
                        //===============================================代码片段2===============================
*/


                if (HttpMethod.GET.name().equals(method)) {


                    String fileName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
                    if (localFileFix.contains(fileName) || fileName.startsWith("9a4a540e-1759-4268-90fa-7fb652c3604a.")) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }



                    String queryString = req.getQueryString();
                    if (queryString != null && queryString.length() > 0 && !queryString.contains("bsh_bid=")) {
                        queryString = "?" + URLDecoder.decode(req.getQueryString());
                    } else {
                        queryString = "";
                    }


                    if (forObject == null) {


                        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.dmzj.com/"+requestURI.substring(8), String.class);
                        forObject = forEntity.getBody();

                        //  forObject = new String(forObject.getBytes("ISO-8859-1"),"utf-8");
                      forObject=forObject.replace("https://www.dmzj.com/js/ad/ad_12.js","");
                      forObject=forObject.replace("https://www.dmzj.com/js/ad/ad_13.js","");
                        forObject=forObject.replace("https://static.dmzj.com/ocomic/js/dmzjMhFinally-new.js","");
                        forObject=forObject.replace("https://static.dmzj.com/ocomic/js/dmzjMhFinally-new.js","");
                        forObject=forObject.replace("https://static.dmzj.com/module/js/float_code.js","");
                        forObject=forObject.replaceAll("<script type=\"text/javascript\">var cnzz_protocol =[^<]+</script>","");
                        forObject=forObject.replaceAll("https://static.dmzj.com/public/js/dmzj-land-2015.6.js","");
                        forObject=forObject.replaceAll("<script type=\"text/javascript\">var cnzz_protocol =[^<]+</script>","");
                        forObject=forObject.replaceAll("<script type=\"text/javascript\">var cnzz_protocol =[^<]+</script>","");
                        forObject=forObject.replaceAll("<script type=\"text/javascript\">var cnzz_protocol =[^<]+</script>","");
                        forObject=forObject.replaceAll("globalNav.js","");
                        forObject=forObject.replaceAll("TSB.js","");
                        forObject=forObject+"<script>$(function(){$(\"#app_manhua\").remove();" +
                                "$('.btmBtnBox').remove();$('.red_box').remove()" +
                                ";$('.mainNav').remove();$('.wrap_last_head').remove();$('.wrap_last_mid').remove()" +
                                ";$('.comic_gd').remove();$('.comic_last').remove();$('.side_bar').remove()" +
                                ";$('.point_wrap').remove();$('.show').remove();$('.light').remove()" +
                                ";$('#sidePublic').remove();$('.side_public').remove()" +
                                ";$('.foot-detail').remove();$('#float_nav_type').remove()})</script>";
// forObject = forObject.replaceAll("/manhua/", "https://www.dmzj.com/")
//                                    .replaceAll("笔趣岛", "酸味书屋")
//                                    .replaceAll("笔趣阁", "酸味书屋")
//                                    .replaceAll("class=\"mainNav independNav\"", "style='dispaly:none' class=\"mainNav independNav\"")
//                                    .replaceAll("</head>", "<script language=\"javascript\" type=\"text/javascript\" src=\"http://www.zinglizingli.xyz/js/wap_collect.js\"></script></head>")
//                                    .replaceFirst("</head>", "<script>" +
//                                            "var _hmt = _hmt || [];" +
//                                            "(function() {" +
//                                            "  var hm = document.createElement(\"script\");" +
//                                            "  hm.src = \"https://hm.baidu.com/hm.js?0bd7345ca6b694ea3dfbe87da008082e\";" +
//                                            "  var s = document.getElementsByTagName(\"script\")[0]; " +
//                                            "  s.parentNode.insertBefore(hm, s);" +
//                                            "})();" +
//                                            "</script></head>")
//                                    .replaceAll("<input type=\"image\" src=\"https://m.baidu.com/se/transcode/static/img/bgn.png\".*>", "")
//                                    .replaceAll("https://zhannei.baidu.com/cse", "http://m.zinglizingli.xyz")
//                                    .replaceAll("<a href=\"/.*/\">返回</a>", "<a href=\"javascript:history.go(-1)\">返回</a>")
//                                    .replaceAll("<a href=\".*\".*>加入书架</a>", "<a href=\"javascript:AddToFavorites(true);\">加入收藏</a>")
//                                    .replaceFirst("</head>", "<script>\n" +
//                                            "(function(){\n" +
//                                            "    var bp = document.createElement('script');\n" +
//                                            "    var curProtocol = window.location.protocol.split(':')[0];\n" +
//                                            "    if (curProtocol === 'https') {\n" +
//                                            "        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';\n" +
//                                            "    }\n" +
//                                            "    else {\n" +
//                                            "        bp.src = 'http://push.zhanzhang.baidu.com/push.js';\n" +
//                                            "    }\n" +
//                                            "    var s = document.getElementsByTagName(\"script\")[0];\n" +
//                                            "    s.parentNode.insertBefore(bp, s);\n" +
//                                            "})();\n" +
//                                            "</script>\n</head>")//页面访问自动推送到百度
//                                    .replaceAll("<script.*wap\\.js.*script>", "")//去除广告
                            ;


//                            if (requestURI.matches(SUANWEI_BOOK_HTML_REGEX)) {
//                                Pattern pattern = Pattern.compile("<h1\\s+id=\"bqgmb_h1\">(.+)\\s+目录共\\d+章</h1>");
//                                Matcher matcher = pattern.matcher(forObject);
//                                String title = "";
//                                if (matcher.find()) {
//                                    title = matcher.group(1);
//                                }//<li class="sort">     类别：武侠仙侠</li>
//                                pattern = Pattern.compile("<p>作者：(.+)</p>");
//                                matcher = pattern.matcher(forObject);
//                                String author = "";
//                                if (matcher.find()) {
//                                    author = matcher.group(1);
//                                }
//                                pattern = Pattern.compile("<a\\s+href=\"/list/\\d+_\\d+.*\">(.+)</a>");
//                                matcher = pattern.matcher(forObject);
//                                String sort = "";
//                                if (matcher.find()) {
//                                    sort = matcher.group(1);
//                                }
//                                String desc = title + "，" + title + "小说最新章节免费在线阅读、最新章节列表，" + title + "小说最新更新免费提供，《" + title + "》是一本情节与文笔俱佳的" + sort + "小说，由作者" + author + "创建。";
//
//                                forObject = forObject.replaceFirst("<meta\\s+name=\"description\"\\s+content=\"[^>]+\"\\s*/?>", "");//[^>]+表示1个或多个不是>的字符
//                                forObject = forObject.replaceFirst("<head>", "<head><meta name=\"description\" content=\"" + desc + "\"/>");
//
//
//                            }
//
//                            if ("/".equals(requestURI)) {
//                                forObject = forObject.replaceFirst("<meta\\s+name=\"description\"\\s+content=\"[^>]+\"\\s*/?>", "");//[^>]+表示1个或多个不是>的字符
//                                forObject = forObject.replaceFirst("<head>", "<head><meta name=\"description\" content=\"酸味书屋致力于打造小说最全，更新最快的在线小说阅读网，本站收录了当前最火热的网络小说，提供无广告、高质量内容的小说服务，是广大网友最喜欢的温馨小说站。\">");
//
//
//                            }
                    }


                } else {


                    Map<String, String[]> oldParameterMap = req.getParameterMap();
                    Map<String, String> newParameterMap = new HashMap<>();
                    Set<Map.Entry<String, String[]>> entries = oldParameterMap.entrySet();
                    for (Map.Entry<String, String[]> entry : entries) {
                        newParameterMap.put(entry.getKey(), entry.getValue()[0]);
                    }

                    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.setAll(newParameterMap);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                    forObject = restTemplate.postForEntity("https://www.dmzj.com/"+requestURI.substring(8), request, String.class).getBody();
                    // forObject = new String(forObject.getBytes("ISO-8859-1"),"utf-8");
                    forObject = forObject.replaceAll("/manhua/", "https://www.dmzj.com/")
                            .replaceAll("笔趣岛", "酸味书屋")
                            .replaceAll("笔趣阁", "酸味书屋")
                            .replaceFirst("</head>", "<script>\n" +
                                    "var _hmt = _hmt || [];" +
                                    "(function() {" +
                                    "  var hm = document.createElement(\"script\");" +
                                    "  hm.src = \"https://hm.baidu.com/hm.js?0bd7345ca6b694ea3dfbe87da008082e\";" +
                                    "  var s = document.getElementsByTagName(\"script\")[0]; " +
                                    "  s.parentNode.insertBefore(hm, s);" +
                                    "})();" +
                                    "</script></head>")
                            .replaceAll("<a\\s+href=\"/bookcase.php\">书架</a>", "<a href=\"" + noteURI.get(new Random().nextInt(noteURI.size())) + "\">笔记</a>")
                            .replaceAll("<a href=\"/.*/\">返回</a>", "<a href=\"javascript:history.go(-1)\">返回</a>")
                    ;
                    forObject = setBookURIToHTML(forObject, SUANWEI_BOOK_REGEX);
                    //resp.setCharacterEncoding("utf-8");
                    //setContentType(postFix, resp);

                }



        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            if (e instanceof HttpClientErrorException && (((HttpClientErrorException) e).getStatusCode() == HttpStatus.NOT_FOUND)) {
                //404
                resp.sendRedirect("/");
                return;
            } else {
                req.getRequestDispatcher("/mang.html").forward(servletRequest, servletResponse);
                return;
            }


            //resp.setCharacterEncoding("utf-8");

        }
        resp.getWriter().print(forObject);
        return;
    }

    private String addAttacDivForSearch(String forObject, String requestURI) {
        try {
            if (requestURI.endsWith(".html") || requestURI.equals("/")) {
                String hotNewsDiv = cacheUtil.get("hotNewsDiv");
                if (hotNewsDiv == null) {
                    MultiValueMap<String, String> mmap = new LinkedMultiValueMap<>();
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Host", "channel.chinanews.com");
                    headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mmap, headers);
                    String body = restTemplate.postForEntity("http://channel.chinanews.com/cns/cjs/sh.shtml", request, String.class).getBody();
                    Pattern pattern = Pattern.compile("specialcnsdata\\s*=\\s*\\{\"docs\":(.+)};\\s+newslist\\s*=\\s*specialcnsdata;");
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        String jsonResult = matcher.group(1);
                        if (jsonResult.length() > 5) {
                            List<Map<String, String>> list = new ObjectMapper().readValue(jsonResult, List.class);
                            StringBuilder hotContent = new StringBuilder();
                            for (Map<String, String> map : list) {
                                hotContent.append("<ul>\n");
                                hotContent.append("<li>\n");
                                hotContent.append(map.get("pubtime"));
                                hotContent.append("</li>\n");
                                hotContent.append("<li>\n");
                                hotContent.append(map.get("title"));
                                hotContent.append("</li>\n");
                                hotContent.append("<li>\n");
                                hotContent.append(map.get("content"));
                                hotContent.append("</li>\n");
                                hotContent.append("<li>\n");
                                hotContent.append("<img src=\"" + map.get("galleryphoto") + "\"/>");
                                hotContent.append("</li>\n");
                                hotContent.append("</ul>\n");
                            }
                            hotNewsDiv = "<div style=\"position:fixed;top:0px;left:0px;z-index:-100;opacity:0\">" + hotContent.toString() + "</div>";
                            cacheUtil.set("hotNewsDiv", hotNewsDiv, 60 * 60 * 24);
                            forObject = forObject.replaceFirst("</body>", hotNewsDiv + "</body>");
                        }
                    }
                } else {
                    forObject = forObject.replaceFirst("</body>", hotNewsDiv + "</body>");

                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {

        }

        return forObject;
    }

    private String setBookURIToHTML(String forObject, String regex) {
        String result = forObject;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(forObject);
        boolean isFind = matcher.find();
        if (isFind) {

            while (isFind) {
                String booURI = matcher.group(1);
                String htmlBooURI = booURI.substring(0, booURI.length()) + ".html";
                result = result.replaceFirst(booURI + "/", htmlBooURI);
                isFind = matcher.find();
            }


        }

        return result;
    }

    private String postBiquta(HttpServletRequest req, String realUrl, Map<String, String> otherParam) {
        String forObject;
        Map<String, String[]> oldParameterMap = req.getParameterMap();
        Map<String, String> newParameterMap = new HashMap<>();
        Set<Map.Entry<String, String[]>> entries = oldParameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            newParameterMap.put(entry.getKey(), entry.getValue()[0]);
        }
        if (otherParam != null) {
            newParameterMap.putAll(otherParam);
        }

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.setAll(newParameterMap);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        forObject = restTemplate.postForEntity(realUrl, request, String.class).getBody();
        forObject = forObject.replaceAll("https://m.biquta.com", "http://m.zinglizingli.xyz")
                .replaceAll("笔趣阁", "看小说吧")
                .replaceAll("笔趣塔", "看小说吧")
                .replaceFirst("<title>看小说吧手机版-看小说吧</title>", "<title>看小说吧</title>")
                .replaceFirst("content=\"看小说吧\"", "content=\"小说阅读,小说排行,好看小说排行,热门小说排行,小说阅读手机版\"")
                .replaceFirst("<head>", "<head><meta name=\"shenma-site-verification\" content=\"5548d4bd962d5cdd4cf6aeba92b991a8_1565878917\">")
                .replaceAll("<a href=\"/bookcase.php\".*>我的书架</a>", "<a href=\"https://www.zinglizingli.xyz/book/searchSoftBook.html\">轻小说</a><a href=\"https://www.zinglizingli.xyz\">精品小说</a>")
                .replaceFirst("<a href=\"http://m.zinglizingli.xyz/tempcase.html\">阅读记录</a>", "<a href=\"/HotBook.apk\">客户端下载</a>")
                .replaceFirst("</head>", "<script>\n" +
                        "var _hmt = _hmt || [];\n" +
                        "(function() {\n" +
                        "  var hm = document.createElement(\"script\");\n" +
                        "  hm.src = \"https://hm.baidu.com/hm.js?b3a84b2ec6cc52dd088d735565b49644\";\n" +
                        "  var s = document.getElementsByTagName(\"script\")[0]; \n" +
                        "  s.parentNode.insertBefore(hm, s);\n" +
                        "})();\n" +
                        "</script>\n</head>")
        ;

        forObject = setBookURIToHTML(forObject, XIYANGYANG_BOOK_REGEX);
        return forObject;
    }

    private void setContentType(String fileFix, HttpServletResponse resp) {
        String contentType = "text/html";
        switch (fileFix) {
            case "js": {
                contentType = "application/javascript";
                break;
            }
            case "css": {
                contentType = "text/css";
                break;
            }
            case "html": {
                contentType = "text/html";
                break;
            }
            default: {
                break;
            }
        }
        resp.setContentType(contentType);


    }

    @Override
    public void destroy() {

    }


}
