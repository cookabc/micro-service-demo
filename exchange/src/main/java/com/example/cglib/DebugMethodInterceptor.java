package com.example.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * DebugMethodInterceptor
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class DebugMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        System.out.println("before method " + method.getName());

        Object object = methodProxy.invokeSuper(o, args);

        System.out.println("after method " + method.getName());

        return object;
    }
}
