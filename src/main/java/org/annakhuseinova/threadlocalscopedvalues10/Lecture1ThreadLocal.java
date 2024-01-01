package org.annakhuseinova.threadlocalscopedvalues10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Lecture1ThreadLocal {

    private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();

    private static final Logger log = LoggerFactory.getLogger(Lecture1ThreadLocal.class);

    public static void main(String[] args) {

    }

    private static void processIncomingRequest(){
        authenticate();
        controller();
    }

    private static void authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        SESSION_TOKEN.set(token);
    }

    private static void controller(){
        log.info("controller: {}", SESSION_TOKEN.get());
    }

    private static void service(){
        log.info("service: {}", SESSION_TOKEN.get());
        callExternalService();
    }

    private static void callExternalService(){
        log.info("preparing HTTP request with token: {}", SESSION_TOKEN.get());
    }
}
