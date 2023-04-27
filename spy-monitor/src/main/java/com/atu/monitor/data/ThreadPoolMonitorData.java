package com.atu.monitor.data;

import com.atu.monitor.factory.NamedThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Tom
 * @create: 2023-04-25 13:16
 * @Description: 线程池监控data
 */
public class ThreadPoolMonitorData {
    final static String KEY = "key";
    final static String DESC = "desc";
    private static Map<String, ThreadPoolExecutorWrapper> threadPoolExecutorWrapperMap = new HashMap<>();

    public static Map<String, ThreadPoolExecutorWrapper> alls() {
        return threadPoolExecutorWrapperMap;
    }

    /**
     * 对外暴露添加接口
     *
     * @param executor
     */
    public void add(ThreadPoolExecutor executor) {
        final String[] info = getInfo(executor);
        ThreadPoolExecutorWrapper data = new ThreadPoolExecutorWrapper(info[0], info[1], executor);
        put(info[0], data);
    }

    private static String[] getInfo(ThreadPoolExecutor executor) {
        final ThreadFactory threadFactory = executor.getThreadFactory();
        String key = executor.getClass().getName() + "@" + executor.hashCode();
        String desc = "未使用提供的NamedThreadFactory";
        if (threadFactory instanceof NamedThreadFactory) {
            NamedThreadFactory namedThreadFactory = (NamedThreadFactory) threadFactory;
            key = namedThreadFactory.getName();
            desc = namedThreadFactory.getDesc();
        }
        return new String[]{key, desc};
    }

    /**
     * 按照key 加锁
     * 按实际情况，线程池是稀缺资源，一个项目里不会有特别多
     * 只有放入了字符串常量池，字符串的对象才会唯一
     *
     * @param name   线程池的名字
     * @param wrapper 线程池包装类
     */
    private void put(String name, ThreadPoolExecutorWrapper wrapper) {
        synchronized (name.intern()) {
            if (!threadPoolExecutorWrapperMap.containsKey(name)) {
                threadPoolExecutorWrapperMap.put(name, wrapper);
            }
        }
    }

    /**
     * 对外暴露remove接口
     *
     * @param executor
     */
    public static void remove(ThreadPoolExecutor executor) {
        final String[] info = getInfo(executor);
        if (threadPoolExecutorWrapperMap.containsKey(info[0])) {
            threadPoolExecutorWrapperMap.remove(info[1]);
        }
    }
}
