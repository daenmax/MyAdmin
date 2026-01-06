package cn.daenx.framework.cache.memory.data;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 内存清理定时器
 *
 * @author DaenMax
 */
public class MemoryCleanTimer {

    /**
     * 默认核心线程数
     */
    private static final int DEFAULT_CORE_THREAD_NUM = 1;

    /**
     * 允许核心线程销毁
     */
    private static final boolean DEFAULT_ALLOW_CORE_THREAD_CLEAN = true;

    /**
     * 允许核心线程空闲后销毁 (秒)
     */
    private static final int DEFAULT_CORE_THREAD_TIMEOUT = 60;

    /**
     * 单例实例
     */
    private static MemoryCleanTimer INSTANCE;

    /**
     * 定时器
     */
    private final ScheduledThreadPoolExecutor executor;


    private MemoryCleanTimer() {
        this.executor = new ScheduledThreadPoolExecutor(DEFAULT_CORE_THREAD_NUM);
        this.executor.setKeepAliveTime(DEFAULT_CORE_THREAD_TIMEOUT, TimeUnit.SECONDS);
        this.executor.allowCoreThreadTimeOut(DEFAULT_ALLOW_CORE_THREAD_CLEAN);
    }

    /**
     * 获取单例实例
     */
    public static MemoryCleanTimer getInstance() {
        if (INSTANCE == null) {
            synchronized (MemoryCleanTimer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MemoryCleanTimer();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 启动定时任务
     */
    public ScheduledFuture<?> schedule(Runnable task, long period, TimeUnit unit) {
        return this.executor.scheduleAtFixedRate(task, period, period, unit);
    }

    /**
     * 等待任务执行完停止
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * 立刻停止
     */
    public List<Runnable> shutdownNow() {
        return executor.shutdownNow();
    }
}
