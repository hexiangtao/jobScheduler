package com.ykn.jobscheduler.other;

import java.util.concurrent.TimeUnit;

/**
 * @author hexiangtao
 * @date 2022/4/3 11:38
 **/
public class MemoryMonitor extends Thread implements Runnable {

    private static final MemoryMonitor INSTANCE = new MemoryMonitor();


    private MemoryMonitor() {

    }

    public static void monitor() {
        INSTANCE.start();
    }

    @Override
    public void run() {
        while (true) {
            Runtime runtime = Runtime.getRuntime();
            long max = runtime.maxMemory() / 1024 / 1024;
            long free = runtime.freeMemory() / 1024 / 1024;
            long total = runtime.totalMemory() / 1024 / 1024;
            long usable = max - total + free;
            long used = total - free;
            System.out.printf("已分配:%sMB,最大:%sMB,已使用:%sMB,空闲:%sMB,可用:%sMB\n", total, max, used, free, usable);
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
