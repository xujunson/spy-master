package com.atu.common.utils;

import com.atu.context.TraceContext;
import com.atu.context.TraceContextHolder;
import org.slf4j.MDC;

/**
 * @author: Tom
 * @date: 2023/4/24 10:46
 * @description: TODO
 **/
public class TraceContextUtil {
    //设置调用线程的上下文到当前执行线程中,并返回执行线程之前的上下文
    public static Object backupAndSet(Object currentContext) {
        MDC.put("traceId", TraceContextHolder.getTraceId());
        TraceContext.setContext(MDC.getCopyOfContextMap());
        return TraceContext.getContext();
    }
    //恢复执行线程的上下文
    public static void restoreBackup(Object backup) {
        TraceContext.setContext(backup);
    }
}
