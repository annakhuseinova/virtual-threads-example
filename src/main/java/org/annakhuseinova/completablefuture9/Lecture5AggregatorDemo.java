package org.annakhuseinova.completablefuture9;

import org.annakhuseinova.completablefuture9.aggregator.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Lecture5AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(Lecture5AggregatorDemo.class);
    public static void main(String[] args) throws Exception {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        log.info("product={}", aggregator.getProductDto(1));
    }
}
