package com.atu.monitor.httpserver;

import com.atu.monitor.data.ThreadPoolExecutorWrapper;
import com.atu.monitor.data.ThreadPoolMonitorData;
import com.atu.monitor.dto.ResultBean;
import com.atu.monitor.dto.ThreadPoolVo;
import com.atu.monitor.httpserver.collector.Collector;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Tom
 * @create: 2023-04-25 13:33
 * @Description:
 */
public class ThreadPoolCollector extends Collector {
    public ThreadPoolCollector() {
        this.methodName = "threadPool";
    }

    @Override
    public ResultBean collect(Map<String, String> params) {
        Map<String, ThreadPoolExecutorWrapper> alls = ThreadPoolMonitorData.alls();
        String key = params.get("key");
        ThreadPoolExecutorWrapper executor = alls.get(key);
        if (null != executor) {
            ThreadPoolVo vo = new ThreadPoolVo.Builder()
                    .name(executor.getName())
                    .desc(executor.getDesc())
                    .threadPoolExecutor(executor.getExecutor())
                    .build();
            return new ResultBean.Builder().success(vo).build();
        }
        Map<String, ThreadPoolVo> map = new HashMap<>();
        alls.forEach((k, v) -> {
            ThreadPoolVo threadPoolVo = new ThreadPoolVo.Builder()
                    .name(v.getName())
                    .desc(v.getDesc())
                    .threadPoolExecutor(v.getExecutor())
                    .build();
            map.put(k, threadPoolVo);
        });
        return new ResultBean.Builder().success(map).build();
    }
}

