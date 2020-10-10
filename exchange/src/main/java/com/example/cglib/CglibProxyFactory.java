package com.example.cglib;

import org.springframework.cglib.proxy.Enhancer;

/**
 * CglibProxyFactory
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class CglibProxyFactory {

    public static Object getProxy(Class<?> clazz) {

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new DebugMethodInterceptor());

        return enhancer.create();
    }
}
