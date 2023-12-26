package org.annakhuseinova.executor.service8;

import org.annakhuseinova.executor.service8.concurrencylimit.ConcurrencyLimiter;
import org.annakhuseinova.executor.service8.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Lecture6ConcurrencyLimitWithSemaphore {

    private static final Logger log = LoggerFactory.getLogger(Lecture6ConcurrencyLimitWithSemaphore.class);

    public static void main(String[] args) throws Exception {
        ConcurrencyLimiter concurrencyLimiter = new ConcurrencyLimiter(Executors.newVirtualThreadPerTaskExecutor(), 3);
        execute(concurrencyLimiter, 20);
    }

    private static void execute(ConcurrencyLimiter concurrencyLimiter, int taskCount) throws Exception {
        try(concurrencyLimiter) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(()-> printProductInfo(j));
            }
        }
        log.info("submitted");
    }

    private static String printProductInfo(int id){
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }
}
