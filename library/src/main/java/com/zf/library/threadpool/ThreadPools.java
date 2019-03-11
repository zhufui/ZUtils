package com.zf.library.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPools {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadPools #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    private static final Executor DISK_EXECUTOR;
    private static final Executor NETWORK_EXECUTOR;
    private static final Executor MAIN_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        DISK_EXECUTOR = threadPoolExecutor;
        NETWORK_EXECUTOR = threadPoolExecutor;
        MAIN_EXECUTOR = new MainThreadExecutor();
    }

    /**
     * io线程池
     *
     * @param r
     */
    public static void diskExecute(Runnable r) {
        DISK_EXECUTOR.execute(r);
    }

    /**
     * 网络线程池
     *
     * @param r
     */
    public static void networkExecute(Runnable r) {
        NETWORK_EXECUTOR.execute(r);
    }

    /**
     * 主线程池
     *
     * @param r
     */
    public static void mainExecute(Runnable r) {
        MAIN_EXECUTOR.execute(r);
    }
}
