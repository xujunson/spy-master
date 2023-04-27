package com.atu.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Tom
 * @create: 2023-04-25 13:41
 * @Description:
 */
public class ThreadPoolMonitor {
    private static final Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>();

    public Map<String, ThreadPoolExecutor> alls() {
        return threadPoolExecutorMap;
    }

    /**
     * 新增ThreadPoolExecutor
     *
     * @param key
     * @param executor
     */
    public void add(String key, ThreadPoolExecutor executor) {
        if (!threadPoolExecutorMap.containsKey(key)) {
            threadPoolExecutorMap.put(key, executor);
        }
    }

    public void remove(String key) {
        threadPoolExecutorMap.remove(key);
    }

    public ThreadPoolExecutor get(String key) {
        return threadPoolExecutorMap.get(key);
    }

}

