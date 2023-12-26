package org.annakhuseinova.executor.service8.aggregator;

import org.annakhuseinova.executor.service8.externalservice.Client;

import java.util.concurrent.ExecutorService;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws Exception{
        var productFuture = executorService.submit(()-> Client.getProduct(id));
        var ratingFuture = executorService.submit(()-> Client.getRating(id));
        return new ProductDto(id, productFuture.get(), ratingFuture.get());
    }
}
