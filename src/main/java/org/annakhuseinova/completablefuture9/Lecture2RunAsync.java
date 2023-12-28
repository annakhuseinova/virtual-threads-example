package org.annakhuseinova.completablefuture9;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

// runAsync(Runnable runnable) allows to launch an asynchronous task that does not return anything
public class Lecture2RunAsync {

    private static final Logger log = LoggerFactory.getLogger(Lecture2RunAsync.class);

    public static void main(String[] args) {
        log.info("main starts");

        runAsync()
                // thenRun() async method runs when the previous operation completes normally
                // exceptionally() defines what to do and to return if an exception occurred
                .exceptionally(throwable -> {
                    log.error("Error occurred", throwable);
                    return null;
                })
                .thenRun(()-> log.info("it is done"));

        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<Void> runAsync(){
        log.info("method starts");

        CompletableFuture<Void> taskCompleted = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            log.info("task completed");
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("method ends");
        return taskCompleted;
    }
}
