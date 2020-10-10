package com.example.proxy;

/**
 * SmsService
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public interface SmsService {

    /**
     * Send message
     *
     * @param message message body
     */
    void send(String message);
}
