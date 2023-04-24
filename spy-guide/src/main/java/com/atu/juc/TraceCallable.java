package com.atu.juc;

import com.atu.common.utils.TraceContextUtil;
import com.atu.context.TraceContext;

import java.util.concurrent.Callable;

/**
 * @author: Tom
 * @date: 2023/4/24 10:44
 * @description: TODO
 **/
public class TraceCallable<V> implements Callable<V> {
    //在初始化TraceCallable时会获取调用线程的上下文
    private final Object context = TraceContext.getContext();
    private final Callable<V> callable;

    public TraceCallable(Callable<V> callable) {
        this.callable = callable;
    }

    @Override
    public V call() throws Exception {
        Object backup = TraceContextUtil.backupAndSet(this.context);

        V result;
        try {
            result = this.callable.call();
        } finally {
            TraceContextUtil.restoreBackup(backup);
        }

        return result;
    }

    public Callable<V> getCallable() {
        return this.callable;
    }
    //返回TraceCallable实例
    public static <T> TraceCallable<T> get(Callable<T> callable) {
        if (callable == null) {
            return null;
        } else {
            return callable instanceof TraceCallable ? (TraceCallable)callable : new TraceCallable(callable);
        }
    }

}