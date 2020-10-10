package com.example.proxy;

import org.junit.jupiter.api.Test;

/**
 * SmsServiceTest
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class SmsServiceTest {

    @Test
    void test() {
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("java");
    }
}
