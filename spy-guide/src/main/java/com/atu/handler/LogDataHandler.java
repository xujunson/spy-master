package com.atu.handler;

import com.atu.bean.LogBean;
import com.atu.common.constants.LogConstants;
import com.atu.common.utils.LocalhostUtil;
import com.atu.context.TraceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author: Tom
 * @date: 2023/4/15 18:14
 * @description: TODO
 **/
public class LogDataHandler {
    protected static final Logger log = LoggerFactory.getLogger(LogDataHandler.class);

    public void processProviderSide(LogBean logBean) {
        if (StringUtils.isBlank(logBean.getPreIvkApp())) {
            logBean.setPreIvkApp(LogConstants.UNKNOWN);
        }
        TraceContextHolder.putPreIvkApp(logBean.getPreIvkApp());

        if (StringUtils.isBlank(logBean.getPreIvkHost())) {
            logBean.setPreIvkHost(LogConstants.UNKNOWN);
        }
        TraceContextHolder.putPreIvkHost(logBean.getPreIvkHost());

        if (StringUtils.isBlank(logBean.getPreIp())) {
            logBean.setPreIp(LogConstants.UNKNOWN);
        }
        TraceContextHolder.putPreIp(logBean.getPreIp());

        //如果没有获取到，则重新生成一个traceId
        if (StringUtils.isBlank(logBean.getTraceId())) {
            logBean.setTraceId(UUID.randomUUID().toString());
            log.debug("[LOG]可能上一个节点[{}]没有正确传递traceId,重新生成traceId[{}]", logBean.getPreIvkApp(), logBean.getTraceId());
        }

        //往上下文里放当前获取到的spanId，如果spanId为空，会放入初始值
        TraceContextHolder.putSpanId(logBean.getSpanId());

        //往上下文里放一个当前的traceId
        TraceContextHolder.putTraceId(logBean.getTraceId());

        //往上下文里放当前的IP
        TraceContextHolder.putCurrIp(LocalhostUtil.getHostIp());

        MDC.put(LogConstants.LOG_TRACE_KEY, TraceContextHolder.getTraceId());
        MDC.put(LogConstants.LOG_SPANID_KEY, TraceContextHolder.getSpanId());
        MDC.put(LogConstants.CURR_IP_KEY, TraceContextHolder.getCurrIp());
        MDC.put(LogConstants.PRE_IP_KEY, TraceContextHolder.getPreIp());
        MDC.put(LogConstants.PRE_IVK_APP_HOST, TraceContextHolder.getPreIvkHost());
        MDC.put(LogConstants.PRE_IVK_APP_KEY, TraceContextHolder.getPreIvkApp());
    }

    public void cleanThreadLocal() {
        //移除ThreadLocal里的数据
        TraceContextHolder.removePreIvkApp();
        TraceContextHolder.removePreIvkHost();
        TraceContextHolder.removePreIp();
        TraceContextHolder.removeCurrIp();
        TraceContextHolder.removeTraceId();
        TraceContextHolder.removeSpanId();

        MDC.remove(LogConstants.LOG_TRACE_KEY);
        MDC.remove(LogConstants.LOG_SPANID_KEY);
        MDC.remove(LogConstants.CURR_IP_KEY);
        MDC.remove(LogConstants.PRE_IP_KEY);
        MDC.remove(LogConstants.PRE_IVK_APP_HOST);
        MDC.remove(LogConstants.PRE_IVK_APP_KEY);
    }
}
