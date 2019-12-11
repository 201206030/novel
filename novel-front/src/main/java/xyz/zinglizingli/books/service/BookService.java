package xyz.zinglizingli.books.service;

import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;
import xyz.zinglizingli.common.constant.CacheKeyConstans;
import xyz.zinglizingli.common.enums.PicSaveType;
import xyz.zinglizingli.books.mapper.*;
import xyz.zinglizingli.books.po.*;
import xyz.zinglizingli.common.utils.UUIDUtils;
import xyz.zinglizingli.common.cache.CommonCacheUtil;
import xyz.zinglizingli.common.utils.RestTemplateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XXY
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookMapper bookMapper;

    private final BookIndexMapper bookIndexMapper;

    private final BookContentMapper bookContentMapper;

    private final ScreenBulletMapper screenBulletMapper;

    private final UserRefBookMapper userRefBookMapper;

    private final CommonCacheUtil cacheUtil;

    @Value("${pic.save.type}")
    private Integer picSaveType;

    @Value("${pic.save.path}")
    private String picSavePath;



    /**
     * 保存章节目录和内容
     * */
    public void saveBookAndIndexAndContent(Book book, List<BookIndex> bookIndex, List<BookContent> bookContent){

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
            updateBook(book, bookId);
            isUpdate = true;

        } else {
            //插入
            if (book.getVisitCount() == null) {
                Long visitCount = generateVisitCount(book.getScore());
                book.setVisitCount(visitCount);
            }
            int rows = bookMapper.insertSelective(book);
            if (rows > 0) {
                bookId = book.getId();
            }


        }

        if (bookId >= 0) {

            List<BookIndex> newBookIndexList = new ArrayList<>();
            List<BookContent> newContentList = new ArrayList<>();
            for (int i = 0; i < bookIndex.size(); i++) {
                BookContent bookContentItem = bookContent.get(i);
                if (!bookContentItem.getContent().contains("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新")) {
                    BookIndex bookIndexItem = bookIndex.get(i);
                    bookIndexItem.setBookId(bookId);
                    bookContentItem.setBookId(bookId);
                    bookContentItem.setIndexNum(bookIndexItem.getIndexNum());
                    newBookIndexList.add(bookIndexItem);
                    newContentList.add(bookContentItem);
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

    /**
     * 更新书籍
     * */
    private void updateBook(Book book, Long bookId) {
        book.setId(bookId);
        String picSrc = book.getPicUrl();
        if(picSaveType == PicSaveType.LOCAL.getValue() && StringUtils.isNotBlank(picSrc)){
            try {
                //本地图片保存
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
                ResponseEntity<Resource> resEntity = RestTemplateUtil.getInstance(Charsets.ISO_8859_1).exchange(picSrc, HttpMethod.GET, requestEntity, Resource.class);
                InputStream input = Objects.requireNonNull(resEntity.getBody()).getInputStream();
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
    }

    /**
     * 批量插入章节目录表和章节内容表
     * */
    @Transactional(rollbackFor = Exception.class)
    public void insertIndexListAndContentList(List<BookIndex> newBookIndexList, List<BookContent> newContentList) {
        bookIndexMapper.insertBatch(newBookIndexList);
        bookContentMapper.insertBatch(newContentList);
    }


    /**
     * 生成随机访问次数
     * */
    private Long generateVisitCount(Float score) {
        int baseNum = (int)(score * 100);
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

        return bookMapper.search(userId, ids, keyword, catId, softCat, softTag, bookStatus);

    }

    /**
     * 获取分类名
     * */
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

    /**
     * 查询书籍的基础数据
     * */
    public Book queryBaseInfo(Long bookId) {

        return bookMapper.selectByPrimaryKey(bookId);
    }

    /**
     * 查询最新更新的书籍列表
     * */
    public List<BookIndex> queryNewIndexList(Long bookId) {
        PageHelper.startPage(1, 15);
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        example.setOrderByClause("index_num DESC");
        return bookIndexMapper.selectByExample(example);

    }

    /**
     * 查询书籍目录列表
     * */
    public List<BookIndex> queryAllIndexList(Long bookId) {
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        example.setOrderByClause("index_num ASC");
        return bookIndexMapper.selectByExample(example);
    }

    /**
     * 查询书籍章节内容
     * */
    public BookContent queryBookContent(Long bookId, Integer indexNum) {
        BookContent content = (BookContent) cacheUtil.getObject(CacheKeyConstans.BOOK_CONTENT_KEY_PREFIX + "_" + bookId + "_" + indexNum);
        if (content == null) {
            BookContentExample example = new BookContentExample();
            example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(indexNum);
            List<BookContent> bookContents = bookContentMapper.selectByExample(example);
            content = bookContents.size() > 0 ? bookContents.get(0) : null;
            cacheUtil.setObject(CacheKeyConstans.BOOK_CONTENT_KEY_PREFIX + "_" + bookId + "_" + indexNum, content, 60 * 60 * 24);
        }

        return content;
    }


    /**
     * 增加访问次数
     * */
    public void addVisitCount(Long bookId, String userId, Integer indexNum) {

        bookMapper.addVisitCount(bookId);

        if(org.apache.commons.lang3.StringUtils.isNotBlank(userId)) {
            userRefBookMapper.updateNewstIndex(bookId, userId, indexNum);
        }

    }

    /**
     * 查询章节名
     * */
    public String queryIndexNameByBookIdAndIndexNum(Long bookId, Integer indexNum) {

        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(indexNum);
        List<BookIndex> indexList = bookIndexMapper.selectByExample(example);
        if(indexList != null && indexList.size() > 0 ) {
            return indexList.get(0).getIndexName();
        }
        return null;
    }

    /**
     * 查询最大和最小章节号
     * */
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
     * 查询该书籍已存在目录号
     */
    public List<Integer> queryIndexNumByBookNameAndAuthor(String bookName, String author) {
        BookExample example = new BookExample();
        example.createCriteria().andBookNameEqualTo(bookName).andAuthorEqualTo(author);
        List<Book> books = bookMapper.selectByExample(example);
        if (books.size() > 0) {

            Long bookId = books.get(0).getId();
            BookIndexExample bookIndexExample = new BookIndexExample();
            bookIndexExample.createCriteria().andBookIdEqualTo(bookId);
            List<BookIndex> bookIndices = bookIndexMapper.selectByExample(bookIndexExample);
            if(bookIndices.size()>0) {
                return bookIndices.stream().map(BookIndex::getIndexNum).collect(Collectors.toList());
            }

        }

        return new ArrayList<>(0);

    }



    /**
     * 查询轻小说分类名
     * */
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

    /**
     * 查询漫画分类名
     * */
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

    /**
     * 保存弹幕
     * */
    public void sendBullet(Long contentId, String bullet) {

        ScreenBullet screenBullet = new ScreenBullet();
        screenBullet.setContentId(contentId);
        screenBullet.setScreenBullet(bullet);
        screenBullet.setCreateTime(new Date());

        screenBulletMapper.insertSelective(screenBullet);
    }

    /**
     * 查询弹幕
     * */
    public List<ScreenBullet> queryBullet(Long contentId) {

        ScreenBulletExample example = new ScreenBulletExample();
        example.createCriteria().andContentIdEqualTo(contentId);
        example.setOrderByClause("create_time asc");

        return screenBulletMapper.selectByExample(example);
    }


    /**
     * 查询章节内容
     * */
    public String queryContentList(Long bookId, int count) {
        BookContentExample example = new BookContentExample();
        example.createCriteria().andBookIdEqualTo(bookId).andIndexNumEqualTo(count);
        return bookContentMapper.selectByExample(example).get(0).getContent();
    }

    /**
     * 查询章节数
     * */
    public int countIndex(Long bookId) {
        BookIndexExample example = new BookIndexExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        return bookIndexMapper.countByExample(example);
    }




    /**
     * 查询前一章节和后一章节号
     * */
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

    /**
     * 清理数据库中无效数据
     * */
    public void clearInvilidData() {

        //清除无效内容
        bookContentMapper.clearInvilidContent();

        //清除无效章节
        bookIndexMapper.clearInvilidIndex();

        //清楚无效书籍
        bookMapper.clearInvilidBook();
    }
}
