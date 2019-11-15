package com.java2nb.books.controller;

import java.util.*;

import com.java2nb.books.config.CrawlConfig;
import com.java2nb.common.utils.GenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiOperation;


import com.java2nb.books.domain.BookCrawlDO;
import com.java2nb.books.service.BookCrawlService;
import com.java2nb.common.utils.PageBean;
import com.java2nb.common.utils.Query;
import com.java2nb.common.utils.R;

/**
 * 
 *
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-15 03:42:54
 */

@Slf4j
@Controller
@RequestMapping("/books/bookCrawl")
public class BookCrawlController {
    @Autowired
    private BookCrawlService bookCrawlService;

    @Autowired
    private CrawlConfig crawlConfig;

    @GetMapping()
    @RequiresPermissions("books:bookCrawl:bookCrawl")
    String BookCrawl() {
        return "books/bookCrawl/bookCrawl";
    }

    @ApiOperation(value = "获取列表", notes = "获取列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("books:bookCrawl:bookCrawl")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<BookCrawlDO> bookCrawlList = bookCrawlService.list(query);
        int total = bookCrawlService.count(query);
        PageBean pageBean = new PageBean(bookCrawlList, total);
        return R.ok().put("data", pageBean);
    }

    @ApiOperation(value = "新增页面", notes = "新增页面")
    @GetMapping("/add")
    @RequiresPermissions("books:bookCrawl:add")
    String add() {
        return "books/bookCrawl/add";
    }

    @ApiOperation(value = "修改页面", notes = "修改页面")
    @GetMapping("/edit")
    String edit( Model model) throws Exception {
        model.addAttribute("property", crawlConfig);
        return "books/bookCrawl/edit";
    }

    @ApiOperation(value = "查看页面", notes = "查看页面")
    @GetMapping("/detail/{id}")
    @RequiresPermissions("books:bookCrawl:detail")
    String detail(@PathVariable("id") Long id, Model model) {
			BookCrawlDO bookCrawl = bookCrawlService.get(id);
        model.addAttribute("bookCrawl", bookCrawl);
        return "books/bookCrawl/detail";
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增", notes = "新增")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("books:bookCrawl:add")
    public R save( BookCrawlDO bookCrawl) {
        if (bookCrawlService.save(bookCrawl) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ResponseBody
    @RequestMapping("/update")
    public R update(CrawlConfig config) {
        crawlConfig = config;
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("books:bookCrawl:remove")
    public R remove( Long id) {
        if (bookCrawlService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("books:bookCrawl:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
            bookCrawlService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 修改爬虫状态
     */
    @ApiOperation(value = "修改爬虫状态", notes = "修改爬虫状态")
    @ResponseBody
    @RequestMapping("/updateStatus")
    public R updateStatus( BookCrawlDO bookCrawl) {
        bookCrawlService.updateStatus(bookCrawl);
        return R.ok();
    }
}
