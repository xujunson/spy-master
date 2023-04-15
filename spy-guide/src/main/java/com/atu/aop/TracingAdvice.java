package com.atu.aop;

import com.atu.common.LogWebCommon;
import net.bytebuddy.asm.Advice;

/**
 * @author: Tom
 * @date: 2023/4/15 17:28
 * @description: TODO
 **/

public class TracingAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        LogWebCommon.loadInstance().preHandle();
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        LogWebCommon.loadInstance().afterCompletion();
    }
}

