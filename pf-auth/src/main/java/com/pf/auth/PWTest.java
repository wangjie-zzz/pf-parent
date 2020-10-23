package com.pf.auth;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.MD5;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @ClassName : PWTest
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/17-15:59
 */
public class PWTest {
    public static void main(String[] args) {
//        String md5Pw = BCrypt.hashpw("123456");
        String md5Pw = DigestUtils.md5DigestAsHex("123456".getBytes());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String enpw = bCryptPasswordEncoder.encode(md5Pw);

        System.out.println(md5Pw + "_______" + enpw);
//        boolean res = bCryptPasswordEncoder.matches(md5Pw,enpw);
        boolean res = bCryptPasswordEncoder.matches("e10adc3949ba59abbe56e057f20f883e",
                "$2a$10$QMQmna8zqlM6q5dcyKs5gegC7tiS6NpRrqgTFbSWJ5TEZRLd6G9bu");
        System.out.println(res);
//        e10adc3949ba59abbe56e057f20f883e

//        @SPLIT@
//        $2a$10$UfZN2/8sFs2P6jv71mGjkumAX5/C4LKMTy/l3Oph4AkbUtQvnqEfO
//$2a$10$5Jj5Sa4J56llDhTT/ZlfPOPQiTN1XYT8r7nq6q3.wtb6GSmbH8vxi

    }
}
