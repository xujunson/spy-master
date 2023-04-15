package com.atu.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author: Tom
 * @date: 2023/4/15 17:51
 * @description: TODO
 **/
public class TraceContextHolder {

    private static final TransmittableThreadLocal<String> traceIdTL = new TransmittableThreadLocal<>();

    private static final TransmittableThreadLocal<String> preIvkAppTL = new TransmittableThreadLocal<>();

    private static final TransmittableThreadLocal<String> preIvkHostTL = new TransmittableThreadLocal<>();

    private static final TransmittableThreadLocal<String> preIpTL = new TransmittableThreadLocal<>();

    private static final TransmittableThreadLocal<String> currIpTL = new TransmittableThreadLocal<>();

    public static void putTraceId(String traceId) {
        traceIdTL.set(traceId);
    }

    public static String getTraceId() {
        return traceIdTL.get();
    }

    public static void removeTraceId() {
        traceIdTL.remove();
    }

    public static void putSpanId(String spanId) {
        SpanIdGenerator.putSpanId(spanId);
    }

    public static String getSpanId() {
        return SpanIdGenerator.getSpanId();
    }

    public static void removeSpanId() {
        SpanIdGenerator.removeSpanId();
    }

    public static String getPreIvkApp() {
        return preIvkAppTL.get();
    }

    public static void putPreIvkApp(String preIvkApp) {
        preIvkAppTL.set(preIvkApp);
    }

    public static void removePreIvkApp() {
        preIvkAppTL.remove();
    }

    public static String getPreIvkHost() {
        return preIvkHostTL.get();
    }

    public static void putPreIvkHost(String preIvkHost) {
        preIvkHostTL.set(preIvkHost);
    }

    public static void removePreIvkHost() {
        preIvkHostTL.remove();
    }

    public static String getPreIp() {
        return preIpTL.get();
    }

    public static void putPreIp(String preIp) {
        preIpTL.set(preIp);
    }

    public static void removePreIp() {
        preIpTL.remove();
    }

    public static String getCurrIp() {
        return currIpTL.get();
    }

    public static void putCurrIp(String currIp) {
        currIpTL.set(currIp);
    }

    public static void removeCurrIp() {
        currIpTL.remove();
    }
}
