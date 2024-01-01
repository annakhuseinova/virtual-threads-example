package org.annakhuseinova.threadlocalscopedvalues10;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

public class Lecture6GetFirstSuccessfulAndCancelOthers {

    private final static Logger log = LoggerFactory.getLogger(Lecture6GetFirstSuccessfulAndCancelOthers.class);

    public static void main(String[] args) {
        try(var taskScope = new StructuredTaskScope.ShutdownOnSuccess()){
            var subtask1 = taskScope.fork(Lecture6GetFirstSuccessfulAndCancelOthers::getDeltaAirfare);
            var subtask2 = taskScope.fork(Lecture6GetFirstSuccessfulAndCancelOthers::getFrontierAirfare);

            // throws TimeoutException if the given time is exceeded
            taskScope.joinUntil(Instant.now().plusSeconds(2));


            // WHEN GETTING RESULTS OF DIFFERENT SCOPE TASKS ALWAYS CHECK THEIR STATE USING scopeTask.state() method!
            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

            // In order to get the first received result - call taskScope.result() method
            log.info("subtask result: {}", taskScope.result());

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static String getDeltaAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("delta: {}", random);
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + random;
    }

    private static String getFrontierAirfare(){
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("frontier: {}", random);
        CommonUtils.sleep("frontier", Duration.ofSeconds(2));
        return "Frontier-$" + random;
    }

    private static String failingTask(){
        throw new RuntimeException("oops");
    }
}
