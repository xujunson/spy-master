package com.atu.context;

/**
 * @author: Tom
 * @date: 2023/4/24 10:44
 * @description: TODO
 **/
public class TraceContext {
    private static final ThreadLocal<Object> CONTEXT = new ThreadLocal<>();
    public static Object getContext() {
        return CONTEXT.get();
    }
    public static void setContext(Object obj) {
        CONTEXT.set(obj);
    }
    public static void removeContext() {
        CONTEXT.remove();
    }

}

