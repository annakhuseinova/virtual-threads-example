package org.annakhuseinova.executor.service8.concurrencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class ConcurrencyLimiter implements AutoCloseable {

    private final static Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);
    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public ConcurrencyLimiter(ExecutorService executorService, int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> submit(Callable<T> callable){
        return executorService.submit(()-> wrapCallable(callable));
    }

    private <T> T wrapCallable(Callable<T> callable){
        try {
            semaphore.acquire();
            return callable.call();
        } catch (Exception e){
            log.error("error", e);
        } finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.executorService.close();
    }
}
