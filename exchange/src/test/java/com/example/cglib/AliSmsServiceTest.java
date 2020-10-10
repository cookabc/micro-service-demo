package com.example.cglib;

import org.junit.jupiter.api.Test;

/**
 * AliSmsServiceTest
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class AliSmsServiceTest {

    @Test
    void test() {
        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
    }
}
