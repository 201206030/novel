package xyz.zinglizingli.common.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.po.BookContent;
import xyz.zinglizingli.books.po.BookIndex;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.books.util.ExcutorUtils;
import xyz.zinglizingli.common.utils.RestTemplateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlBooksSchedule {


    private Logger log = LoggerFactory.getLogger(CrawlBooksSchedule.class);


    @Autowired
    private BookService bookService;

    RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");


    private boolean isExcuting = false;


    public static void main(String[] args) {//<input type="text" class="page_txt" value="1/3019"
        //size="5" name="txtPage" id="txtPage" />
        String forObject = "<input type=\"text\" class=\"page_txt\" value=\"1/3019\" size=\"5\" name=\"txtPage\" id=\"txtPage\" />";

        Pattern pattern = Pattern.compile("<input type=\"text\" class=\"page_txt\" value=\"(.+)/(.+)\"");
        Matcher matcher = pattern.matcher(forObject);
        boolean isFind = matcher.find();
        System.out.println(isFind);
    }

    /**
     * 该项目只更新前1页的书籍
     */
    //@Scheduled(fixedRate = 1000 * 60 * 60 * 2)//暂定2小说，爬等以后书籍多了之后，会适当缩短更新间隔
    public void crawBqutaBooks() throws Exception {
        final String baseUrl = "https://m.biquta.com";
        System.out.println("开始时间" + new Date());


//①爬分类列表的书籍url和总页数
//        https:
////m.biquta.com/class/1/1.html
//        https:
////m.biquta.com/class/2/1.html
//        https:
////m.biquta.com/class/2/2.html
//
//
//        https:
////m.biquta.com/class/2/2.html
//<input type = "text" class="page_txt" value = "2/1244" size = "5" name = "txtPage" id = "txtPage" >
//
//
//<div class="bookinfo" >
//                <a href = "/14_14988/" >
//                            <div class="detail" >
//                                <p class="title" > 苍穹九变 </p >
//                                <p class="author" > 作者：风起闲云</p >
//                            </div >
//                            <div class="score" > 7.5 分</div >
//				</a >
//                        </div >
//
//
        //第一周期全部书拉取完后，可进行第二周期，只拉取前面几页的数据，拉取时间间隔变小
        for (int i = 1; i <= 7; i++) {

            int finalI = i;
            new Thread(
                    () -> {

                        try {
                            //拼接分类URL
                            int page = 1;//起始页码
                            int totalPage = page;
                            String catBookListUrl = baseUrl + "/class/" + finalI + "/" + page + ".html";
                            ResponseEntity<String> forEntity = restTemplate.getForEntity(catBookListUrl, String.class);
                            if (forEntity.getStatusCode() == HttpStatus.OK) {
                                String forObject = forEntity.getBody();
                                //匹配分页数<input type="text" class="page_txt" value="1/3019" size="5" name="txtPage" id="txtPage" />
                                Pattern pattern = Pattern.compile("value=\"(\\d+)/(\\d+)\"");
                                Matcher matcher = pattern.matcher(forObject);
                                boolean isFind = matcher.find();
                                System.out.println("匹配分页数" + isFind);
                                if (isFind) {
                                    int currentPage = Integer.parseInt(matcher.group(1));
                                    totalPage = Integer.parseInt(matcher.group(2));
                                    //解析第一页书籍的数据
                                    Pattern bookPatten = Pattern.compile("href=\"/(\\d+_\\d+)/\"");
                                    parseBook(bookPatten, forObject, restTemplate, finalI, baseUrl);
                                    /*while (currentPage < totalPage) {
                                        catBookListUrl = baseUrl + "/class/" + finalI + "/" + (currentPage + 1) + ".html";
                                        forEntity = restTemplate.getForEntity(catBookListUrl, String.class);
                                        if (forEntity.getStatusCode() == HttpStatus.OK) {
                                            forObject = forEntity.getBody();
                                            //匹配分页数
                                            matcher = pattern.matcher(forObject);
                                            isFind = matcher.find();

                                            if (isFind) {
                                                currentPage = Integer.parseInt(matcher.group(1));
                                                totalPage = Integer.parseInt(matcher.group(2));
                                                parseBook(bookPatten, forObject, restTemplate, finalI, baseUrl);
                                            }
                                        }else{
                                            currentPage++;
                                        }
                                    }*/
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
            ).start();


        }


        System.out.println("结束时间" + new Date());

    }


    // @Scheduled(fixedRate = 1000 * 60 * 30)
    //@Scheduled(fixedRate = 1000 * 60 * 60 * 3)//本机更新，否则服务器压力过大,等书籍多了之后，再去服务器更新，这样更新数量不会很大
    //暂定2小说，只爬分类前3本书，一共3*7=21本书，爬等以后书籍多了之后，会适当缩短更新间隔
    public void crawBqugeTaBooks() throws Exception {
        if (!isExcuting) {
            isExcuting = true;
            final String baseUrl = "https://m.biquta.com";
            log.debug("crawlBooksSchedule执行中。。。。。。。。。。。。");


//①爬分类列表的书籍url和总页数
//        https:
////m.biquta.com/class/1/1.html
//        https:
////m.biquta.com/class/2/1.html
//        https:
////m.biquta.com/class/2/2.html
//
//
//        https:
////m.biquta.com/class/2/2.html
//<input type = "text" class="page_txt" value = "2/1244" size = "5" name = "txtPage" id = "txtPage" >
//
//
//<div class="bookinfo" >
//                <a href = "/14_14988/" >
//                            <div class="detail" >
//                                <p class="title" > 苍穹九变 </p >
//                                <p class="author" > 作者：风起闲云</p >
//                            </div >
//                            <div class="score" > 7.5 分</div >
//				</a >
//                        </div >
//
//
            //第一周期全部书拉取完后，可进行第二周期，只拉取前面几页的数据，拉取时间间隔变小
            RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
            while (true) {
                log.debug("crawlBooksSchedule循环执行中。。。。。。。。。。。。");
                Set<Integer> classIdSet = new HashSet<>();
                for (int i = 1; i <= 7; i++) {

                    log.debug("crawlBooksSchedule分类" + i + "执行中。。。。。。。。。。。。");

                    // int finalI = i;
           /* new Thread(
                    () -> {*/

                    try {
                        //先随机更新分类
                        Random random = new Random();
                        int finalI = random.nextInt(7) + 1;
                        if (classIdSet.contains(finalI)) {
                            finalI = random.nextInt(7) + 1;
                        }
                        classIdSet.add(finalI);
                        //拼接分类URL
                        int page = 1;//起始页码
                        int totalPage = page;
                        String catBookListUrl = baseUrl + "/class/" + finalI + "/" + page + ".html";
                        String forObject = getByHttpClient(catBookListUrl);
                        if (forObject != null) {
                            //匹配分页数<input type="text" class="page_txt" value="1/3019" size="5" name="txtPage" id="txtPage" />
                            Pattern pattern = Pattern.compile("value=\"(\\d+)/(\\d+)\"");
                            Matcher matcher = pattern.matcher(forObject);
                            boolean isFind = matcher.find();
                            System.out.println("匹配分页数" + isFind);
                            if (isFind) {
                                int currentPage = Integer.parseInt(matcher.group(1));
                                totalPage = Integer.parseInt(matcher.group(2));
                                //解析第一页书籍的数据
                                Pattern bookPatten = Pattern.compile("href=\"/(\\d+_\\d+)/\"");
                                parseBook(bookPatten, forObject, restTemplate, finalI, baseUrl);
                                   /* while (currentPage < totalPage) {
                                        catBookListUrl = baseUrl + "/bqgeclass/" + finalI + "/" + (currentPage + 1) + ".html";
                                        forObject = getByHttpClient(catBookListUrl);
                                        if (forObject != null) {
                                            //匹配分页数
                                            matcher = pattern.matcher(forObject);
                                            isFind = matcher.find();

                                            if (isFind) {
                                                currentPage = Integer.parseInt(matcher.group(1));
                                                totalPage = Integer.parseInt(matcher.group(2));
                                                parseBiquge11Book(bookPatten, forObject, finalI, baseUrl);
                                            }
                                        } else {
                                            currentPage++;
                                        }
                                    }*/
                            }
                        }
                        Thread.sleep(1000 * 60 * 10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                  /*  }
            ).start();*/


                }
            }

        }


    }


    //@Scheduled(fixedRate = 1000 * 60 * 35)
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    //暂定2小说，只爬分类前3本书，一共3*7=21本书，爬等以后书籍多了之后，会适当缩短更新间隔
    public void crawBquge11BooksAtDay() throws Exception {
        if (!isExcuting) {
            isExcuting = true;
            final String baseUrl = "https://m.biqudao.com";
            log.debug("crawlBooksSchedule执行中。。。。。。。。。。。。");


//①爬分类列表的书籍url和总页数
//        https:
////m.biquta.com/class/1/1.html
//        https:
////m.biquta.com/class/2/1.html
//        https:
////m.biquta.com/class/2/2.html
//
//
//        https:
////m.biquta.com/class/2/2.html
//<input type = "text" class="page_txt" value = "2/1244" size = "5" name = "txtPage" id = "txtPage" >
//
//
//<div class="bookinfo" >
//                <a href = "/14_14988/" >
//                            <div class="detail" >
//                                <p class="title" > 苍穹九变 </p >
//                                <p class="author" > 作者：风起闲云</p >
//                            </div >
//                            <div class="score" > 7.5 分</div >
//				</a >
//                        </div >
//
//
            //第一周期全部书拉取完后，可进行第二周期，只拉取前面几页的数据，拉取时间间隔变小
            while (true) {
                log.debug("crawlBooksSchedule循环执行中。。。。。。。。。。。。");
                //List<Integer> classIdList = new ArrayList<>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7}));
                // for (int i = 1; i <= 7; i++) {

                // log.debug("crawlBooksSchedule分类"+i+"执行中。。。。。。。。。。。。");

                // int finalI = i;
           /* new Thread(
                    () -> {*/

                try {
                    //先随机更新分类
                    //Random random = new Random();
                    //int finalI = classIdList.get(new Random().nextInt(classIdList.size()));
                    //classIdList.remove(finalI);
                    int finalI = 0;
                    //拼接分类URL
                    int page = 1;//起始页码
                    int totalPage = page;
                    String catBookListUrl = baseUrl + "/bqgeclass/" + finalI + "/" + page + ".html";
                    String forObject = getByHttpClient(catBookListUrl);
                    if (forObject != null) {
                        //匹配分页数<input type="text" class="page_txt" value="1/3019" size="5" name="txtPage" id="txtPage" />
                        Pattern pattern = Pattern.compile("value=\"(\\d+)/(\\d+)\"");
                        Matcher matcher = pattern.matcher(forObject);
                        boolean isFind = matcher.find();
                        System.out.println("匹配分页数" + isFind);
                        if (isFind) {
                            int currentPage = Integer.parseInt(matcher.group(1));
                            totalPage = Integer.parseInt(matcher.group(2));
                            //解析第一页书籍的数据
                            Pattern bookPatten = Pattern.compile("href=\"/(bqge\\d+)/\"");
                            //白天更新
                            parseBiquge11Book(bookPatten, forObject, finalI, baseUrl, true);
                                   /* while (currentPage < totalPage) {
                                        catBookListUrl = baseUrl + "/bqgeclass/" + finalI + "/" + (currentPage + 1) + ".html";
                                        forObject = getByHttpClient(catBookListUrl);
                                        if (forObject != null) {
                                            //匹配分页数
                                            matcher = pattern.matcher(forObject);
                                            isFind = matcher.find();

                                            if (isFind) {
                                                currentPage = Integer.parseInt(matcher.group(1));
                                                totalPage = Integer.parseInt(matcher.group(2));
                                                parseBiquge11Book(bookPatten, forObject, finalI, baseUrl);
                                            }
                                        } else {
                                            currentPage++;
                                        }
                                    }*/
                        }
                    }
                    Thread.sleep(1000 * 60 * 5);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                  /*  }
            ).start();*/


                // }
            }

        }


    }


    //@Scheduled(cron = "0 0 2 * * ?")磁盘空间不足，暂时不抓新书
    //暂定2小说，只爬分类前3本书，一共3*7=21本书，爬等以后书籍多了之后，会适当缩短更新间隔
    public void crawBquge11BooksAtNight() throws Exception {
        final String baseUrl = "https://m.biqudao.com";
        log.debug("crawlBooksSchedule执行中。。。。。。。。。。。。");


//①爬分类列表的书籍url和总页数
//        https:
////m.biquta.com/class/1/1.html
//        https:
////m.biquta.com/class/2/1.html
//        https:
////m.biquta.com/class/2/2.html
//
//
//        https:
////m.biquta.com/class/2/2.html
//<input type = "text" class="page_txt" value = "2/1244" size = "5" name = "txtPage" id = "txtPage" >
//
//
//<div class="bookinfo" >
//                <a href = "/14_14988/" >
//                            <div class="detail" >
//                                <p class="title" > 苍穹九变 </p >
//                                <p class="author" > 作者：风起闲云</p >
//                            </div >
//                            <div class="score" > 7.5 分</div >
//				</a >
//                        </div >
//
//
        //第一周期全部书拉取完后，可进行第二周期，只拉取前面几页的数据，拉取时间间隔变小
        log.debug("crawlBooksSchedule循环执行中。。。。。。。。。。。。");
        //List<Integer> classIdList = new ArrayList<>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7}));
        // for (int i = 1; i <= 7; i++) {

        // log.debug("crawlBooksSchedule分类"+i+"执行中。。。。。。。。。。。。");

        // int finalI = i;
           /* new Thread(
                    () -> {*/

        try {
            //先随机更新分类
            //Random random = new Random();
            //int finalI = classIdList.get(new Random().nextInt(classIdList.size()));
            //classIdList.remove(finalI);
            int finalI = 0;
            //拼接分类URL
            int page = 1;//起始页码
            int totalPage = page;
            String catBookListUrl = baseUrl + "/bqgeclass/" + finalI + "/" + page + ".html";
            String forObject = getByHttpClient(catBookListUrl);
            if (forObject != null) {
                //匹配分页数<input type="text" class="page_txt" value="1/3019" size="5" name="txtPage" id="txtPage" />
                Pattern pattern = Pattern.compile("value=\"(\\d+)/(\\d+)\"");
                Matcher matcher = pattern.matcher(forObject);
                boolean isFind = matcher.find();
                System.out.println("匹配分页数" + isFind);
                if (isFind) {
                    int currentPage = Integer.parseInt(matcher.group(1));
                    totalPage = Integer.parseInt(matcher.group(2));
                    //解析第一页书籍的数据
                    Pattern bookPatten = Pattern.compile("href=\"/(bqge\\d+)/\"");
                    //晚上插入
                    parseBiquge11Book(bookPatten, forObject, finalI, baseUrl, false);
                    while (currentPage < totalPage) {
                        if (new Date().getHours() > 5) {
                            break;
                        }
                        catBookListUrl = baseUrl + "/bqgeclass/" + finalI + "/" + (currentPage + 1) + ".html";
                        forObject = getByHttpClient(catBookListUrl);
                        if (forObject != null) {
                            //匹配分页数
                            matcher = pattern.matcher(forObject);
                            isFind = matcher.find();

                            if (isFind) {
                                currentPage = Integer.parseInt(matcher.group(1));
                                totalPage = Integer.parseInt(matcher.group(2));
                                parseBiquge11Book(bookPatten, forObject, finalI, baseUrl, false);
                            }
                        } else {
                            currentPage++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

                  /*  }
            ).start();*/


        // }


    }

    private void parseBiquge11Book(Pattern bookPatten, String forObject, int catNum, String baseUrl, boolean isUpdate) {

        Matcher matcher2 = bookPatten.matcher(forObject);
        boolean isFind = matcher2.find();
        Pattern scorePatten = Pattern.compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
        Matcher scoreMatch = scorePatten.matcher(forObject);
        boolean scoreFind = scoreMatch.find();

        Pattern bookNamePatten = Pattern.compile("<p class=\"title\">([^/]+)</p>");
        Matcher bookNameMatch = bookNamePatten.matcher(forObject);
        boolean isBookNameMatch = bookNameMatch.find();


        System.out.println("匹配书籍url" + isFind);

        System.out.println("匹配分数" + scoreFind);

        while (isFind && scoreFind && isBookNameMatch) {

            try {
                Float score = Float.parseFloat(scoreMatch.group(1));

                if (score < 8.0) {//数据库空间有效暂时爬取7.5分以上的小说
                    continue;
                }

                String bokNum = matcher2.group(1);
                String bookUrl = baseUrl + "/" + bokNum + "/";

                String body = getByHttpClient(bookUrl);
                if (body != null) {

                    String bookName = bookNameMatch.group(1);
                    Pattern authorPatten = Pattern.compile("<li class=\"author\">作者：([^/]+)</li>");
                    Matcher authoreMatch = authorPatten.matcher(body);
                    if (authoreMatch.find()) {
                        String author = authoreMatch.group(1);

                        Pattern statusPatten = Pattern.compile("状态：([^/]+)</li>");
                        Matcher statusMatch = statusPatten.matcher(body);
                        if (statusMatch.find()) {
                            String status = statusMatch.group(1);

                            Pattern catPatten = Pattern.compile("类别：([^/]+)</li>");
                            Matcher catMatch = catPatten.matcher(body);
                            if (catMatch.find()) {
                                String catName = catMatch.group(1);
                                switch (catName) {
                                    case "玄幻奇幻": {
                                        catNum = 1;
                                        break;
                                    }
                                    case "武侠仙侠": {
                                        catNum = 2;
                                        break;
                                    }
                                    case "都市言情": {
                                        catNum = 3;
                                        break;
                                    }
                                    case "历史军事": {
                                        catNum = 4;
                                        break;
                                    }
                                    case "科幻灵异": {
                                        catNum = 5;
                                        break;
                                    }
                                    case "网游竞技": {
                                        catNum = 6;
                                        break;
                                    }
                                    case "女生频道": {
                                        catNum = 7;
                                        break;
                                    }
                                    default: {
                                        catNum = 1;
                                        break;
                                    }
                                }
                                Pattern updateTimePatten = Pattern.compile("更新：(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)</a>");
                                Matcher updateTimeMatch = updateTimePatten.matcher(body);
                                if (updateTimeMatch.find()) {
                                    String updateTimeStr = updateTimeMatch.group(1);
                                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                                    Date updateTime = format.parse(updateTimeStr);
                                    Pattern picPatten = Pattern.compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
                                    Matcher picMather = picPatten.matcher(body);
                                    if (picMather.find()) {
                                        String picSrc = picMather.group(1);

                                        Pattern descPatten = Pattern.compile("class=\"review\">([^<]+)</p>");
                                        Matcher descMatch = descPatten.matcher(body);
                                        if (descMatch.find()) {
                                            String desc = descMatch.group(1);


                                            Book book = new Book();
                                            book.setAuthor(author);
                                            book.setCatid(catNum);
                                            book.setBookDesc(desc);
                                            book.setBookName(bookName);
                                            book.setScore(score);
                                            book.setPicUrl(picSrc);
                                            book.setBookStatus(status);
                                            book.setUpdateTime(updateTime);

                                            List<BookIndex> indexList = new ArrayList<>();
                                            List<BookContent> contentList = new ArrayList<>();

                                            //读取目录
                                            Pattern indexPatten = Pattern.compile("<a\\s+href=\"(/bqge\\d+/all\\.html)\">查看完整目录</a>");
                                            Matcher indexMatch = indexPatten.matcher(body);
                                            if (indexMatch.find()) {
                                                String indexUrl = baseUrl + indexMatch.group(1);
                                                String body2 = getByHttpClient(indexUrl);
                                                if (body2 != null) {
                                                    Pattern indexListPatten = Pattern.compile("<a[^/]+style[^/]+href=\"(/bqge\\d+/\\d+\\.html)\">([^/]+)</a>");
                                                    Matcher indexListMatch = indexListPatten.matcher(body2);

                                                    boolean isFindIndex = indexListMatch.find();

                                                    int indexNum = 0;

                                                    //查询该书籍已存在目录号
                                                    List<Integer> hasIndexNum = bookService.queryIndexCountByBookNameAndBAuthor(bookName, author);
                                                    //更新和插入分别开，插入只在凌晨做一次
                                                    if ((isUpdate && hasIndexNum.size() > 0) || (!isUpdate && hasIndexNum.size() == 0)) {
                                                        while (isFindIndex) {
                                                            if (!hasIndexNum.contains(indexNum)) {

                                                                String contentUrl = baseUrl + indexListMatch.group(1);
                                                                String indexName = indexListMatch.group(2);


                                                                //查询章节内容
                                                                String body3 = getByHttpClient(contentUrl);
                                                                if (body3 != null) {
                                                                    Pattern contentPattten = Pattern.compile("章节错误,点此举报(.*)加入书签，方便阅读");
                                                                    String start = "『章节错误,点此举报』";
                                                                    String end = "『加入书签，方便阅读』";
                                                                    String content = body3.substring(body3.indexOf(start) + start.length(), body3.indexOf(end));
                                                                    //TODO插入章节目录和章节内容
                                                                    BookIndex bookIndex = new BookIndex();
                                                                    bookIndex.setIndexName(indexName);
                                                                    bookIndex.setIndexNum(indexNum);
                                                                    indexList.add(bookIndex);
                                                                    BookContent bookContent = new BookContent();
                                                                    bookContent.setContent(content);
                                                                    bookContent.setIndexNum(indexNum);
                                                                    contentList.add(bookContent);


                                                                } else {
                                                                    break;
                                                                }


                                                            }
                                                            indexNum++;
                                                            isFindIndex = indexListMatch.find();
                                                        }

                                                        if (indexList.size() == contentList.size() && indexList.size() > 0) {
                                                            ExcutorUtils.excuteFixedTask(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    bookService.saveBookAndIndexAndContent(book, indexList, contentList);
                                                                }
                                                            });

                                                        }
                                                    }
                                                }


                                            }


                                        }


                                    }
                                }
                            }
                        }


                    }

                }


            } catch (Exception e) {

                e.printStackTrace();

            } finally {
                matcher2.find();
                isFind = matcher2.find();//需要找两次，应为有两个一样的路径匹配
                scoreFind = scoreMatch.find();
                isBookNameMatch = bookNameMatch.find();
            }


        }

    }

    private String getByHttpClient(String catBookListUrl) {
        try {
           /* HttpClient httpClient = new DefaultHttpClient();
            HttpGet getReq = new HttpGet(catBookListUrl);
            getReq.setHeader("user-agent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
            HttpResponse execute = httpClient.execute(getReq);
            if (execute.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                HttpEntity entity = execute.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            } else {
                return null;
            }*/
            //经测试restTemplate比httpClient效率高出很多倍，所有选择restTemplate
            ResponseEntity<String> forEntity = restTemplate.getForEntity(catBookListUrl, String.class);
            if (forEntity.getStatusCode() == HttpStatus.OK) {
                return forEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 解析书籍详情之后的页面
     */
    private void parseBook(Pattern bookPatten, String forObject, RestTemplate restTemplate, int catNum, String baseUrl) throws ParseException {
        Matcher matcher2 = bookPatten.matcher(forObject);
        boolean isFind = matcher2.find();
        Pattern scorePatten = Pattern.compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
        Matcher scoreMatch = scorePatten.matcher(forObject);
        boolean scoreFind = scoreMatch.find();

        Pattern bookNamePatten = Pattern.compile("<p class=\"title\">([^/]+)</p>");
        Matcher bookNameMatch = bookNamePatten.matcher(forObject);
        boolean isBookNameMatch = bookNameMatch.find();

        Pattern authorPatten = Pattern.compile(">作者：([^/]+)<");
        Matcher authoreMatch = authorPatten.matcher(forObject);
        boolean isFindAuthor = authoreMatch.find();


        System.out.println("匹配书籍url" + isFind);

        System.out.println("匹配分数" + scoreFind);
        while (isFind && scoreFind && isBookNameMatch && isFindAuthor) {

            try {
                Float score = Float.parseFloat(scoreMatch.group(1));

                if (score < 8.0) {//数据库空间有效暂时爬取7.5分以上的小说
                    continue;
                }
                String bookName = bookNameMatch.group(1);
                String author = authoreMatch.group(1);

                String bokNum = matcher2.group(1);
                String bookUrl = baseUrl + "/" + bokNum + "/";

                ResponseEntity<String> forEntity = restTemplate.getForEntity(bookUrl, String.class);
                if (forEntity.getStatusCode() == HttpStatus.OK) {

                    String body = forEntity.getBody();

                    Pattern statusPatten = Pattern.compile("状态：([^/]+)</li>");
                    Matcher statusMatch = statusPatten.matcher(body);
                    if (statusMatch.find()) {
                        String status = statusMatch.group(1);
                        Pattern updateTimePatten = Pattern.compile("更新：(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)</a>");
                        Matcher updateTimeMatch = updateTimePatten.matcher(body);
                        if (updateTimeMatch.find()) {
                            String updateTimeStr = updateTimeMatch.group(1);
                            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                            Date updateTime = format.parse(updateTimeStr);
                            Pattern picPatten = Pattern.compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
                            Matcher picMather = picPatten.matcher(body);
                            if (picMather.find()) {
                                String picSrc = picMather.group(1);

                                Pattern descPatten = Pattern.compile("class=\"review\">([^<]+)</p>");
                                Matcher descMatch = descPatten.matcher(body);
                                if (descMatch.find()) {
                                    String desc = descMatch.group(1);


                                    Book book = new Book();
                                    book.setAuthor(author);
                                    book.setCatid(catNum);
                                    book.setBookDesc(desc);
                                    book.setBookName(bookName);
                                    book.setScore(score);
                                    book.setPicUrl(picSrc);
                                    book.setBookStatus(status);
                                    book.setUpdateTime(updateTime);

                                    List<BookIndex> indexList = new ArrayList<>();
                                    List<BookContent> contentList = new ArrayList<>();

                                    //读取目录
                                    Pattern indexPatten = Pattern.compile("<a\\s+href=\"(/du/\\d+_\\d+/)\">查看完整目录</a>");
                                    Matcher indexMatch = indexPatten.matcher(body);
                                    if (indexMatch.find()) {
                                        String indexUrl = baseUrl + indexMatch.group(1);
                                        ResponseEntity<String> forEntity1 = restTemplate.getForEntity(indexUrl, String.class);
                                        if (forEntity1.getStatusCode() == HttpStatus.OK) {
                                            String body2 = forEntity1.getBody();
                                            Pattern indexListPatten = Pattern.compile("<a\\s+style=\"\"\\s+href=\"(/\\d+_\\d+/\\d+\\.html)\">([^/]+)</a>");
                                            Matcher indexListMatch = indexListPatten.matcher(body2);

                                            boolean isFindIndex = indexListMatch.find();

                                            int indexNum = 0;

                                            //查询该书籍已存在目录号
                                            List<Integer> hasIndexNum = bookService.queryIndexCountByBookNameAndBAuthor(bookName, author);

                                            while (isFindIndex) {
                                                if (!hasIndexNum.contains(indexNum)) {

                                                    String contentUrl = baseUrl + indexListMatch.group(1);
                                                    String indexName = indexListMatch.group(2);


                                                    //查询章节内容
                                                    ResponseEntity<String> forEntity2 = restTemplate.getForEntity(contentUrl, String.class);
                                                    if (forEntity2.getStatusCode() == HttpStatus.OK) {
                                                        String body3 = forEntity2.getBody();
                                                        Pattern contentPattten = Pattern.compile("章节错误,点此举报(.*)加入书签，方便阅读");
                                                        String start = "『章节错误,点此举报』";
                                                        String end = "『加入书签，方便阅读』";
                                                        String content = body3.substring(body3.indexOf(start) + start.length(), body3.indexOf(end));
                                                        //TODO插入章节目录和章节内容
                                                        BookIndex bookIndex = new BookIndex();
                                                        bookIndex.setIndexName(indexName);
                                                        bookIndex.setIndexNum(indexNum);
                                                        indexList.add(bookIndex);
                                                        BookContent bookContent = new BookContent();
                                                        bookContent.setContent(content);
                                                        bookContent.setIndexNum(indexNum);
                                                        contentList.add(bookContent);


                                                    } else {
                                                        break;
                                                    }

                                                }

                                                indexNum++;
                                                isFindIndex = indexListMatch.find();
                                            }
                                            if (indexList.size() == contentList.size() && indexList.size() > 0) {
                                                bookService.saveBookAndIndexAndContent(book, indexList, contentList);
                                            }


                                        }
                                    }


                                }


                            }
                        }


                    }

                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {
                matcher2.find();
                isFind = matcher2.find();//需要找两次，应为有两个一样的路径匹配
                scoreFind = scoreMatch.find();
                isBookNameMatch = bookNameMatch.find();
                isFindAuthor = authoreMatch.find();
            }


        }

    }
}
