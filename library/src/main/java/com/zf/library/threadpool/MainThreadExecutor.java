package com.zf.library.threadpool;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * 主线程池
 */
public class MainThreadExecutor implements Executor {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        handler.post(command);
    }
}
