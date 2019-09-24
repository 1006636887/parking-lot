package com.qf.account.mapper;

import com.qf.account.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
*@author SongZun
*@date 2019/9/20 19:20
*/
@Mapper
public interface UserMapper {
    int insert(@Param("user") User user);
    User login(@Param("userName") String userName, @Param("userPassword") String userPassword);
    User loginCheck(@Param("userName") String userName);
}