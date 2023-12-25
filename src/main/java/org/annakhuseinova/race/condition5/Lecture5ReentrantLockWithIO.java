package org.annakhuseinova.race.condition5;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lecture5ReentrantLockWithIO {

    private static final Logger log = LoggerFactory.getLogger(Lecture5ReentrantLockWithIO.class);
    private static final Lock lock = new ReentrantLock(true);

    static {
        System.setProperty("jdk.tracePinnedThreads", "full");
    }

    public static void main(String[] args) {
        demo(Thread.ofPlatform());
        CommonUtils.sleep(Duration.ofSeconds(15));
    }
    
    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(()-> {
                log.info("Task started. {}", Thread.currentThread());
                ioTask();
                log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }
    
    private static void ioTask(){
        try {
            lock.lock();
            CommonUtils.sleep(Duration.ofSeconds(10));
        } catch (Exception e){
            log.error("error", e);
        } finally {
            lock.unlock();
        }

    }
}
