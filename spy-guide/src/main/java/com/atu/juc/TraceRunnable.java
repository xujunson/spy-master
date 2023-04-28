package com.atu.juc;

import com.atu.context.TraceContextHolder;
import org.slf4j.MDC;

/**
 * @author: Tom
 * @date: 2023/4/24 10:44
 * @description: TODO
 **/
public class TraceRunnable implements Runnable {
    private final Runnable runnable;

    public TraceRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
    @Override
    public void run() {
        MDC.put("traceId", TraceContextHolder.getTraceId());
        try {
            this.runnable.run();
        } finally {
            MDC.remove("traceId");
        }
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public static TraceRunnable get(Runnable runnable) {
        if (runnable == null) {
            return null;
        } else {
            return runnable instanceof TraceRunnable ? (TraceRunnable)runnable : new TraceRunnable(runnable);
        }
    }
}
