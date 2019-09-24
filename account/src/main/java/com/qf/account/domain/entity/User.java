package com.qf.account.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
*@author SongZun
*@date 2019/9/20 19:20
*/
@Data
public class User {
    /**
    * 用户id
    */
    private Integer uid;

    /**
    * 用户名
    */
    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    private String userName;

    /**
    * 密码
    */
    @JsonIgnore
    @Length(min = 6, max = 30, message = "密码只能在4~30位之间")
    private String userPassword;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 手机号
    */
    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String userPhone;

    /**
    * 是否删除  默认为0 1为删除
    */
    private Integer isDelete;

    private String salt;
}