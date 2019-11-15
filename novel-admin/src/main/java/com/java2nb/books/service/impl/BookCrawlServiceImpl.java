package com.java2nb.books.service.impl;

import com.java2nb.books.config.CrawlConfig;
import com.java2nb.books.dao.BookContentDao;
import com.java2nb.books.dao.BookDao;
import com.java2nb.books.dao.BookIndexDao;
import com.java2nb.books.domain.BookContentDO;
import com.java2nb.books.domain.BookDO;
import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.util.RestTemplateUtil;
import com.java2nb.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.java2nb.books.dao.BookCrawlDao;
import com.java2nb.books.domain.BookCrawlDO;
import com.java2nb.books.service.BookCrawlService;
import org.springframework.web.client.RestTemplate;


@Service
public class BookCrawlServiceImpl implements BookCrawlService {

    @Autowired
    private CrawlConfig crawlConfig;

    private boolean isInteruptBiquDaoCrawl;//是否中断笔趣岛爬虫程序

    private boolean isInteruptBiquTaCrawl;//是否中断笔趣塔爬虫程序

    private RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");

    @Autowired
    private BookCrawlDao bookCrawlDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookIndexDao bookIndexDao;

    @Autowired
    private BookContentDao bookContentDao;

    @Override
    public BookCrawlDO get(Long id) {
        return bookCrawlDao.get(id);
    }

    @Override
    public List<BookCrawlDO> list(Map<String, Object> map) {
        return bookCrawlDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return bookCrawlDao.count(map);
    }

    @Override
    public int save(BookCrawlDO bookCrawl) {
        return bookCrawlDao.save(bookCrawl);
    }

    @Override
    public int update(BookCrawlDO bookCrawl) {
        return bookCrawlDao.update(bookCrawl);
    }

    @Override
    public int remove(Long id) {
        return bookCrawlDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return bookCrawlDao.batchRemove(ids);
    }

    @Override
    public void updateStatus(BookCrawlDO bookCrawl) {
        bookCrawlDao.update(bookCrawl);

        if (bookCrawl.getStatus() == 0) {
            switch (bookCrawl.getCrawlWebCode()) {
                case 1: {
                    isInteruptBiquDaoCrawl = true;
                    break;
                }
                case 2: {
                    isInteruptBiquTaCrawl = true;
                    break;
                }
            }
        } else {
            crawlBook(bookCrawl);
        }


    }


    private void crawlBook(BookCrawlDO bookCrawl) {
        int threadCount = crawlConfig.getThreadCount();
        int step = 7 / threadCount;
        int pos = step;
        int i = 1;
        while (i <= 7) {
            final int fPos = pos;
            final int fI = i;
            i = pos + 1;
            new Thread(
                    () -> {
                        int j = fI;
                        for (; j <= fPos; j++) {
                            try {

                                switch (bookCrawl.getCrawlWebCode()) {
                                    case 1: {
                                        while (true) {
                                            if (isInteruptBiquDaoCrawl) {
                                                return;
                                            }
                                            crawBiqudaoBooks(j);
                                            Thread.sleep(1000 * 60 * 60 * 24);
                                        }
                                    }
                                    case 2: {
                                        while (true) {
                                            if (isInteruptBiquTaCrawl) {
                                                return;
                                            }
                                            crawBiquTaBooks(j);
                                            Thread.sleep(1000 * 60 * 60 * 24);
                                        }
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
            ).start();
            pos += step;
            if (7 - pos < step) {
                pos = 7;
            }
        }

        new Thread(() -> {
            for (int j = 21; j <= 29; j++) {


                for (int k = 1; k <= 499; k++) {
                    if (isInteruptBiquTaCrawl || isInteruptBiquDaoCrawl) {
                        return;
                    }
                    System.out.println("==============分类============：" + j);
                    System.out.println("==============页码============：" + k);
                    int catId = j;
                    int page = k;

                    String bookListUrl = "http://book.sfacg.com/List/default.aspx?&tid=" + catId + "&if=1&PageIndex=" + page;

                    String forObject = getByHttpClient(bookListUrl);

                    if (forObject != null) {
                        Pattern bookPatten = Pattern.compile("href=\"/Novel/(\\d+)/\"");
                        Matcher bookMatcher = bookPatten.matcher(forObject);
                        boolean isFindBook = bookMatcher.find();

                        while (isFindBook) {
                            try {
                                if (isInteruptBiquTaCrawl || isInteruptBiquDaoCrawl) {
                                    return;
                                }
                                long bookNum = Long.parseLong(bookMatcher.group(1));
                                String bookUrl = "http://book.sfacg.com/Novel/" + bookNum;
                                String forObject1 = getByHttpClient(bookUrl);
                                if (forObject1 != null) {
                                    Pattern updateTimePatten = Pattern.compile("更新：(\\d+/\\d+/\\d+ \\d+:\\d+:\\d+)");
                                    Matcher updateTimeMatch = updateTimePatten.matcher(forObject1);
                                    boolean isFindUpdateTime = updateTimeMatch.find();
                                    if (isFindUpdateTime) {
                                        String updateTimeStr = updateTimeMatch.group(1);
                                        String dateStr = updateTimeStr;
                                        int firstPos = dateStr.indexOf("/");
                                        String year = dateStr.substring(0, firstPos);
                                        dateStr = dateStr.substring(firstPos + 1);
                                        firstPos = dateStr.indexOf("/");
                                        String month = dateStr.substring(0, firstPos);
                                        dateStr = dateStr.substring(firstPos + 1);
                                        firstPos = dateStr.indexOf(" ");
                                        String day = dateStr.substring(0, firstPos);
                                        dateStr = dateStr.substring(firstPos + 1);
                                        firstPos = dateStr.indexOf(":");
                                        String hour = dateStr.substring(0, firstPos);
                                        dateStr = dateStr.substring(firstPos + 1);
                                        firstPos = dateStr.indexOf(":");
                                        String minus = dateStr.substring(0, firstPos);
                                        String second = dateStr.substring(firstPos + 1);
                                        if (month.length() == 1) {
                                            month = "0" + month;
                                        }
                                        if (day.length() == 1) {
                                            day = "0" + day;
                                        }
                                        if (hour.length() == 1) {
                                            hour = "0" + hour;
                                        }
                                        if (minus.length() == 1) {
                                            minus = "0" + minus;
                                        }
                                        if (second.length() == 1) {
                                            second = "0" + second;
                                        }


                                        Date updateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(updateTimeStr);


                                        //Date updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(year+"-"+month+"-"+hour+" "+minus+" "+minus);
                                        Pattern bookNamePatten = Pattern.compile("<h1 class=\"title\">\\s*" +
                                                "<span class=\"text\">([^<]+)</span>\\s*" +
                                                "<span");
                                        Matcher bookNameMatcher = bookNamePatten.matcher(forObject1);
                                        boolean isFindBookName = bookNameMatcher.find();
                                        if (isFindBookName) {
                                            String bookName = bookNameMatcher.group(1);
                                            System.out.println(bookName);
                                            Pattern authorPatten = Pattern.compile("<div class=\"author-name\">\\s*" +
                                                    "<span>([^<]+)</span>\\s*" +
                                                    "</div>");
                                            Matcher authorMatcher = authorPatten.matcher(forObject1);
                                            boolean isFindAuthor = authorMatcher.find();
                                            if (isFindAuthor) {
                                                String author = authorMatcher.group(1);

                                                Pattern picPtten = Pattern.compile("src=\"(http://rs.sfacg.com/web/novel/images/NovelCover/Big/[^\"]+)\"");
                                                Matcher picMatcher = picPtten.matcher(forObject1);
                                                if (picMatcher.find()) {
                                                    String pic = picMatcher.group(1);

                                                    Pattern visitPatten = Pattern.compile(">点击：(\\d+)<");
                                                    Matcher visitMatcher = visitPatten.matcher(forObject1);
                                                    boolean isFindVisit = visitMatcher.find();
                                                    if (isFindVisit) {
                                                        String visit = visitMatcher.group(1);

                                                        Pattern statusPatten = Pattern.compile(">字数：\\d+字\\[([^<]+)\\]<");
                                                        Matcher statusMatcher = statusPatten.matcher(forObject1);
                                                        boolean isFindStatus = statusMatcher.find();
                                                        if (isFindStatus) {
                                                            String status = statusMatcher.group(1);

                                                            if ("已完结".equals(status)) {//先爬已完结的

                                                                status = "已完成";
                                                            }


                                                            Pattern scorePatten = Pattern.compile("<div class=\"num\">\\s*" +
                                                                    "<span>(\\d+\\.\\d+)</span>\\s*" +
                                                                    "</div>");
                                                            Matcher scoreMather = scorePatten.matcher(forObject1);
                                                            boolean isFindScore = scoreMather.find();
                                                            if (isFindScore) {

                                                                float score = Float.parseFloat(scoreMather.group(1));
                                                                //if (score >= 7.0) {

                                                                Pattern descPatten = Pattern.compile("<p class=\"introduce\">\\s*" +
                                                                        "([^<]+)\\s*</p>");
                                                                Matcher descMatcher = descPatten.matcher(forObject1);
                                                                boolean isFindDesc = descMatcher.find();
                                                                if (isFindDesc) {
                                                                    String desc = descMatcher.group(1);

                                                                    Pattern tagPatten = Pattern.compile("<li class=\"tag\">\\s*" +
                                                                            "<a href=\"/stag/\\d+/\" class=\"highlight\"><span class=\"icn\">[^<]+</span><span class=\"text\">([^<]+)</span></a>\\s*" +
                                                                            "</li>");
                                                                    Matcher tagMatch = tagPatten.matcher(forObject1);
                                                                    String tag = "";
                                                                    boolean isFindTag = tagMatch.find();
                                                                    while (isFindTag) {
                                                                        tag += ("," + tagMatch.group(1));
                                                                        isFindTag = tagMatch.find();
                                                                    }

                                                                    if (tag.length() > 0) {
                                                                        tag = tag.substring(1);
                                                                    }


                                                                    BookDO book = new BookDO();
                                                                    book.setAuthor(author);
                                                                    book.setCatid(8);
                                                                    book.setBookDesc(desc);
                                                                    book.setBookName(bookName);
                                                                    book.setSoftTag(tag);
                                                                    book.setSoftCat(catId);
                                                                    book.setScore(score > 10 ? 8.0f : score);
                                                                    book.setVisitCount(Long.parseLong(visit));
                                                                    book.setPicUrl(pic);
                                                                    book.setBookStatus(status);
                                                                    book.setUpdateTime(updateTime);

                                                                    List<BookIndexDO> indexList = new ArrayList<>();
                                                                    List<BookContentDO> contentList = new ArrayList<>();

                                                                    //读取目录
                                                                    String indexUrl = "http://book.sfacg.com/Novel/" + bookNum + "/MainIndex/";
                                                                    String forObject2 = getByHttpClient(indexUrl);
                                                                    if (forObject2 != null) {
                                                                        Pattern indexListPatten = Pattern.compile("href=\"(/Novel/\\d+/\\d+/\\d+/)\"\\s+title=\"([^\"]+)\\s*");
                                                                        Matcher indexListMatch = indexListPatten.matcher(forObject2);

                                                                        boolean isFindIndex = indexListMatch.find();

                                                                        int indexNum = 0;

                                                                        //查询该书籍已存在目录号
                                                                        List<Integer> hasIndexNum = queryIndexCountByBookNameAndBAuthor(bookName, author);

                                                                        while (isFindIndex) {
                                                                            if (isInteruptBiquTaCrawl || isInteruptBiquDaoCrawl) {
                                                                                return;
                                                                            }
                                                                            if (!hasIndexNum.contains(indexNum)) {

                                                                                String contentUrl = "http://book.sfacg.com" + indexListMatch.group(1);
                                                                                String indexName = indexListMatch.group(2);


                                                                                //查询章节内容
                                                                                String forObject3 = getByHttpClient(contentUrl);
                                                                                if (forObject3 != null && !forObject3.contains("内容整改中,请等待")) {
                                                                                    String content = forObject3.substring(forObject3.indexOf("<div class=\"article-content"));
                                                                                    content = content.substring(0, content.indexOf("</div>") + 6);
                                                                                    //TODO插入章节目录和章节内容
                                                                                    BookIndexDO bookIndex = new BookIndexDO();
                                                                                    bookIndex.setIndexName(indexName);
                                                                                    bookIndex.setIndexNum(indexNum);
                                                                                    indexList.add(bookIndex);
                                                                                    BookContentDO bookContent = new BookContentDO();
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
                                                                            saveBookAndIndexAndContent(book, indexList, contentList);
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
                                isFindBook = bookMatcher.find();
                            }
                        }
                    }
                }


            }
        }).start();

    }

    private void crawBiquTaBooks(int i) {
        String baseUrl = "https://m.biquta.com";
        String catBookListUrlBase = baseUrl + "/class/";
        if (crawlConfig.getPriority() == 1) {
            catBookListUrlBase = baseUrl + "/lhb/";
        }
        //拼接分类URL
        int page = 1;//起始页码
        int totalPage = page;
        String catBookListUrl = catBookListUrlBase + i + "/" + page + ".html";
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
                parseBiquTaBook(bookPatten, forObject, i, baseUrl);
                while (currentPage < totalPage) {
                    if (isInteruptBiquTaCrawl) {
                        return;
                    }

                    catBookListUrl = catBookListUrlBase + i + "/" + (currentPage + 1) + ".html";
                    forObject = getByHttpClient(catBookListUrl);
                    if (forObject != null) {
                        //匹配分页数
                        matcher = pattern.matcher(forObject);
                        isFind = matcher.find();

                        if (isFind) {
                            currentPage = Integer.parseInt(matcher.group(1));
                            totalPage = Integer.parseInt(matcher.group(2));
                            parseBiquTaBook(bookPatten, forObject, i, baseUrl);
                        }
                    } else {
                        currentPage++;
                    }
                }
            }
        }
    }

    private void parseBiquTaBook(Pattern bookPatten, String forObject, int catNum, String baseUrl) {
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
            if (isInteruptBiquTaCrawl) {
                return;
            }

            try {
                Float score = Float.parseFloat(scoreMatch.group(1));

                if (score < crawlConfig.getLowestScore()) {//数据库空间有限，暂时爬取8.0分以上的小说
                    continue;
                }

                String bookName = bookNameMatch.group(1);
                String author = authoreMatch.group(1);
                /*//查询该书籍是否存在
                boolean isExsit = bookService.isExsitBook(bookName, author);
                if (isExsit) {
                    continue;
                }*/

                //System.out.println(new Date()+bookName + "：");

                String bokNum = matcher2.group(1);
                String bookUrl = baseUrl + "/" + bokNum + "/";

                String body = getByHttpClient(bookUrl);
                if (body != null) {
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
                            if (updateTime.getTime() < new SimpleDateFormat("yyyy-MM-dd").parse(crawlConfig.getMinUptTime()).getTime()) {
                                continue;
                            }
                            Pattern picPatten = Pattern.compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
                            Matcher picMather = picPatten.matcher(body);
                            if (picMather.find()) {
                                String picSrc = picMather.group(1);

                                Pattern descPatten = Pattern.compile("class=\"review\">([^<]+)</p>");
                                Matcher descMatch = descPatten.matcher(body);
                                if (descMatch.find()) {
                                    String desc = descMatch.group(1);


                                    BookDO book = new BookDO();
                                    book.setAuthor(author);
                                    book.setCatid(catNum);
                                    book.setBookDesc(desc);
                                    book.setBookName(bookName);
                                    book.setScore(score > 10 ? 8.0f : score);
                                    book.setPicUrl(picSrc);
                                    book.setBookStatus(status);
                                    book.setUpdateTime(updateTime);

                                    List<BookIndexDO> indexList = new ArrayList<>();
                                    List<BookContentDO> contentList = new ArrayList<>();

                                    //读取目录
                                    Pattern indexPatten = Pattern.compile("<a\\s+href=\"(/du/\\d+_\\d+/)\">查看完整目录</a>");
                                    Matcher indexMatch = indexPatten.matcher(body);
                                    if (indexMatch.find()) {
                                        String indexUrl = baseUrl + indexMatch.group(1);
                                        String body2 = getByHttpClient(indexUrl);
                                        if (body2 != null) {
                                            Pattern indexListPatten = Pattern.compile("<a\\s+style=\"\"\\s+href=\"(/\\d+_\\d+/\\d+\\.html)\">([^/]+)</a>");
                                            Matcher indexListMatch = indexListPatten.matcher(body2);

                                            boolean isFindIndex = indexListMatch.find();

                                            int indexNum = 0;
                                            //查询该书籍已存在目录号
                                            List<Integer> hasIndexNum = queryIndexCountByBookNameAndBAuthor(bookName, author);

                                            while (isFindIndex) {
                                                if (isInteruptBiquTaCrawl) {
                                                    return;
                                                }

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
                                                        BookIndexDO bookIndex = new BookIndexDO();
                                                        bookIndex.setIndexName(indexName);
                                                        bookIndex.setIndexNum(indexNum);
                                                        indexList.add(bookIndex);
                                                        BookContentDO bookContent = new BookContentDO();
                                                        bookContent.setContent(content);
                                                        bookContent.setIndexNum(indexNum);
                                                        contentList.add(bookContent);
                                                        //System.out.println(indexName);


                                                    } else {
                                                        break;
                                                    }
                                                }
                                                indexNum++;
                                                isFindIndex = indexListMatch.find();
                                            }

                                            if (indexList.size() == contentList.size() && indexList.size() > 0) {
                                                saveBookAndIndexAndContent(book, indexList, contentList);
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


    private void crawBiqudaoBooks(final int i) {
        String baseUrl = "https://m.biqudao.com";
        String catBookListUrlBase = baseUrl + "/bqgeclass/";
        if (crawlConfig.getPriority() == 1) {

            catBookListUrlBase = baseUrl + "/bqgelhb/";
        }
        //拼接分类URL
        int page = 1;//起始页码
        int totalPage = page;
        String catBookListUrl = catBookListUrlBase + i + "/" + page + ".html";
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
                parseBiqudaoBook(bookPatten, forObject, i, baseUrl);
                while (currentPage < totalPage) {

                    if (isInteruptBiquDaoCrawl) {
                        return;
                    }

                    catBookListUrl = catBookListUrlBase + i + "/" + (currentPage + 1) + ".html";
                    forObject = getByHttpClient(catBookListUrl);
                    if (forObject != null) {
                        //匹配分页数
                        matcher = pattern.matcher(forObject);
                        isFind = matcher.find();

                        if (isFind) {
                            currentPage = Integer.parseInt(matcher.group(1));
                            totalPage = Integer.parseInt(matcher.group(2));
                            parseBiqudaoBook(bookPatten, forObject, i, baseUrl);
                        }
                    } else {
                        currentPage++;
                    }
                }
            }
        }

    }

    private void parseBiqudaoBook(Pattern bookPatten, String forObject, int catNum, String baseUrl) {

        Matcher matcher2 = bookPatten.matcher(forObject);
        boolean isFind = matcher2.find();
        Pattern scorePatten = Pattern.compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
        Matcher scoreMatch = scorePatten.matcher(forObject);
        boolean scoreFind = scoreMatch.find();

        Pattern bookNamePatten = Pattern.compile("<p class=\"title\">([^/]+)</p>");
        Matcher bookNameMatch = bookNamePatten.matcher(forObject);
        boolean isBookNameMatch = bookNameMatch.find();

        Pattern authorPatten = Pattern.compile(">作者：([^<]+)<");
        Matcher authoreMatch = authorPatten.matcher(forObject);
        boolean isFindAuthor = authoreMatch.find();


        System.out.println("匹配书籍url" + isFind);

        System.out.println("匹配分数" + scoreFind);
        while (isFind && scoreFind && isBookNameMatch && isFindAuthor) {

            try {
                if (isInteruptBiquDaoCrawl) {
                    return;
                }


                Float score = Float.parseFloat(scoreMatch.group(1));

                if (score < crawlConfig.getLowestScore()) {//数据库空间有限，暂时爬取8.0分以上的小说
                    continue;
                }

                String bookName = bookNameMatch.group(1);
                String author = authoreMatch.group(1);
                /*//查询该书籍是否存在
                boolean isExsit = bookService.isExsitBook(bookName, author);
                if (isExsit) {
                    continue;
                }*/

                //System.out.println(new Date()+bookName + "：");

                String bokNum = matcher2.group(1);
                String bookUrl = baseUrl + "/" + bokNum + "/";

                String body = getByHttpClient(bookUrl);
                if (body != null) {
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
                            if (updateTime.getTime() < new SimpleDateFormat("yyyy-MM-dd").parse(crawlConfig.getMinUptTime()).getTime()) {
                                continue;
                            }
                            Pattern picPatten = Pattern.compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
                            Matcher picMather = picPatten.matcher(body);
                            if (picMather.find()) {
                                String picSrc = picMather.group(1);

                                Pattern descPatten = Pattern.compile("class=\"review\">([^<]+)</p>");
                                Matcher descMatch = descPatten.matcher(body);
                                if (descMatch.find()) {
                                    String desc = descMatch.group(1);


                                    BookDO book = new BookDO();
                                    book.setAuthor(author);
                                    book.setCatid(catNum);
                                    book.setBookDesc(desc);
                                    book.setBookName(bookName);
                                    book.setScore(score > 10 ? 8.0f : score);
                                    book.setPicUrl(picSrc);
                                    book.setBookStatus(status);
                                    book.setUpdateTime(updateTime);

                                    List<BookIndexDO> indexList = new ArrayList<>();
                                    List<BookContentDO> contentList = new ArrayList<>();

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
                                            List<Integer> hasIndexNum = queryIndexCountByBookNameAndBAuthor(bookName, author);

                                            while (isFindIndex) {
                                                if (isInteruptBiquDaoCrawl) {
                                                    return;
                                                }
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
                                                        BookIndexDO bookIndex = new BookIndexDO();
                                                        bookIndex.setIndexName(indexName);
                                                        bookIndex.setIndexNum(indexNum);
                                                        indexList.add(bookIndex);
                                                        BookContentDO bookContent = new BookContentDO();
                                                        bookContent.setContent(content);
                                                        bookContent.setIndexNum(indexNum);
                                                        contentList.add(bookContent);
                                                        //System.out.println(indexName);


                                                    } else {
                                                        break;
                                                    }
                                                }
                                                indexNum++;
                                                isFindIndex = indexListMatch.find();
                                            }

                                            if (indexList.size() == contentList.size() && indexList.size() > 0) {
                                                saveBookAndIndexAndContent(book, indexList, contentList);
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

    public void saveBookAndIndexAndContent(BookDO book, List<BookIndexDO> bookIndex, List<BookContentDO> bookContent) {
        boolean isUpdate = false;
        Long bookId = -1l;
        book.setBookName(book.getBookName().trim());
        book.setAuthor(book.getAuthor().trim());
        Map<String, Object> bookExample = new HashMap<>();
        bookExample.put("bookName", book.getBookName());
        bookExample.put("author", book.getAuthor());
        List<BookDO> books = bookDao.list(bookExample);
        if (books.size() > 0) {
            //更新
            bookId = books.get(0).getId();
            book.setId(bookId);
            bookDao.update(book);
            isUpdate = true;

        } else {
            if (book.getVisitCount() == null) {
                Long visitCount = generateVisiteCount(book.getScore());
                book.setVisitCount(visitCount);
            }
            //插入
            int rows = bookDao.save(book);
            if (rows > 0) {
                bookId = book.getId();
            }


        }

        if (bookId >= 0) {
            //查询目录已存在数量
           /* BookIndexExample bookIndexExample = new BookIndexExample();
            bookIndexExample.createCriteria().andBookIdEqualTo(bookId);
            int indexCount = bookIndexMapper.countByExample(bookIndexExample);*/


            List<BookIndexDO> newBookIndexList = new ArrayList<>();
            List<BookContentDO> newContentList = new ArrayList<>();
            for (int i = 0; i < bookIndex.size(); i++) {
                BookContentDO bookContentItem = bookContent.get(i);
                if (!bookContentItem.getContent().contains("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新")) {


                    BookIndexDO bookIndexItem = bookIndex.get(i);
                    bookIndexItem.setBookId(bookId);
                    bookContentItem.setBookId(bookId);
                    //bookContentItem.setIndexId(bookIndexItem.getId());暂时使用bookId和IndexNum查询content
                    bookContentItem.setIndexNum(bookIndexItem.getIndexNum());

                    newBookIndexList.add(bookIndexItem);
                    newContentList.add(bookContentItem);
                }
            }

            if (newBookIndexList.size() > 0) {
                bookIndexDao.insertBatch(newBookIndexList);

                bookContentDao.insertBatch(newContentList);
            }


        }


    }

    private Long generateVisiteCount(Float score) {
        int baseNum = (int) (Math.pow(score * 10, (int) (score - 5)) / 2);
        return Long.parseLong(baseNum + new Random().nextInt(1000) + "");
    }

    private String getByHttpClient(String catBookListUrl) {
        try {
            /*HttpClient httpClient = new DefaultHttpClient();
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
                    .setRedirectsEnabled(false) // 不自动重定向
                    .build();
            HttpGet getReq = new HttpGet(catBookListUrl);
            getReq.setConfig(requestConfig);
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

    /**
     * 查询该书籍目录数量
     */
    private List<Integer> queryIndexCountByBookNameAndBAuthor(String bookName, String author) {
        List<Integer> result = new ArrayList<>();
        Map<String, Object> bookExample = new HashMap<>();
        bookExample.put("bookName", bookName);
        bookExample.put("author", author);
        List<BookDO> books = bookDao.list(bookExample);
        if (books.size() > 0) {

            Long bookId = books.get(0).getId();
            Map<String, Object> bookIndexExample = new HashMap<>();
            bookExample.put("bookId", bookId);
            List<BookIndexDO> bookIndices = bookIndexDao.list(bookIndexExample);
            if (bookIndices != null && bookIndices.size() > 0) {
                for (BookIndexDO bookIndex : bookIndices) {
                    result.add(bookIndex.getIndexNum());
                }
            }

        }

        return result;

    }
}
