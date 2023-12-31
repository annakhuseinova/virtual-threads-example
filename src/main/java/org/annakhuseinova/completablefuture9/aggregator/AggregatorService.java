package org.annakhuseinova.completablefuture9.aggregator;

import org.annakhuseinova.executor.service8.externalservice.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id){
        var productFuture = CompletableFuture.supplyAsync(()-> Client.getProduct(id), executorService)
                .exceptionally(t -> "product-not-found")
                .orTimeout(750, TimeUnit.MILLISECONDS)
                .exceptionally(t -> "timeout");
        var ratingFuture = CompletableFuture.supplyAsync(()-> Client.getRating(id), executorService)
                .exceptionally(t -> -1)
                .orTimeout(750, TimeUnit.MILLISECONDS)
                .exceptionally(t -> 1);
        return new ProductDto(id, productFuture.join(), ratingFuture.join());
    }
}
