package com.quicksilver.mbeattie.rxperiment.utils;

import android.os.Looper;

public final class ThreadUtils {

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    private ThreadUtils() {}
}
