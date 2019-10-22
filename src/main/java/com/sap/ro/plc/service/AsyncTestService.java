package com.sap.ro.plc.service;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

@Service
@Async
public class AsyncTestService {

    private Random random = new Random(System.currentTimeMillis());

    public void threadName(Logger logger) {
        logger.info("threadName current thread: {}", Thread.currentThread());
    }

    public Future<String> async1(Logger logger) throws InterruptedException, IllegalStateException {
        logger.info("async1 current thread: {}", Thread.currentThread());
        return getStringFuture();
    }

    public Future<String> async2(Logger logger) throws InterruptedException, IllegalStateException {
        logger.info("async2 current thread: {}", Thread.currentThread());
        return getStringFuture();
    }

    public Future<String> async3(Logger logger) throws InterruptedException, IllegalStateException {
        logger.info("async3 current thread: {}", Thread.currentThread());
        return getStringFuture();
    }

    private Future<String> getStringFuture() throws InterruptedException {
        Thread.sleep(random.nextInt(5001));
        if (random.nextInt() % 5 == 0) {
            throw new IllegalStateException("illegal state");
        }
        return new AsyncResult<>(Thread.currentThread().getName());
    }
}
