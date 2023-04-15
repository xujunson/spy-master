package com.atu.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2023/4/15 17:59
 * @description: TODO
 **/
public class LogBean implements Serializable {

    private static final long serialVersionUID = -7651540728733995117L;

    private String preIvkApp;

    private String preIvkHost;

    private String preIp;

    private String traceId;

    private String spanId;

    private Map<String, Object> extData;

    public String getPreIvkApp() {
        return preIvkApp;
    }

    public void setPreIvkApp(String preIvkApp) {
        this.preIvkApp = preIvkApp;
    }

    public String getPreIvkHost() {
        return preIvkHost;
    }

    public void setPreIvkHost(String preIvkHost) {
        this.preIvkHost = preIvkHost;
    }

    public String getPreIp() {
        return preIp;
    }

    public void setPreIp(String preIp) {
        this.preIp = preIp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public Map<String, Object> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, Object> extData) {
        this.extData = extData;
    }

    public LogBean() {
    }

    public LogBean(String preIvkApp, String preIvkHost, String preIp, String traceId, String spanId) {
        this.preIvkApp = preIvkApp;
        this.preIvkHost = preIvkHost;
        this.preIp = preIp;
        this.traceId = traceId;
        this.spanId = spanId;
    }
}
