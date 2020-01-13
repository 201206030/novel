package xyz.zinglizingli.books.core.crawl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zinglizingli.books.core.utils.Constants;
import xyz.zinglizingli.books.mapper.BookParseLogMapper;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.po.BookContent;
import xyz.zinglizingli.books.po.BookIndex;
import xyz.zinglizingli.books.po.BookParseLog;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.books.core.utils.CatUtil;
import xyz.zinglizingli.common.utils.ExcutorUtils;
import xyz.zinglizingli.common.utils.RestTemplateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author 11797
 */
@Slf4j
public class BiquCrawlSource extends BaseHtmlCrawlSource {


    @Autowired
    private BookService bookService;

    @Override
    public void parse() {

        for(int page = 1; page<= Constants.UPDATE_PAGES_ONCE; page++) {
            String catBookListUrl = getListPageUrl().replace("{0}", "0").replace("{1}", page+"");
            String forObject = RestTemplateUtil.getBodyByUtf8(catBookListUrl);
            if (forObject != null) {
                //解析第一页书籍的数据
                Pattern bookPatten = compile(getBookUrlPattern());

                Matcher bookMatcher = bookPatten.matcher(forObject);

                boolean isFind = bookMatcher.find();
                Pattern scorePatten = compile(getScorePattern());
                Matcher scoreMatch = scorePatten.matcher(forObject);
                boolean scoreFind = scoreMatch.find();

                Pattern bookNamePatten = compile(getBookNamePattern());

                Matcher bookNameMatch = bookNamePatten.matcher(forObject);

                Pattern authorPatten = compile(getAuthorPattern());

                Matcher authorMatch = authorPatten.matcher(forObject);

                boolean isBookNameMatch = bookNameMatch.find();

                while (isFind && scoreFind && isBookNameMatch && authorMatch.find()) {

                    try {
                        Float score = Float.parseFloat(scoreMatch.group(1));

                        if (score < getLowestScore()) {
                            continue;
                        }

                        String bokNum = bookMatcher.group(1);
                        String bookUrl = getIndexUrl() + "/" + bokNum + "/";

                        String bookName = bookNameMatch.group(1);

                        String author = authorMatch.group(1);

                        Boolean hasBook = bookService.hasBook(bookName, author);

                        if(hasBook) {

                            bookService.addBookParseLog(bookUrl, bookName, score);
                        }


                    } catch (Exception e) {

                        log.error(e.getMessage(), e);

                    } finally {
                        bookMatcher.find();
                        isFind = bookMatcher.find();
                        scoreFind = scoreMatch.find();
                        isBookNameMatch = bookNameMatch.find();
                    }


                }
            }
        }

    }

    @Override
    public void update() {
        List<BookParseLog> logs = bookService.queryBookParseLogs();
        List<Long> successLogIds = new ArrayList<>();
        for (BookParseLog bookParseLog : logs) {
            try {

                Float score = bookParseLog.getScore();

                String bookUrl = bookParseLog.getBookUrl();

                String bookName = bookParseLog.getBookName();

                String body = RestTemplateUtil.getBodyByUtf8(bookUrl);
                if (body != null) {


                    Pattern authorPatten = compile(getAuthorPattern());
                    Matcher authoreMatch = authorPatten.matcher(body);
                    if (authoreMatch.find()) {
                        String author = authoreMatch.group(1);

                        Pattern statusPatten = compile(getStatusPattern());
                        Matcher statusMatch = statusPatten.matcher(body);
                        if (statusMatch.find()) {
                            String status = statusMatch.group(1);

                            Pattern catPatten = compile(getCatPattern());
                            Matcher catMatch = catPatten.matcher(body);
                            if (catMatch.find()) {
                                String catName = catMatch.group(1);
                                int catNum = CatUtil.getCatNum(catName);


                                Pattern updateTimePatten = compile(getUpdateTimePattern());
                                Matcher updateTimeMatch = updateTimePatten.matcher(body);
                                if (updateTimeMatch.find()) {
                                    String updateTimeStr = updateTimeMatch.group(1);
                                    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                                    Date updateTime = format.parse(updateTimeStr);
                                    Pattern picPatten = compile(getPicPattern());
                                    Matcher picMather = picPatten.matcher(body);
                                    if (picMather.find()) {
                                        String picSrc = picMather.group(1);
                                        String desc = body.substring(body.indexOf("<p class=\"review\">") + "<p class=\"review\">".length());
                                        desc = desc.substring(0, desc.indexOf("</p>"));


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
                                        Pattern indexPatten = compile(getCatalogUrlPattern());
                                        Matcher indexMatch = indexPatten.matcher(body);
                                        if (indexMatch.find()) {
                                            String indexUrl = getIndexUrl() + indexMatch.group(1);
                                            String body2 = RestTemplateUtil.getBodyByUtf8(indexUrl);
                                            if (body2 != null) {
                                                Pattern indexListPatten = compile(getCatalogPattern());
                                                Matcher indexListMatch = indexListPatten.matcher(body2);

                                                boolean isFindIndex = indexListMatch.find();

                                                int indexNum = 0;

                                                //查询该书籍已存在目录号
                                                Map<Integer, BookIndex> hasIndexs = bookService.queryIndexByBookNameAndAuthor(bookName, author);
                                                //更新和插入分别开，此处只做更新
                                                if (hasIndexs.size() > 0) {
                                                    while (isFindIndex) {
                                                        BookIndex hasIndex = hasIndexs.get(indexNum);
                                                        String indexName = indexListMatch.group(2);

                                                        if (hasIndex == null || !StringUtils.deleteWhitespace(hasIndex.getIndexName()).equals(StringUtils.deleteWhitespace(indexName))) {
                                                            String contentUrl = getIndexUrl() + indexListMatch.group(1);

                                                            //查询章节内容
                                                            String body3 = RestTemplateUtil.getBodyByUtf8(contentUrl.replace("//m.", "//www.").replace("//wap.", "//www."));
                                                            if (body3 != null) {
                                                                String start = "id=\"content\">";
                                                                String end = "<script>";
                                                                String content = body3.substring(body3.indexOf(start) + start.length());
                                                                content = "<div class=\"article-content font16\" id=\"ChapterBody\" data-class=\"font16\">" + content.substring(0, content.indexOf(end)) + "</div>";
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
                                                successLogIds.add(bookParseLog.getId());
                                            }


                                        }


                                    }

                                }
                            }
                        }


                    }

                }

            } catch (Exception e) {

                log.error(e.getMessage(), e);

            }
        }

        bookService.deleteBookParseLogs(successLogIds);

    }


}
