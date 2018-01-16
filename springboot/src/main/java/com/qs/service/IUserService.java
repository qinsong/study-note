package com.qs.service;

import com.qs.entity.User;

/**
 * Created by Administrator on 2017/5/17.
 */
public interface IUserService {
    User queryUsers(User user);

    void addUser(User user);
}
