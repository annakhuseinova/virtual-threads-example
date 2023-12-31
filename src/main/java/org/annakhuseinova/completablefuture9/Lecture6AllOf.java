package org.annakhuseinova.completablefuture9;

import org.annakhuseinova.completablefuture9.aggregator.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Lecture6AllOf {

    private static final Logger log = LoggerFactory.getLogger(Lecture6AllOf.class);

    public static void main(String[] args) {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        var futures = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> CompletableFuture.supplyAsync(()-> aggregator.getProductDto(id), executor)).toList();

        // We can wait until all the provided futures complete but in order to get the results of all of futures
        // we need to get them manually via join()
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        var list = futures.stream().map(CompletableFuture::join).toList();

        log.info("list: {}", list);
    }

}
