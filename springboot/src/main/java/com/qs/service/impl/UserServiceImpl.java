package com.qs.service.impl;

import com.qs.entity.User;
import com.qs.mapper.UserMapper;
import com.qs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/5/17.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUsers(User user) {
        return userMapper.queryUsers(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        userMapper.insertUser(user);
        //throw new RuntimeException("test transaction");
    }
}
