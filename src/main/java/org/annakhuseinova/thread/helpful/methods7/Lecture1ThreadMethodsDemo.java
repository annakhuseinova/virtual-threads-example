package org.annakhuseinova.thread.helpful.methods7;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Lecture1ThreadMethodsDemo {

    private static final Logger log = LoggerFactory.getLogger(Lecture1ThreadMethodsDemo.class);

    public static void main(String[] args) throws InterruptedException {
        isVirtual();
        join();
    }

    private static void isVirtual(){
        var thread1 = Thread.ofVirtual().start(()-> CommonUtils.sleep(Duration.ofSeconds(2)));
        var thread2 = Thread.ofPlatform().start(()-> CommonUtils.sleep(Duration.ofSeconds(2)));
        log.info("Is thread-1 virtual {}", thread1.isVirtual());
        log.info("Is thread-2 virtual {}", thread2.isVirtual());
        log.info("IS current thread virtual: {}", Thread.currentThread().isVirtual());
    }

    // Wait for the 2 threads to terminate. Behind the scenes join() uses CountDownLatch await()
    private static void join() throws InterruptedException {
        var thread1 = Thread.ofVirtual().start(()-> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called product service");
        });
        var thread2 = Thread.ofVirtual().start(()-> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called pricing service");
        });
        thread1.join();
        thread2.join();
    }

    /*
    * To interrupt / stop the thread execution
    * in some cases, java will throw interrupted exception. IO exception, socket exception etc
    *
    * We can also check if the current thread is interrupted Thread.currentThread().isInterrupted() - returns a boolean
    * while(!Thread.currentThread().isInterrupted()){
    *   ...
    * }
    * */
    private static void interrupt(){
        var thread1 = Thread.ofVirtual().start(()-> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("called product service");
        });
        log.info("Is thread-1 interrupted: {}", thread1.isInterrupted());
        thread1.interrupt();
        log.info("Is thread-1 interrupted: {}", thread1.isInterrupted());
    }
}
