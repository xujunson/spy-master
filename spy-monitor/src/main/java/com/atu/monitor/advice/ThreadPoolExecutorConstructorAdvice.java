package com.atu.monitor.advice;

import net.bytebuddy.asm.Advice;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Tom
 * @create: 2023-04-25 13:14
 * @Description: 针对构造函数进行增强
 */
public class ThreadPoolExecutorConstructorAdvice {
    @Advice.OnMethodExit
    public static void constructor(@Advice.This Object obj, @Advice.AllArguments Object[] args) {
        try {
            if (null != args && args.length == 7) {
                ThreadPoolExecutor executor = (ThreadPoolExecutor) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
