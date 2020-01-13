package xyz.zinglizingli.books.core.utils;

/**
 * @author XXY
 */
public class Constants {

    /**
     * 服务异常路径
     * */
    public static final String SERVER_ERROR_PATH = "/mang.html";

    /**
     * 404跳转路径
     * */
    public static final String NOT_FOUND_PATH = "/";

    /**
     * 本地图片保存前缀
     * */
    public static final String LOCAL_PIC_PREFIX = "/localPic/";

    /**
     * 漫画内容访问前缀
     * */
    public static final String CARTOON_CONTENT_PREFIX = "/manhua/";

    /**
     * 漫画图片访问前缀
     * */
    public static final String CARTOON_PIC_PREFIX = "/manhua/images/";

    /**
     * 漫画图片访问前缀
     * */
    public static final String CARTOON_STATIC_PREFIX = "/manhua/statics/";

    /**
     * 爬取的动漫网站访问前缀
     * */
    public static final String CRAWL_CARTOON_URL_PREFIX = "https://www.dmzj.com/";

    /**
     * 爬取的动漫网站图片访问前缀
     * */
    public static final String CRAWL_CARTOON_PIC_URL_PREFIX = "https://images.dmzj.com/";

    /**
     * 爬取的动漫网站静态文件访问前缀
     * */
    public static final String CRAWL_CARTOON_STATIC_URL_PREFIX = "https://static.dmzj.com/";


    /**
     * 最大的小说分类ID
     * */
    public static final Integer MAX_NOVEL_CAT = 7;

    /**
     * 小说是否正在下载的key
     * */
    public static final String NOVEL_IS_DOWNLOADING_KEY = "isDownloading";

    /**
     * 轻小说分类ID
     * */
    public static final int SOFT_NOVEL_CAT = 8;

    /**
     * 漫画分类ID
     * */
    public static final int MH_NOVEL_CAT = 9;

    /**
     * 小说排行字段名
     * */
    public static final String NOVEL_TOP_FIELD = "score";

    /**
     * 完本小说标识名
     * */
    public static final String NOVEL_END_TAG = "完成";

    /**
     * 多本书籍ID分隔符
     * */
    public static final String BOOK_ID_SEPARATOR = "-";

    /**
     * 没有内容的描述
     * */
    public static final String NO_CONTENT_DESC = "正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新";

    /**
     * 书籍内容页的广告pattern
     * */
    public static final String CONTENT_AD_PATTERN = "<div[^>]+app\\.html[^>]+>\\s*<div[^>]+>\\s*<div[^>]+>[^<]+</div>\\s*<div[^>]+>[^<]+<span[^>]+>>>[^<]+<<</span>\\s*</div>\\s*</div>\\s*</div>";

    /**
     * 是否开启抓取新书
     * */
    public static final String ENABLE_NEW_BOOK = "true";

    /**
     * SEO配置保存的key
     * */
    public static final String SEO_CONFIG_KEY = "seoConfig";

    /**
     * 每次更新抓取的页数
     */
    public static final int UPDATE_PAGES_ONCE = 10;
}
