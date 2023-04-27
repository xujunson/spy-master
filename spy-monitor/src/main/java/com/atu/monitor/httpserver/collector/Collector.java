package com.atu.monitor.httpserver.collector;

import com.atu.monitor.dto.ResultBean;

import java.util.Map;

/**
 * @author: Tom
 * @create: 2023-04-25 13:25
 * @Description: 自定义收集器模板
 */
public abstract class Collector {
    /**
     * 方法名称,也是接口名称
     */
    protected String methodName;
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * 每个收集器去实现自己的收集功能
     * @return
     */
    public abstract ResultBean collect(Map<String,String> params);
    /**
     * Register the Collector with the default registry.
     */
    public <T extends Collector> T register() {
        return register(CollectorRegistry.defaultRegistry);
    }

    /**
     * Register the Collector with the given registry.
     */
    public <T extends Collector> T register(CollectorRegistry registry) {
        registry.register(this);
        return (T)this;
    }

}

