package com.qf.account.service;

/**
 * @author SongZun
 * @date 2019/9/19 21:03
 */

public interface SmsService {
    boolean sendSms(String phone,String signName,String templateCode,String templateParam);
}
