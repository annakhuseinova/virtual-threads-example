package org.annakhuseinova.executor.service8;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lecture2ExecutorServices {

    private static final Logger log = LoggerFactory.getLogger(Lecture2ExecutorServices.class);

    public static void main(String[] args) {
        // execute(Executors.newSingleThreadExecutor(), 3);
        // execute(Executors.newFixedThreadPool(5), 20);
        // execute(Executors.newCachedThreadPool(), 200);
        //execute(Executors.newVirtualThreadPerTaskExecutor(), 200);
        //scheduled();
    }

    private static void scheduled(){
        try(var executorService = Executors.newSingleThreadScheduledExecutor()){
            executorService.scheduleAtFixedRate(()-> {
                log.info("executing task");
            }, 0, 1, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(5));
        }

    }

    private static void execute(ExecutorService executorService, int taskCount){
        try(executorService){
            for (int i = 0; i < taskCount; i++) {
                int j = i;
                executorService.submit(()-> ioTask(j));
            }
           log.info("submitted");
        }
    }

    private static void ioTask(int i){
        log.info("Task started: {}. Thread info: {}", i, Thread.currentThread());
        CommonUtils.sleep(Duration.ofSeconds(5));
        log.info("Task ended: {}. Thread info: {}", i, Thread.currentThread());
    }
}
