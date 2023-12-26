package org.annakhuseinova.executor.service8;

import org.annakhuseinova.executor.service8.aggregator.AggregatorService;
import org.annakhuseinova.executor.service8.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Lecture4AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Lecture4AggregatorDemo.class);

    public static void main(String[] args) throws Exception {

        // bean
        var executorService = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executorService);

        var futures = IntStream.rangeClosed(1, 50)
                               .mapToObj(id -> executorService.submit(()-> aggregator.getProductDto(id)))
                                       .toList();
        List<ProductDto> products = futures
                                        .stream()
                                        .map(Lecture4AggregatorDemo::toProductDto)
                                        .toList();

        log.info("list: {}", products);
    }

    private static ProductDto toProductDto(Future<ProductDto> future){
        try {
            return future.get();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
