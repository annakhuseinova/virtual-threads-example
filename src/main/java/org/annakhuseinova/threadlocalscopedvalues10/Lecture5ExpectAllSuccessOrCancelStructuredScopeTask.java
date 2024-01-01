package org.annakhuseinova.threadlocalscopedvalues10;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

/*
* Cancel all the running subtasks when one of the subtasks fails
* */
public class Lecture5ExpectAllSuccessOrCancelStructuredScopeTask {

    private final static Logger log = LoggerFactory.getLogger(Lecture5ExpectAllSuccessOrCancelStructuredScopeTask.class);
    public static void main(String[] args) {
        try(var taskScope = new StructuredTaskScope.ShutdownOnFailure()){
            var subtask1 = taskScope.fork(Lecture5ExpectAllSuccessOrCancelStructuredScopeTask::getDeltaAirfare);
            var subtask2 = taskScope.fork(Lecture5ExpectAllSuccessOrCancelStructuredScopeTask::getFrontierAirfare);

            // throws TimeoutException if the given time is exceeded
            taskScope.joinUntil(Instant.now().plusSeconds(2));

            taskScope.throwIfFailed();

            // WHEN GETTING RESULTS OF DIFFERENT SCOPE TASKS ALWAYS CHECK THEIR STATE USING scopeTask.state() method!
            log.info("subtask1 state: {}", subtask1.state());
            log.info("subtask2 state: {}", subtask2.state());

            log.info("subtask1 result: {}", subtask1.get());
            log.info("subtask2 result: {}", subtask2.get());

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
