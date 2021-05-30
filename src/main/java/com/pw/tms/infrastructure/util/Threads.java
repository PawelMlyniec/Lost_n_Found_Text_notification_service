package com.pw.tms.infrastructure.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Threads {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(int millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
