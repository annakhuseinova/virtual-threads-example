package org.annakhuseinova.fibonacci3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    private static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);

    private static final int TASKS_COUNT = 3 * Runtime.getRuntime().availableProcessors();

    // When switching from platform thread builder to virtual thread builder when running demo method
    // we will see that virtual threads do not have an advantage when running purely cpu intensive tasks (when thread
    // blocking is not involved). Virtual threads are not faster than platform threads! They have a clear advantage
    // when dealing with numerous blocking calls (microservices architecture).
    public static void main(String[] args) {
       demo(Thread.ofVirtual());
    }
    
    private static void demo(Thread.Builder builder){
        CountDownLatch latch = new CountDownLatch(TASKS_COUNT);
        log.info(  "Starting number of tasks: {}", TASKS_COUNT);
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
