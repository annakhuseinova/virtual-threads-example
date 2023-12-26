package org.annakhuseinova.executor.service8;

import org.annakhuseinova.executor.service8.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Lecture3AccessResponseUsingFuture {

    private static final Logger log = LoggerFactory.getLogger(Lecture2ExecutorServices.class);

    public static void main(String[] args) throws Exception{
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){
            Future<String> productOneFuture = executorService.submit(() -> Client.getProduct(1));
            Future<String> productTwoFuture = executorService.submit(() -> Client.getProduct(2));
            Future<String> productThreeFuture = executorService.submit(() -> Client.getProduct(3));

            log.info("product-1: {}", productOneFuture.get());
            log.info("product-2: {}", productTwoFuture.get());
            log.info("product-3: {}", productThreeFuture.get());
        }
    }
}
