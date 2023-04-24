package com.atu.aop;

import com.alibaba.ttl.threadpool.agent.internal.javassist.CannotCompileException;
import com.alibaba.ttl.threadpool.agent.internal.javassist.CtClass;
import com.alibaba.ttl.threadpool.agent.internal.javassist.NotFoundException;

import java.io.IOException;

/**
 * @author: Tom
 * @date: 2023/4/24 10:41
 * @description: TODO
 **/
public interface TraceTransformer {
    boolean needTransform(String className);

    void doTransform(CtClass var1) throws NotFoundException, CannotCompileException, IOException;
}
