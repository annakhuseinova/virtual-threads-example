package org.annakhuseinova.fibonacci3;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensiveTask(int i){
        log.info("starting CPU task. Thread info: {}", Thread.currentThread());
        var timeTaken = CommonUtils.timer(()-> findFib(i));
        log.info("ending CPU task. Time taken: {} ms", timeTaken);
    }


    // 2^N - intentionally done this way to simulate CPU intensive task
    public static long findFib(long input){
        if (input < 2) return input;
        return findFib(input - 1) + findFib(input - 2);
    }
}
