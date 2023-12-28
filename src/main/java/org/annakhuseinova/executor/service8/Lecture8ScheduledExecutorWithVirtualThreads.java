package org.annakhuseinova.executor.service8;

import org.annakhuseinova.executor.service8.externalservice.Client;
import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lecture8ScheduledExecutorWithVirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Lecture8ScheduledExecutorWithVirtualThreads.class);
    public static void main(String[] args) {
        scheduled();
    }

    // To schedule tasks periodically - we  use platform thread to offload task to virtual thread executor.
    // As of now - the only way to provide scheduling for virtual threads - workaround
    private static void scheduled(){
        var scheduledExecutorWithPlatformThreads = Executors.newSingleThreadScheduledExecutor();
        var virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
        try(scheduledExecutorWithPlatformThreads; virtualThreadExecutor) {
            scheduledExecutorWithPlatformThreads.scheduleAtFixedRate(
                    ()-> {
                        virtualThreadExecutor.submit(()-> printProductInfo(1));
                    }, 0, 3, TimeUnit.SECONDS
            );
            CommonUtils.sleep(Duration.ofSeconds(15));
        }
    }

    private static void printProductInfo(int id){
        log.info("{} => {}", id, Client.getProduct(id));
    }
}
