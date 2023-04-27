package com.atu.monitor.httpserver;

import com.atu.monitor.dto.ResultBean;
import com.atu.monitor.httpserver.collector.Collector;

import java.util.Map;

/**
 * @author: Tom
 * @create: 2023-04-25 13:32
 * @Description:
 */
public class StackCollector extends Collector {

    public StackCollector() {
        this.methodName = "stack";
    }

    @Override
    public ResultBean collect(Map<String, String> params) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return new ResultBean.Builder().success(elements).build();
    }
}