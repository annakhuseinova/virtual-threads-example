package org.annakhuseinova.executor.service8;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;

public class Lecture1AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(Lecture1AutoCloseable.class);

    public static void main(String[] args) {
        var executorService = Executors.newSingleThreadExecutor();
        executorService.submit(Lecture1AutoCloseable::task);
        executorService.shutdown();
        log.info("submitted");
    }

    private static void task(){
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("task executed");
    }
}
