package xyz.zinglizingli.common.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Value;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 更新书籍章节内容定时任务
 *
 * @author 11797
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlBooksSchedule {

    private final BookService bookService;

    private RestTemplate utf8RestTemplate = RestTemplateUtil.getInstance(Charsets.UTF_8);


    @Value("${books.lowestScore}")
    private Float lowestScore;

    @Value("${crawl.website.type}")
    private Byte websiteType;

    @Value("${pic.save.path}")
    private String picSavePath;


    /**
     * 10分钟抓取一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void crawBooks() {

        log.debug("crawlBooksSchedule执行中。。。。。。。。。。。。");

        switch (websiteType) {
            case 1: {
                updateBiqudaoBooks(0);
                break;
            }
            case 2: {
                updateBiquTaBooks(0);
                break;
            }
            default: {
                break;
            }
        }


    }

    /**
     * 从笔趣塔更新
     */
    private void updateBiquTaBooks(int bookClass) {
        String baseUrl = "https://m.biquta.la";
        String catBookListUrlBase = baseUrl + "/class/";

        String catBookListUrl = catBookListUrlBase + bookClass + "/" + 1 + ".html";
        String forObject = getByRestTemplate(catBookListUrl);
        if (forObject != null) {
            Pattern pattern = compile("value=\"(\\d+)/(\\d+)\"");
            Matcher matcher = pattern.matcher(forObject);
            boolean isFind = matcher.find();
            System.out.println("匹配分页数" + isFind);
            if (isFind) {
                //解析第一页书籍的数据
                Pattern bookPatten = compile("href=\"/(\\d+_\\d+)/\"");
                parseBiquTaBook(bookPatten, forObject, baseUrl);
            }
        }
    }

    /**
     * 解析笔趣塔数据
     */
    private void parseBiquTaBook(Pattern bookPatten, String forObject, String baseUrl) {
        Matcher bookMatcher = bookPatten.matcher(forObject);

        boolean isFind = bookMatcher.find();
        Pattern scorePatten = compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
        Matcher scoreMatch = scorePatten.matcher(forObject);
        boolean scoreFind = scoreMatch.find();

        Pattern bookNamePatten = compile("<p class=\"title\">([^/]+)</p>");
        Matcher bookNameMatch = bookNamePatten.matcher(forObject);
        boolean isBookNameMatch = bookNameMatch.find();

        while (isFind && scoreFind && isBookNameMatch) {

            try {
                Float score = Float.parseFloat(scoreMatch.group(1));

                if (score < lowestScore) {
                    continue;
                }

                String bokNum = bookMatcher.group(1);
                String bookUrl = baseUrl + "/" + bokNum + "/";

                String body = getByRestTemplate(bookUrl);
                if (body != null) {

                    String bookName = bookNameMatch.group(1);
                    Pattern authorPatten = compile(">作者：([^/]+)<");
                    Matcher authoreMatch = authorPatten.matcher(body);
                    if (authoreMatch.find()) {
                        String author = authoreMatch.group(1);

                        Pattern statusPatten = compile("状态：([^/]+)</li>");
                        Matcher statusMatch = statusPatten.matcher(body);
                        if (statusMatch.find()) {
                            String status = statusMatch.group(1);

                            Pattern catPatten = compile("类别：([^/]+)</li>");
                            Matcher catMatch = catPatten.matcher(body);
                            if (catMatch.find()) {
                                String catName = catMatch.group(1);
                                int catNum = getCatNum(catName);


                                Pattern updateTimePatten = compile("更新：(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)</a>");
                                Matcher updateTimeMatch = updateTimePatten.matcher(body);
                                if (updateTimeMatch.find()) {
                                    String updateTimeStr = updateTimeMatch.group(1);
                                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                                    Date updateTime = format.parse(updateTimeStr);
                                    Pattern picPatten = compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
                                    Matcher picMather = picPatten.matcher(body);
                                    if (picMather.find()) {
                                        String picSrc = picMather.group(1);


                                        Pattern descPatten = compile("class=\"review\">([^<]+)</p>");
                                        Matcher descMatch = descPatten.matcher(body);
                                        if (descMatch.find()) {
                                            String desc = descMatch.group(1);


                                            Book book = new Book();
                                            book.setAuthor(author);
                                            book.setCatid(catNum);
                                            book.setBookDesc(desc);
                                            book.setBookName(bookName);
                                            book.setScore(score > 10 ? 8.0f : score);
                                            book.setPicUrl(picSrc);
                                            book.setBookStatus(status);
                                            book.setUpdateTime(updateTime);

                                            List<BookIndex> indexList = new ArrayList<>();
                                            List<BookContent> contentList = new ArrayList<>();

                                            //读取目录
                                            Pattern indexPatten = compile("<a\\s+href=\"(/du/\\d+_\\d+/)\">查看完整目录</a>");
                                            Matcher indexMatch = indexPatten.matcher(body);
                                            if (indexMatch.find()) {
                                                String indexUrl = baseUrl + indexMatch.group(1);
                                                String body2 = getByRestTemplate(indexUrl);
                                                if (body2 != null) {
                                                    Pattern indexListPatten = compile("<a\\s+style=\"\"\\s+href=\"(/\\d+_\\d+/\\d+\\.html)\">([^/]+)</a>");
                                                    Matcher indexListMatch = indexListPatten.matcher(body2);

                                                    boolean isFindIndex = indexListMatch.find();

                                                    int indexNum = 0;

                                                    //查询该书籍已存在目录号
                                                    List<Integer> hasIndexNum = bookService.queryIndexNumByBookNameAndAuthor(bookName, author);
                                                    //更新和插入分别开，插入只在凌晨做一次
                                                    if (hasIndexNum.size() > 0) {
                                                        while (isFindIndex) {
                                                            if (!hasIndexNum.contains(indexNum)) {

                                                                String contentUrl = baseUrl + indexListMatch.group(1);
                                                                String indexName = indexListMatch.group(2);


                                                                //查询章节内容
                                                                String body3 = getByRestTemplate(contentUrl);
                                                                if (body3 != null) {
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
                                                            ExcutorUtils.excuteFixedTask(() ->
                                                                    bookService.saveBookAndIndexAndContent(book, indexList, contentList)
                                                            );

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
                bookMatcher.find();
                isFind = bookMatcher.find();
                scoreFind = scoreMatch.find();
                isBookNameMatch = bookNameMatch.find();
            }


        }
    }

    /**
     * 从笔趣岛更新
     */
    private void updateBiqudaoBooks(int bookClass) {
        String baseUrl = "https://m.biqudao.com";
        String catBookListUrlBase = baseUrl + "/bqgeclass/";

        int page = 1;
        String catBookListUrl = catBookListUrlBase + bookClass + "/" + page + ".html";
        String forObject = getByRestTemplate(catBookListUrl);
        if (forObject != null) {
            Pattern pattern = compile("value=\"(\\d+)/(\\d+)\"");
            Matcher matcher = pattern.matcher(forObject);
            boolean isFind = matcher.find();
            System.out.println("匹配分页数" + isFind);
            if (isFind) {
                //解析第一页书籍的数据
                Pattern bookPatten = compile("href=\"/(bqge\\d+)/\"");
                parseBiquDaoBook(bookPatten, forObject, baseUrl);
            }
        }


    }


    /**
     * 解析笔趣岛数据
     */
    private void parseBiquDaoBook(Pattern bookPatten, String forObject, String baseUrl) {

        Matcher bookMatcher = bookPatten.matcher(forObject);
        boolean isFind = bookMatcher.find();
        Pattern scorePatten = compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
        Matcher scoreMatch = scorePatten.matcher(forObject);
        boolean scoreFind = scoreMatch.find();

        Pattern bookNamePatten = compile("<p class=\"title\">([^/]+)</p>");
        Matcher bookNameMatch = bookNamePatten.matcher(forObject);
        boolean isBookNameMatch = bookNameMatch.find();

        while (isFind && scoreFind && isBookNameMatch) {

            try {
                Float score = Float.parseFloat(scoreMatch.group(1));

                if (score < lowestScore) {
                    continue;
                }

                String bokNum = bookMatcher.group(1);
                String bookUrl = baseUrl + "/" + bokNum + "/";

                String body = getByRestTemplate(bookUrl);
                if (body != null) {

                    String bookName = bookNameMatch.group(1);
                    Pattern authorPatten = compile("<li class=\"author\">作者：([^/]+)</li>");
                    Matcher authoreMatch = authorPatten.matcher(body);
                    if (authoreMatch.find()) {
                        String author = authoreMatch.group(1);

                        Pattern statusPatten = compile("状态：([^/]+)</li>");
                        Matcher statusMatch = statusPatten.matcher(body);
                        if (statusMatch.find()) {
                            String status = statusMatch.group(1);

                            Pattern catPatten = compile("类别：([^/]+)</li>");
                            Matcher catMatch = catPatten.matcher(body);
                            if (catMatch.find()) {
                                String catName = catMatch.group(1);
                                int catNum = getCatNum(catName);
                                Pattern updateTimePatten = compile("更新：(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)</a>");
                                Matcher updateTimeMatch = updateTimePatten.matcher(body);
                                if (updateTimeMatch.find()) {
                                    String updateTimeStr = updateTimeMatch.group(1);
                                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                                    Date updateTime = format.parse(updateTimeStr);
                                    Pattern picPatten = compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
                                    Matcher picMather = picPatten.matcher(body);
                                    if (picMather.find()) {
                                        String picSrc = picMather.group(1);


                                        Pattern descPatten = compile("class=\"review\">([^<]+)</p>");
                                        Matcher descMatch = descPatten.matcher(body);
                                        if (descMatch.find()) {
                                            String desc = descMatch.group(1);


                                            Book book = new Book();
                                            book.setAuthor(author);
                                            book.setCatid(catNum);
                                            book.setBookDesc(desc);
                                            book.setBookName(bookName);
                                            book.setScore(score > 10 ? 8.0f : score);
                                            book.setPicUrl(picSrc);
                                            book.setBookStatus(status);
                                            book.setUpdateTime(updateTime);

                                            List<BookIndex> indexList = new ArrayList<>();
                                            List<BookContent> contentList = new ArrayList<>();

                                            //读取目录
                                            Pattern indexPatten = compile("<a\\s+href=\"(/bqge\\d+/all\\.html)\">查看完整目录</a>");
                                            Matcher indexMatch = indexPatten.matcher(body);
                                            if (indexMatch.find()) {
                                                String indexUrl = baseUrl + indexMatch.group(1);
                                                String body2 = getByRestTemplate(indexUrl);
                                                if (body2 != null) {
                                                    Pattern indexListPatten = compile("<a[^/]+style[^/]+href=\"(/bqge\\d+/\\d+\\.html)\">([^/]+)</a>");
                                                    Matcher indexListMatch = indexListPatten.matcher(body2);

                                                    boolean isFindIndex = indexListMatch.find();

                                                    int indexNum = 0;

                                                    //查询该书籍已存在目录号
                                                    List<Integer> hasIndexNum = bookService.queryIndexNumByBookNameAndAuthor(bookName, author);
                                                    //只更新已存在的书籍
                                                    if (hasIndexNum.size() > 0) {
                                                        while (isFindIndex) {
                                                            if (!hasIndexNum.contains(indexNum)) {

                                                                String contentUrl = baseUrl + indexListMatch.group(1);
                                                                String indexName = indexListMatch.group(2);


                                                                //查询章节内容
                                                                String body3 = getByRestTemplate(contentUrl);
                                                                if (body3 != null) {
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
                                                            ExcutorUtils.excuteFixedTask(() -> bookService.saveBookAndIndexAndContent(book, indexList, contentList));

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
                bookMatcher.find();
                isFind = bookMatcher.find();
                scoreFind = scoreMatch.find();
                isBookNameMatch = bookNameMatch.find();
            }


        }

    }

    private int getCatNum(String catName) {
        int catNum;
        switch (catName) {
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
        return catNum;
    }

    private String getByRestTemplate(String url) {
        try {
            ResponseEntity<String> forEntity = utf8RestTemplate.getForEntity(url, String.class);
            if (forEntity.getStatusCode() == HttpStatus.OK) {
                return forEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


}
