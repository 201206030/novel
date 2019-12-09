package xyz.zinglizingli.books.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.books.mapper.UserMapper;
import xyz.zinglizingli.books.mapper.UserRefBookMapper;
import xyz.zinglizingli.books.po.User;
import xyz.zinglizingli.books.po.UserExample;
import xyz.zinglizingli.books.po.UserRefBook;
import xyz.zinglizingli.books.po.UserRefBookExample;
import xyz.zinglizingli.books.util.MD5Util;

import java.util.List;

/**
 * @author XXY
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserRefBookMapper userRefBookMapper;


    /**
     * 判断登录名是否存在
     * */
    public boolean isExistLoginName(String loginName) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(loginName);
        return userMapper.countByExample(example)>0;
    }

    /**
     * 注册
     * */
    public void regist(User user) {
        user.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
        userMapper.insertSelective(user);
    }

    /**
     * 登陆
     * */
    public void login(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(user.getLoginName())
        .andPasswordEqualTo(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
        List<User> users = userMapper.selectByExample(example);
        if(users.size() > 0){
            user.setId(users.get(0).getId());
        }else {
            user.setId(null);
        }


    }

    /**
     * 加入书架
     * */
    public void addToCollect(Long bookId, long userId) {
        UserRefBook userRefBook = new UserRefBook();
        userRefBook.setBookId(bookId);
        userRefBook.setUserId(userId);
        UserRefBookExample example = new UserRefBookExample();
        example.createCriteria().andBookIdEqualTo(bookId).andUserIdEqualTo(userId);
        userRefBookMapper.deleteByExample(example);
        userRefBookMapper.insertSelective(userRefBook);

    }

    /**
     * 判断是否加入书架
     * */
    public boolean isCollect(Long bookId, long userId) {

        UserRefBookExample example = new UserRefBookExample();
        example.createCriteria().andBookIdEqualTo(bookId).andUserIdEqualTo(userId);
        return userRefBookMapper.countByExample(example)>0;

    }

    /**
     * 取消加入书架
     * */
    public void cancelToCollect(Long bookId, long userId) {
        UserRefBookExample example = new UserRefBookExample();
        example.createCriteria().andBookIdEqualTo(bookId).andUserIdEqualTo(userId);
        userRefBookMapper.deleteByExample(example);
    }

    /**
     * 加入或取消书架
     * */
    public void collectOrCancelBook(Long userid, Long bookId) {

        boolean collect = isCollect(bookId, userid);

        if(collect){
            cancelToCollect(bookId, userid);;
        }else{
            addToCollect(bookId, userid);
        }
    }

    /**
     * 查询用户章节阅读记录
     * */
    public Integer queryBookIndexNumber(String userId, Long bookId) {
        return userRefBookMapper.queryBookIndexNumber(userId,bookId);
    }
}
