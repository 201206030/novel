package com.java2nb.books.controller;

import java.util.List;
import java.util.Map;

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


import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.service.BookIndexService;
import com.java2nb.common.utils.PageBean;
import com.java2nb.common.utils.Query;
import com.java2nb.common.utils.R;

/**
 * 
 *
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:28:15
 */

@Controller
@RequestMapping("/books/bookIndex")
public class BookIndexController {
    @Autowired
    private BookIndexService bookIndexService;

    @GetMapping()
    @RequiresPermissions("books:bookIndex:bookIndex")
    String BookIndex() {
        return "books/bookIndex/bookIndex";
    }

    @ApiOperation(value = "获取列表", notes = "获取列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("books:bookIndex:bookIndex")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<BookIndexDO> bookIndexList = bookIndexService.list(query);
        int total = bookIndexService.count(query);
        PageBean pageBean = new PageBean(bookIndexList, total);
        return R.ok().put("data", pageBean);
    }

    @ApiOperation(value = "新增页面", notes = "新增页面")
    @GetMapping("/add")
    String add() {
        return "books/bookIndex/add";
    }

    @ApiOperation(value = "修改页面", notes = "修改页面")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
            BookIndexDO bookIndex = bookIndexService.get(id);
        model.addAttribute("bookIndex", bookIndex);
        return "books/bookIndex/edit";
    }

    @ApiOperation(value = "查看页面", notes = "查看页面")
    @GetMapping("/detail/{id}")
    String detail(@PathVariable("id") Long id, Model model) {
			BookIndexDO bookIndex = bookIndexService.get(id);
        model.addAttribute("bookIndex", bookIndex);
        return "books/bookIndex/detail";
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增", notes = "新增")
    @ResponseBody
    @PostMapping("/save")
    public R save( BookIndexDO bookIndex) {
        if (bookIndexService.save(bookIndex) > 0) {
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
    public R update( BookIndexDO bookIndex) {
            bookIndexService.update(bookIndex);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping("/remove")
    @ResponseBody
    public R remove( Long id) {
        if (bookIndexService.remove(id) > 0) {
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
    public R remove(@RequestParam("ids[]") Long[] ids) {
            bookIndexService.batchRemove(ids);
        return R.ok();
    }

}
