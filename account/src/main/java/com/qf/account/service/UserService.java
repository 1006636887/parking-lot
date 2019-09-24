package com.qf.account.service;

import com.qf.account.domain.entity.User;

/**
 * @author SongZun
 * @date 2019/9/20 19:23
 */
public interface UserService {
    int insert(User user);
    User login(String userName,String userPassword);
}
