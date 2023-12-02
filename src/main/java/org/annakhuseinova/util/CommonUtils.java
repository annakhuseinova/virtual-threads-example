package org.annakhuseinova.util;

import java.time.Duration;

public final class CommonUtils {

    public static void sleep(Duration duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
