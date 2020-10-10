package com.example.proxy;

import java.lang.reflect.Proxy;

/**
 * JdkProxyFactory
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class JdkProxyFactory {

    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DebugInvocationHandler(target)
        );
    }
}
