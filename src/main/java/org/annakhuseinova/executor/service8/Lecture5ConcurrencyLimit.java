package org.annakhuseinova.executor.service8;

import org.annakhuseinova.executor.service8.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class Lecture5ConcurrencyLimit {

    private static final Logger log = LoggerFactory.getLogger(Lecture5ConcurrencyLimit.class);

    public static void main(String[] args) {

    }
    
    private static void execute(ExecutorService executorService, int taskCount){
        try(executorService) {
            for (int i = 1; i <= taskCount; i++) {
                int j = i;
                executorService.submit(()-> printProductInfo(j));
            }
        }
        log.info("submitted");
    }

    private static void printProductInfo(int id){
        log.info("{} => {}", id, Client.getProduct(id));
    }
}
