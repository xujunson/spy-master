package com.atu.common;

import com.atu.bean.LogBean;
import com.atu.handler.LogDataHandler;

/**
 * @author: Tom
 * @date: 2023/4/15 18:29
 * @description: TODO
 **/
public class LogWebCommon extends LogDataHandler {
    private static volatile LogWebCommon logWebCommon;

    public static LogWebCommon loadInstance() {
        if (logWebCommon == null) {
            synchronized (LogWebCommon.class) {
                if (logWebCommon == null) {
                    logWebCommon = new LogWebCommon();
                }
            }
        }
        return logWebCommon;
    }

    public void preHandle() {
        LogBean logBean = new LogBean();
        processProviderSide(logBean);
    }

    public void afterCompletion() {
        cleanThreadLocal();
    }
}
