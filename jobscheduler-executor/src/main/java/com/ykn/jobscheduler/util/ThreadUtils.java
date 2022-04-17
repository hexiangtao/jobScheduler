package com.ykn.jobscheduler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: hexiangtao
 * @date: 2020/3/26 12:20
 */
public class ThreadUtils {

    static Logger log = LoggerFactory.getLogger(ThreadUtils.class);

    public static void sleepSeconds(TimeUnit timeUnit, long timeout) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            //ignore the exception
        }
    }

    public static void shutdown(ExecutorService executorService, int awaitSeconds) {

        log.info("closing executorService await {} seconds...",awaitSeconds);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(awaitSeconds, TimeUnit.SECONDS)) {
                log.info("await timeout invoke shutdownNow() methods");
                executorService.shutdownNow();
                if (!executorService.awaitTermination(awaitSeconds, TimeUnit.SECONDS)) {
                    log.error("Thread pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
        }
        log.info("executorService closed");
    }

    public static void shutdownNow(ExecutorService executorService, int awaitSeconds) {
        try {
            log.info("closing executorService...");
            executorService.shutdownNow();
            if (!executorService.awaitTermination(awaitSeconds, TimeUnit.SECONDS)) {
                log.error("Thread pool did not terminate");
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
        }
        Thread.currentThread().interrupt();
    }
}
