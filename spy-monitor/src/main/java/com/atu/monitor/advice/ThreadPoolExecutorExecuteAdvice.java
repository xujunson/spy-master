package com.atu.monitor.advice;

import com.atu.monitor.data.ThreadPoolMonitorData;
import net.bytebuddy.asm.Advice;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Tom
 * @create: 2023-04-25 13:44
 * @Description:
 */
public class ThreadPoolExecutorExecuteAdvice {
    /**
     * 对所有的线程的execute 进入方法进行增强
     * byteBuddy不支持对constructor
     *
     * @param obj
     * @param abc
     * @Advice.OnMethodEnter 必须作用与static方法
     */
    @Advice.OnMethodEnter
    public static void executeBefore(@Advice.This Object obj, @Advice.Argument(0) Object abc) {
        try {
            //以下代码不能抽取，一旦抽取，必须用bootstrap加载器加载
            ThreadPoolExecutor executor = (ThreadPoolExecutor) obj;
            ThreadPoolMonitorData data = new ThreadPoolMonitorData();
            data.add(executor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
