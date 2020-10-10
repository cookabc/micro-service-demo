package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * DebugInvocationHandler
 *
 * @author: Xugang Song
 * @create: 2020/10/10
 */
public class DebugInvocationHandler implements InvocationHandler {

    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

        System.out.println("before method " + method.getName());

        Object result = method.invoke(target, args);

        System.out.println("after method " + method.getName());

        return result;
    }
}
