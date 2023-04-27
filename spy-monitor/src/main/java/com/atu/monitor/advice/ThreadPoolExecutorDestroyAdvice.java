package com.atu.monitor.advice;

import com.atu.monitor.data.ThreadPoolMonitorData;
import net.bytebuddy.asm.Advice;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Tom
 * @create: 2023-04-25 13:15
 * @Description: 线程池销毁增强
 */
public class ThreadPoolExecutorDestroyAdvice {
    @Advice.OnMethodEnter
    public static void finalize(@Advice.This Object obj) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) obj;
        ThreadPoolMonitorData.remove(executor);
    }
}
