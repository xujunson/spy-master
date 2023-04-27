package com.atu.monitor.httpserver;

import com.atu.monitor.dto.ResultBean;
import com.atu.monitor.httpserver.collector.Collector;
import com.atu.monitor.utils.JVMGCInfo;
import com.atu.monitor.utils.JVMInfo;
import com.atu.monitor.utils.JVMMemoryInfo;
import com.atu.monitor.utils.JVMThreadInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Tom
 * @create: 2023-04-25 13:32
 * @Description:
 */
public class JVMCollector extends Collector {
    public JVMCollector() {
        this.methodName = "jvm";
    }

    @Override
    public ResultBean collect(Map<String, String> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("GC", JVMGCInfo.getInfo());
        map.put("JVM", JVMInfo.getInfo());
        map.put("memory", JVMMemoryInfo.getInfo());
        map.put("thread", JVMThreadInfo.getInfo());

        return new ResultBean.Builder().success(map).build();
    }
}
