package org.annakhuseinova.cooperative.scheduling4;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CooperativeSchedulingDemo {

    private static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);

    // Adjusting the carrier thread pool to just 1 thread
    static {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
    }

    public static void main(String[] args) {
        Thread.Builder.OfVirtual virtualThreadBuilder = Thread.ofVirtual();
        Thread firstThread = virtualThreadBuilder.unstarted(() -> demo(1));
        Thread secondthread = virtualThreadBuilder.unstarted(()-> demo(2));
        firstThread.start();
        secondthread.start();
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(int threadNumber){
        log.info("thread-{} started", threadNumber);
        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is printing {}. Thread: {}", threadNumber, i, Thread.currentThread());
            Thread.yield();
        }
        log.info("thread-{} ended", threadNumber);
    }
}
