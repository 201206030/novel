package xyz.zinglizingli.books.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.zinglizingli.books.mapper.UserMapper;
import xyz.zinglizingli.books.mapper.UserRefBookMapper;
import xyz.zinglizingli.books.po.User;
import xyz.zinglizingli.books.po.UserExample;
import xyz.zinglizingli.books.po.UserRefBook;
import xyz.zinglizingli.books.po.UserRefBookExample;
import xyz.zinglizingli.books.util.MD5Util;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRefBookMapper userRefBookMapper;


    public boolean isExistLoginName(String loginName) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(loginName);
        return userMapper.countByExample(example)>0?true:false;
    }

    public void regist(User user) {
        user.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
        userMapper.insertSelective(user);
    }

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

    public void addToCollect(Long bookId, long userId) {
        UserRefBook userRefBook = new UserRefBook();
        userRefBook.setBookId(bookId);
        userRefBook.setUserId(userId);
        UserRefBookExample example = new UserRefBookExample();
        example.createCriteria().andBookIdEqualTo(bookId).andUserIdEqualTo(userId);
        userRefBookMapper.deleteByExample(example);
        userRefBookMapper.insertSelective(userRefBook);

    }

    public boolean isCollect(Long bookId, long userId) {

        UserRefBookExample example = new UserRefBookExample();
        example.createCriteria().andBookIdEqualTo(bookId).andUserIdEqualTo(userId);
        return userRefBookMapper.countByExample(example)>0?true:false;

    }

    public void cancelToCollect(Long bookId, long userId) {
        UserRefBookExample example = new UserRefBookExample();
        example.createCriteria().andBookIdEqualTo(bookId).andUserIdEqualTo(userId);
        userRefBookMapper.deleteByExample(example);
    }

    public void collectOrCancelBook(Long userid, Long bookId) {

        boolean collect = isCollect(bookId, userid);

        if(collect){
            cancelToCollect(bookId, userid);;
        }else{
            addToCollect(bookId, userid);
        }
    }

    public Integer queryBookIndexNumber(String userId, Long bookId) {
        return userRefBookMapper.queryBookIndexNumber(userId,bookId);
    }
}
