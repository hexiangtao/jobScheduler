package com.ykn.jobscheduler.util;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author: hexiangtao
 * @date: 2020/3/26 14:48
 */
public class FunctionUtils {

    public static <T> T retryIfException(final long millSeconds, final int maxRetryCount, Callable<T> call) throws Exception {
        int retryCount = 0;
        while (true) {
            try {
                retryCount++;
                return call.call();
            } catch (Exception  ex) {
                if (retryCount > maxRetryCount) {
                    throw ex;
                } else {
                    ThreadUtils.sleepSeconds(TimeUnit.MILLISECONDS, millSeconds);
                }
            }
        }
    }
}
