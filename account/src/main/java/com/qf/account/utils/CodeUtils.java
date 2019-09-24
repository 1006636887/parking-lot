package com.qf.account.utils;



import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.UUID;

/**
 * @author SongZun
 * @date 2019/9/20 16:12
 */
public class CodeUtils {
    public static String md5Hex(String data,String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    public static String generateSalt(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

}
