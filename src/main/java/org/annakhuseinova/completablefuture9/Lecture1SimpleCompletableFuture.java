package org.annakhuseinova.completablefuture9;

import org.annakhuseinova.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class Lecture1SimpleCompletableFuture {

    private static final Logger log = LoggerFactory.getLogger(Lecture1SimpleCompletableFuture.class);

    public static void main(String[] args) {
        log.info("main starts");
        var cf = slowTask();
        cf.thenAccept(v-> log.info("value={}", v));
        //log.info("value={}", cf.join());
        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> fastTask(){
        log.info("method starts");
        var completableFuture = new CompletableFuture<String>();
        // sets the value of the cf (that will be available through get()).
        // Returns true if cf has transitioned to completed state
        completableFuture.complete("hi");
        log.info("method ends");
        return completableFuture;
    }

    private static CompletableFuture<String> slowTask(){
        log.info("methods starts");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(()-> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            cf.complete("Hi");
        });
        log.info("method ends");
        return cf;
    }
}
