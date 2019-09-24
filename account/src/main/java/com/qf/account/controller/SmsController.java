package com.qf.account.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.qf.account.domain.entity.User;
import com.qf.account.service.SmsService;
import com.qf.account.service.impl.UserServiceImpl;
import com.qf.account.utils.CodeUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author SongZun
 * @date 2019/9/19 21:12
 */
@RestController
@Slf4j
public class SmsController {
    @Value("${sms.signName}")
    private String signName ;
    @Value("${sms.templateCode}")
    private String templateCode;


    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    UserServiceImpl userService;

    @GetMapping(value = "/sendSms",params = {"phone"})
    @ApiOperation(value = "发短信",notes = "根据手机号发短信")
    @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",required = true)
    public Map<String, Object> sendSms(String phone){

        String code = UUID.randomUUID().toString()
                .replaceAll("-", "")
                .replaceAll("[a-z|A-Z]","")
                .substring(0, 6);
        redisTemplate.boundValueOps(phone).set(code,5, TimeUnit.MINUTES);
        String templateParam = "{\"code\":\"" + code + "\"}";

        // 发送短信
        boolean success = smsService.sendSms(phone,
                signName,templateCode, templateParam);
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        return map;
    }

    @PostMapping("/regist")
    public boolean register(@RequestBody @Valid User user,
                            @Valid String phone,
                            String code){
        String checkCode = (String) redisTemplate.boundValueOps(phone).get();
        if (!code.equals(checkCode)){
            return false;
        }

        String salt = CodeUtils.generateSalt();
        user.setUserPassword(CodeUtils.md5Hex(user.getUserPassword(),salt));
        user.setSalt(salt);
        user.setUserPhone(phone);
        boolean i = userService.insert(user) == 1;

        if (i){
            try {
                redisTemplate.delete(phone);
            } catch (Exception e) {
                log.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return i;
    }


    @GetMapping(value = "/login")
    public boolean login(@RequestParam(name = "userName") String userName,
                         @RequestParam(name = "userPassword") String userPassword){
        User login = userService.login(userName, userPassword);
        if (login!=null){
            return true;
        }
        return false;
    }
}
