package com.qf.account.service.impl;

import com.qf.account.domain.entity.User;
import com.qf.account.mapper.UserMapper;
import com.qf.account.service.UserService;
import com.qf.account.utils.CodeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author SongZun
 * @date 2019/9/20 19:24
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public int insert(User user) {

        return userMapper.insert(user);
    }

    @Override
    public User login(String userName, String userPassword) {

        User check = userMapper.loginCheck(userName);
        if (check==null){
            return null;
        }
        String salt = check.getSalt();
        String pass = CodeUtils.md5Hex(userPassword, salt);
        if (!check.getUserPassword().equals(pass)){
            return null;
        }

        User login = userMapper.login(userName, pass);
        return login;
    }
}
