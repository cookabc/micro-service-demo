package com.example.proxy;

/**
 * SmsServiceImpl
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class SmsServiceImpl implements SmsService {

    @Override
    public void send(String message) {
        System.out.println("send message: " + message);
    }
}
