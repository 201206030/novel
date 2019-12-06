package xyz.zinglizingli.books.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.orderbyhelper.OrderByHelper;
import xyz.zinglizingli.books.constant.CacheKeyConstans;
import xyz.zinglizingli.books.mapper.*;
import xyz.zinglizingli.books.po.*;
import xyz.zinglizingli.books.util.UUIDUtils;
import xyz.zinglizingli.common.cache.CommonCacheUtil;
import xyz.zinglizingli.common.utils.RestTemplateUtil;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookIndexMapper bookIndexMapper;

    @Autowired
    private BookContentMapper bookContentMapper;

    @Autowired
    private ScreenBulletMapper screenBulletMapper;

    @Autowired
    private UserRefBookMapper userRefBookMapper;

    @Autowired
    private CommonCacheUtil cacheUtil;

    RestTemplate isoRestTemplate = RestTemplateUtil.getInstance("iso-8859-1");

    @Value("${pic.save.type}")
    private Byte picSaveType;

    @Value("${pic.save.path}")
    private String picSavePath;


    private Logger log = LoggerFactory.getLogger(BookService.class);



    public void saveBookAndIndexAndContent(Book book, List<BookIndex> bookIndex, List<BookContent> bookContent){
        //一次最多只允许插入20条记录,否则影响服务器响应,如果没有插入所有更新，则更新时间设为昨天
        /*if(bookIndex.size()>100){
            book.setUpdateTime(new Date(book.getUpdateTime().getTime()-1000*60*60*24));
        }
*/

        boolean isUpdate = false;
        Long bookId = -1L;
        book.setBookName(book.getBookName().trim());
        book.setAuthor(book.getAuthor().trim());
        BookExample example = new BookExample();
        example.createCriteria().andBookNameEqualTo(book.getBookName()).andAuthorEqualTo(book.getAuthor());
        List<Book> books = bookMapper.selectByExample(example);
        if (books.size() > 0) {
            //更新
            bookId = books.get(0).getId();
            book.setId(bookId);
            String picSrc = book.getPicUrl();
            if(picSaveType == 2 && org.apache.commons.lang3.StringUtils.isNotBlank(picSrc)){
                try {
                    HttpHeaders headers = new HttpHeaders();
                    HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
                    ResponseEntity<Resource> resEntity = isoRestTemplate.exchange(picSrc, HttpMethod.GET, requestEntity, Resource.class);
                    InputStream input = resEntity.getBody().getInputStream();
                    Date currentDate = new Date();
                    picSrc = "/localPic/" + DateUtils.formatDate(currentDate, "yyyy") + "/" + DateUtils.formatDate(currentDate, "MM") + "/" + DateUtils.formatDate(currentDate, "dd")
                            + UUIDUtils.getUUID32()
                            + picSrc.substring(picSrc.lastIndexOf("."));
                    File picFile = new File(picSavePath + picSrc);
                    File parentFile = picFile.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    OutputStream out = new FileOutputStream(picFile);
                    byte[] b = new byte[4096];
                    for (int n; (n = input.read(b)) != -1; ) {
                        out.write(b, 0, n);
                    }
                    out.close();
                    input.close();
                    book.setPicUrl(picSrc);
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }

            }
            bookMapper.updateByPrimaryKeySelective(book);
            isUpdate = true;

        } else {
            if (book.getVisitCount() == null) {
                Long visitCount = generateVisiteCount(book.getScore());
                book.setVisitCount(visitCount);
            }
            //插入
            int rows = bookMapper.insertSelective(book);
            if (rows > 0) {
                bookId = book.getId();
            }


        }

        if (bookId >= 0) {
            //查询目录已存在数量
           /* BookIndexExample bookIndexExample = new BookIndexExample();
            bookIndexExample.createCriteria().andBookIdEqualTo(bookId);
            int indexCount = bookIndexMapper.countByExample(bookIndexExample);*/

            BookIndex lastIndex = null;
            List<BookIndex> newBookIndexList = new ArrayList<>();
            List<BookContent> newContentList = new ArrayList<>();
            for (int i = 0; i < bookIndex.size(); i++) {
                BookContent bookContentItem = bookContent.get(i);
                if (!bookContentItem.getContent().contains("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新")) {
                    BookIndex bookIndexItem = bookIndex.get(i);
                    bookIndexItem.setBookId(bookId);
                    bookContentItem.setBookId(bookId);
                    //bookContentItem.setIndexId(bookIndexItem.getId());暂时使用bookId和IndexNum查询content
                    bookContentItem.setIndexNum(bookIndexItem.getIndexNum());
                    newBookIndexList.add(bookIndexItem);
                    newContentList.add(bookContentItem);
                    lastIndex = bookIndexItem;
                }
                //一次最多只允许插入20条记录,否则影响服务器响应
                if (isUpdate && i % 20 == 0 && newBookIndexList.size() > 0) {
                    insertIndexListAndContentList(newBookIndexList, newContentList);
                    newBookIndexList = new ArrayList<>();
                    newContentList = new ArrayList<>();
                    try {
                        Thread.sleep(1000 * 60 * 5);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e.getMessage());
                    }
                }
            }


            if (newBookIndexList.size() > 0) {
                insertIndexListAndContentList(newBookIndexList, newContentList);
            }

            cacheUtil.del(CacheKeyConstans.NEWST_BOOK_LIST_KEY);


        }


    }

    @Transactional
    public void insertIndexListAndContentList(List<BookIndex> newBookIndexList, List<BookContent> newContentList) {
        bookIndexMapper.insertBatch(newBookIndexList);
        bookContentMapper.insertBatch(newContentList);
    }


    private Long generateVisiteCount(Float score) {
        int baseNum = (int) (Math.pow(score * 10, (int) (score - 5)) / 2);
        return Long.parseLong(baseNum + new Random().nextInt(1000) + "");
    }

    /**
     * 分页查询
     */
    public List<Book> search(int page, int pageSize,
                             String userId, String ids, String keyword, String bookStatus, Integer catId, Integer softCat, String softTag, String sortBy, String sort) {

        if (!StringUtils.isEmpty(userId)) {
            sortBy = "user_ref_book.id";
            sort = "DESC";
        }
        PageHelper.startPage(page, pageSize);
        // 排序设置[注意orderby 紧跟分页后面]
        if (!StringUtils.isEmpty(sortBy)) {
            OrderByHelper.orderBy(sortBy + " " + sort);
        }

        List<Book> books = bookMapper.search(userId, ids, keyword, catId, softCat, softTag, bookStatus);

        return books;

    }

    public String getCatNameById(Integer catid) {
        String catName = "其他";

        switch (catid) {
            case 1: {
                catName = "玄幻奇幻";
                break;
            }
            case 2: {
                catName = "武侠仙侠";
                break;
            }
            case 3: {
                catName = "都市言情";
                break;
            }
            case 4: {
                catName = "历史军事";
                break;
            }
            case 5: {
                catName = "科幻灵异";
                break;
            }
            case 6: {
                catName = "网游竞技";
                break;
            }
            case 7: {
                catName = "女生频道";
                break;
            }
            case 8: {
                catName = "轻小说";
                break;
            }
            case 9: {
                catName = "漫画";
                break;
            }
            default: {
                break;
            }


        }
        return catName;
    }

    public Book queryBaseInfo(Long bookId) {

        return bookMapper.selectByPrimaryKey(bookId);
    }

    public List<BookIndex> queryNewIndexList(Long bookId) {
        PageHelper.startPage(1, 15);
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        example.setOrderByClause("index_num DESC");
        return bookIndexMapper.selectByExample(example);

    }

    public List<BookIndex> queryAllIndexList(Long bookId) {
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        example.setOrderByClause("index_num ASC");
        return bookIndexMapper.selectByExample(example);
    }

    public BookContent queryBookContent(Long bookId, Integer indexNum) {
        BookContent content = (BookContent) cacheUtil.getObject(CacheKeyConstans.BOOK_CONTENT_KEY_PREFIX + "_" + bookId + "_" + indexNum);
        if (content == null) {
            BookContentExample example = new BookContentExample();
            example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(indexNum);
            List<BookContent> bookContents = bookContentMapper.selectByExample(example);
            content = bookContents.size() > 0 ? bookContents.get(0) : null;
            /*try {
                content.setContent(chargeBookContent(content.getContent()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }*/
            cacheUtil.setObject(CacheKeyConstans.BOOK_CONTENT_KEY_PREFIX + "_" + bookId + "_" + indexNum, content, 60 * 60 * 24);
        }

        return content;
    }


    public void addVisitCount(Long bookId, String userId, Integer indexNum) {

        bookMapper.addVisitCount(bookId);

        if(org.apache.commons.lang3.StringUtils.isNotBlank(userId)) {
            userRefBookMapper.updateNewstIndex(bookId, userId, indexNum);
        }

    }

    public String queryIndexNameByBookIdAndIndexNum(Long bookId, Integer indexNum) {

        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(indexNum);
        return bookIndexMapper.selectByExample(example).get(0).getIndexName();
    }

    public List<Integer> queryMaxAndMinIndexNum(Long bookId) {
        List<Integer> result = new ArrayList<>();
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        example.setOrderByClause("index_num desc");
        List<BookIndex> bookIndices = bookIndexMapper.selectByExample(example);
        if (bookIndices.size() > 0) {
            result.add(bookIndices.get(0).getIndexNum());
            result.add(bookIndices.get(bookIndices.size() - 1).getIndexNum());
        }
        return result;
    }

    /**
     * 查询该书籍目录数量
     */
    public List<Integer> queryIndexCountByBookNameAndBAuthor(String bookName, String author) {
        List<Integer> result = new ArrayList<>();
        BookExample example = new BookExample();
        example.createCriteria().andBookNameEqualTo(bookName).andAuthorEqualTo(author);
        List<Book> books = bookMapper.selectByExample(example);
        if (books.size() > 0) {

            Long bookId = books.get(0).getId();
            BookIndexExample bookIndexExample = new BookIndexExample();
            bookIndexExample.createCriteria().andBookIdEqualTo(bookId);
            List<BookIndex> bookIndices = bookIndexMapper.selectByExample(bookIndexExample);
            if (bookIndices != null && bookIndices.size() > 0) {
                for (BookIndex bookIndex : bookIndices) {
                    result.add(bookIndex.getIndexNum());
                }
            }

        }

        return result;

    }

    public Book queryRandomBook() {

        return bookMapper.queryRandomBook();
    }

    public Map<String, Object> queryNewstBook() {
        final String SENDIDS = "sendWeiboIds";
        Set<Long> sendIds = (Set<Long>) cacheUtil.getObject(SENDIDS);
        if (sendIds == null) {
            sendIds = new HashSet<>();
        }
        String newstIndexName = null;
        Book book = null;
        book = bookMapper.queryNewstBook(sendIds);
        Map<String, Object> data = new HashMap<>();
        if (book != null && book.getId() != null) {
            newstIndexName = bookIndexMapper.queryNewstIndexName(book.getId());
            if (!StringUtils.isEmpty(newstIndexName)) {
                sendIds.add(book.getId());
                cacheUtil.setObject(SENDIDS, sendIds, 60 * 60 * 24 * 2);
                data.put("book", book);
                data.put("newstIndexName", newstIndexName);
            }
        }
        return data;
    }

    public String getSoftCatNameById(Integer softCat) {
        String catName = "其他";

        switch (softCat) {
            case 21: {
                catName = "魔幻";
                break;
            }
            case 22: {
                catName = "玄幻";
                break;
            }
            case 23: {
                catName = "古风";
                break;
            }
            case 24: {
                catName = "科幻";
                break;
            }
            case 25: {
                catName = "校园";
                break;
            }
            case 26: {
                catName = "都市";
                break;
            }
            case 27: {
                catName = "游戏";
                break;
            }
            case 28: {
                catName = "同人";
                break;
            }
            case 29: {
                catName = "悬疑";
                break;
            }
            case 0: {
                catName = "动漫";
                break;
            }
            default: {
                break;
            }


        }
        return catName;

    }

    public String getMhCatNameById(Integer softCat) {
        String catName = "其他";

        switch (softCat) {
            case 3262: {
                catName = "少年漫";
                break;
            }
            case 3263: {
                catName = "少女漫";
                break;
            }
            default: {
                break;
            }


        }
        return catName;

    }

    public void sendBullet(Long contentId, String bullet) {

        ScreenBullet screenBullet = new ScreenBullet();
        screenBullet.setContentId(contentId);
        screenBullet.setScreenBullet(bullet);
        screenBullet.setCreateTime(new Date());

        screenBulletMapper.insertSelective(screenBullet);
    }

    public List<ScreenBullet> queryBullet(Long contentId) {

        ScreenBulletExample example = new ScreenBulletExample();
        example.createCriteria().andContentIdEqualTo(contentId);
        example.setOrderByClause("create_time asc");

        return screenBulletMapper.selectByExample(example);
    }

    public String queryIndexList(Long bookId, int count) {

        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(count);
        return bookIndexMapper.selectByExample(example).get(0).getIndexName();
    }

    public String queryContentList(Long bookId, int count) {
        BookContentExample example = new BookContentExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(count);
        return bookContentMapper.selectByExample(example).get(0).getContent();
    }

    public int countIndex(Long bookId) {
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        return bookIndexMapper.countByExample(example);
    }


    public List<String> queryEndBookIdList() {
        return bookMapper.queryEndBookIdList();
    }





    public List<Integer> queryPreAndNextIndexNum(Long bookId, Integer indexNum) {
        List<Integer> result = new ArrayList<>();
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumGreaterThan(indexNum);
        example.setOrderByClause("index_num asc");
        List<BookIndex> bookIndices = bookIndexMapper.selectByExample(example);
        if (bookIndices.size() > 0) {
            result.add(bookIndices.get(0).getIndexNum());
        } else {
            result.add(indexNum);
        }
        example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumLessThan(indexNum);
        example.setOrderByClause("index_num DESC");
        bookIndices = bookIndexMapper.selectByExample(example);
        if (bookIndices.size() > 0) {
            result.add(bookIndices.get(0).getIndexNum());
        } else {
            result.add(indexNum);
        }
        return result;

    }

    /**
     * 查询推荐书籍数据
     * */
    public List<Book> queryRecBooks(List<Map<String, String>> configMap) {
        return bookMapper.queryRecBooks(configMap);
    }
}
