package org.annakhuseinova.thread.creation1;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 1000000;

    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo1();
    }

    private static void platformThreadDemo1(){
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = new Thread(()-> Task.imitateIOIntensiveTask(j));
            thread.start();
        }
        Thread.yield();
    }

    private static void platformThreadDemo2(){
        Thread.Builder.OfPlatform builder = Thread.ofPlatform().name("some-thread-name", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(()-> Task.imitateIOIntensiveTask(j));
            thread.start();
        }
    }

    private static void platformDaemonThreadDemo3() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(MAX_PLATFORM);
        Thread.Builder.OfPlatform builder = Thread.ofPlatform()
                .daemon()
                .name("some-daemon-thread-name", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(()-> {
                Task.imitateIOIntensiveTask(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }

    private static void virtualThreadDemo2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(MAX_VIRTUAL);
        // Virtual threads do not have any name by default
        // Virtual threads are daemon threads by default
        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("some-virtual-thread", 1);
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(()-> {
                Task.imitateIOIntensiveTask(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }

}
