package xyz.zinglizingli.books.core.crawl;

import lombok.Data;

/**
 * html爬虫源
 * @author 11797
 */
@Data
public abstract class BaseHtmlCrawlSource extends BaseCrawlSource{

    /**
     * 首页url
     * */
    private String indexUrl;

    /**
     * 列表页url
     * */
    private String listPageUrl;

    /**
     * 书籍url Pattern
     * */
    private String bookUrlPattern;

    /**
     * 评分 Pattern
     * */
    private String scorePattern;

    /**
     * 书名 Pattern
     * */
    private String bookNamePattern;

    /**
     * 作者 Pattern
     * */
    private String authorPattern;

    /**
     * 状态 Pattern
     * */
    private String statusPattern;

    /**
     * 类别 Pattern
     * */
    private String catPattern;


    /**
     * 更新时间 Pattern
     * */
    private String updateTimePattern;


    /**
     * 封面 Pattern
     * */
    private String picPattern;


    /**
     * 简介 Pattern
     * */
    private String introPattern;

    /**
     * 完整目录页url Pattern
     * */
    private String catalogUrlPattern;

    /**
     * 目录 Pattern
     * */
    private String catalogPattern;


}
