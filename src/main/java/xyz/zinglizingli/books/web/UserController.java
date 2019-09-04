package xyz.zinglizingli.books.web;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.zinglizingli.books.po.Book;
import xyz.zinglizingli.books.po.BookContent;
import xyz.zinglizingli.books.po.BookIndex;
import xyz.zinglizingli.books.po.User;
import xyz.zinglizingli.books.service.BookService;
import xyz.zinglizingli.books.service.UserService;
import xyz.zinglizingli.books.util.UUIDUtils;
import xyz.zinglizingli.books.vo.BookVO;
import xyz.zinglizingli.search.cache.CommonCacheUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommonCacheUtil commonCacheUtil;


    @RequestMapping("login.html")
    public String login(Long bookId, ModelMap modelMap) {
        modelMap.put("bookId", bookId);
        return "user/login";
    }


    @RequestMapping("loginOrRegist")
    @ResponseBody
    public Map<String, Object> loginOrRegist(User user,Long bookId) {
        Map<String, Object> result = new HashMap<>();
        //查询用户名是否存在
        boolean isExistLoginName = userService.isExistLoginName(user.getLoginName());
        String token = null;
        if (isExistLoginName) {
            //登录
            userService.login(user);
            if (user.getId() != null) {
                token = UUIDUtils.getUUID32();
                commonCacheUtil.set(token, user.getId() + "");
                result.put("code", 1);
                result.put("desc", "登录成功！");
                if(!StringUtils.isEmpty(bookId)) {
                    userService.collectOrCancelBook(user.getId(), bookId);
                }
            } else {
                result.put("code", -1);
                result.put("desc", "用户名或密码错误！");
            }
        } else {
            //注册
            userService.regist(user);
            Long userId = user.getId();
            token = UUIDUtils.getUUID32();
            commonCacheUtil.set(token, userId + "");
            result.put("code", 2);
            result.put("desc", "注册成功！");
            if(!StringUtils.isEmpty(bookId)) {
                userService.collectOrCancelBook(user.getId(), bookId);
            }
        }
        if(token != null){
            result.put("token",token);
        }
        return result;
    }

    @RequestMapping("isLogin")
    @ResponseBody
    public Map<String, Object> isLogin(String token) {
        Map<String, Object> result = new HashMap<>();
        String userId = commonCacheUtil.get(token);
        if(StringUtils.isEmpty(userId)){
            result.put("code", -1);
            result.put("desc", "未登录！");
        }else{
            result.put("code", 1);
            result.put("desc", "已登录！");
        }
        return result;
    }


    @RequestMapping("addToCollect")
    @ResponseBody
    public Map<String, Object> addToCollect(Long bookId,String token) {
        Map<String, Object> result = new HashMap<>();
        String userId = commonCacheUtil.get(token);
        if(StringUtils.isEmpty(userId)){
            result.put("code", -1);
            result.put("desc", "未登录！");
        }else {
            userService.addToCollect(bookId,Long.parseLong(userId));
            result.put("code", 1);
            result.put("desc", "加入成功，请前往我的书架查看！");
        }
        return result;
    }

    @RequestMapping("cancelToCollect")
    @ResponseBody
    public Map<String, Object> cancelToCollect(Long bookId,String token) {
        Map<String, Object> result = new HashMap<>();
        String userId = commonCacheUtil.get(token);
        if(StringUtils.isEmpty(userId)){
            result.put("code", -1);
            result.put("desc", "未登录！");
        }else {
            userService.cancelToCollect(bookId,Long.parseLong(userId));
            result.put("code", 1);
            result.put("desc", "撤下成功！");
        }
        return result;
    }

    @RequestMapping("isCollect")
    @ResponseBody
    public Map<String, Object> isCollect(Long bookId,String token) {
        Map<String, Object> result = new HashMap<>();
        String userId = commonCacheUtil.get(token);
        if(StringUtils.isEmpty(userId)){
            result.put("code", -1);
            result.put("desc", "未登录！");
        }else {
            boolean isCollect = userService.isCollect(bookId,Long.parseLong(userId));
            if(isCollect) {
                result.put("code", 1);
                result.put("desc", "已收藏！");
            }else{
                result.put("code", 2);
                result.put("desc", "未收藏！");
            }
        }
        return result;
    }




}
