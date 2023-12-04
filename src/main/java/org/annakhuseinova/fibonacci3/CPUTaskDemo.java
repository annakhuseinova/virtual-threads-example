package org.annakhuseinova.fibonacci3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    private static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);

    private static final int TASKS_COUNT = 5;

    public static void main(String[] args) {
       demo(Thread.ofPlatform());
    }
    
    private static void demo(Thread.Builder builder){
        CountDownLatch latch = new CountDownLatch(TASKS_COUNT);
        for (int i = 0; i < TASKS_COUNT; i++) {
            builder.start(()-> {
               Task.cpuIntensiveTask(45);
               latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
