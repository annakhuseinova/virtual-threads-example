package org.annakhuseinova.thread.factory6;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class Lecture1ThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(Lecture1ThreadFactory.class);
    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("thread-name", 1).factory());

        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static void demo(ThreadFactory threadFactory){
        for (int i = 0; i < 3; i++) {
            var parentThread = threadFactory.newThread(()-> {
               log.info("Parent task started. {}", Thread.currentThread());
                var childThread = threadFactory.newThread(()-> {
                    log.info("Child task started. {}", Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("Child task ended. {}", Thread.currentThread());
                });
                childThread.start();
               log.info("Parent task ended. {}", Thread.currentThread());
            });
            parentThread.start();
        }
    }
}
