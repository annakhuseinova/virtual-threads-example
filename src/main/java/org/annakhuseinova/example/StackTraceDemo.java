package org.annakhuseinova.example;

public class StackTraceDemo {

    public static void main(String[] args) {
        demo(Thread.ofPlatform());
    }

    private static void demo(Thread.Builder builder){
        for (int i = 1; i <= 20; i++) {
            int j = i;
            builder.start(()-> Task.execute(j));
        }
    }
}
