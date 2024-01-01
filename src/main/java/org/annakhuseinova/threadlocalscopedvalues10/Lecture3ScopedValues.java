package org.annakhuseinova.threadlocalscopedvalues10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Lecture3ScopedValues {

    private static final Logger log = LoggerFactory.getLogger(Lecture3ScopedValues.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();
    public static void main(String[] args) {
        log.info("value={}", SESSION_TOKEN.get());
        log.info("isBound={}", SESSION_TOKEN.isBound());
        log.info("value={}", SESSION_TOKEN.orElse("default"));
    }

    private static void processIncomingRequest(){
        var token = authenticate();
        ScopedValue.runWhere(SESSION_TOKEN, token, ()-> controller());
    }

    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    private static void controller(){
        log.info("controller: {}", SESSION_TOKEN.get());
        service();
    }

    private static void service(){
        log.info("service: {}", SESSION_TOKEN.get());
        callExternalService();
    }

    private static void callExternalService(){
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }
}

