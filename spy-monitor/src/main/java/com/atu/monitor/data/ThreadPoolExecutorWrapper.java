package com.atu.monitor.data;

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Tom
 * @create: 2023-04-25 13:17
 * @Description:
 */
public class ThreadPoolExecutorWrapper implements Serializable {

    private String name;
    private String desc;
    private ThreadPoolExecutor executor;

    public ThreadPoolExecutorWrapper(String name, String desc, ThreadPoolExecutor executor) {
        this.name = name;
        this.desc = desc;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }
}
