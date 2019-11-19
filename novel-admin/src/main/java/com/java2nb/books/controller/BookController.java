package com.java2nb.books.controller;

import java.util.List;
import java.util.Map;

import com.java2nb.books.domain.BookContentDO;
import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.vo.BookIndexVO;
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


import com.java2nb.books.domain.BookDO;
import com.java2nb.books.service.BookService;
import com.java2nb.common.utils.PageBean;
import com.java2nb.common.utils.Query;
import com.java2nb.common.utils.R;

import javax.jws.WebParam;

/**
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:27:04
 */

@Controller
@RequestMapping("/books/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping()
    @RequiresPermissions("books:book:book")
    String Book() {
        return "books/book/book";
    }

    @ApiOperation(value = "获取列表", notes = "获取列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("books:book:book")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<BookDO> bookList = bookService.list(query);
        int total = bookService.count(query);
        PageBean pageBean = new PageBean(bookList, total);
        return R.ok().put("data", pageBean);
    }

    @ApiOperation(value = "新增页面", notes = "新增页面")
    @GetMapping("/add")
    String add() {
        return "books/book/add";
    }

    @ApiOperation(value = "修改页面", notes = "修改页面")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        BookDO book = bookService.get(id);
        model.addAttribute("book", book);
        return "books/book/edit";
    }

    @ApiOperation(value = "查看页面", notes = "查看页面")
    @GetMapping("/detail/{id}")
    String detail(@PathVariable("id") Long id, Model model) {
        BookDO book = bookService.get(id);
        model.addAttribute("book", book);
        return "books/book/detail";
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增", notes = "新增")
    @ResponseBody
    @PostMapping("/save")
    public R save(BookDO book) {
        if (bookService.save(book) > 0) {
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
    public R update(BookDO book) {
        bookService.update(book);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (bookService.remove(id) > 0) {
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
        bookService.batchRemove(ids);
        return R.ok();
    }


    @ApiOperation(value = "新增章节页面", notes = "新增章节页面")
    @GetMapping("/index/add")
    String indexAdd(Long bookId, Model model) {
        model.addAttribute("bookId",bookId);
        return "books/bookIndex/add";
    }

    /**
     * 保存章节
     */
    @ApiOperation(value = "新增章节", notes = "新增章节")
    @ResponseBody
    @PostMapping("/index/save")
    public R indexSave(BookIndexDO bookIndex, BookContentDO bookContent) {
        if (bookService.saveIndexAndContent(bookIndex,bookContent) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/index")
    String BookIndex() {
        return "books/bookIndex/bookIndex";
    }

    @ApiOperation(value = "获取章节列表", notes = "获取章节列表")
    @ResponseBody
    @GetMapping("/index/list")
    public R indexList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<BookIndexVO> bookIndexList = bookService.indexVOList(query);
        int total = bookService.indexVOCount(query);
        PageBean pageBean = new PageBean(bookIndexList, total);
        return R.ok().put("data", pageBean);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping("/index/remove")
    @ResponseBody
    public R indexRemove( Long id) {
        if (bookService.indexRemove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/index/batchRemove")
    @ResponseBody
    public R indexRemove(@RequestParam("ids[]") Long[] ids) {
        bookService.batchIndexRemove(ids);
        return R.ok();
    }
}
