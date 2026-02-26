package cn.daenx.framework.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gltqe
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean("CommonTaskExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);

        // 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(50);

        // 队列容量：用来缓冲执行任务的队列
        executor.setQueueCapacity(100);

        // 允许线程的空闲时间60秒：当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);

        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("common-");

        //1.DiscardOldestPolicy，抛弃最早的任务，将新任务加入队列。
        //2.AbortPolicy，拒绝执行新任务，并抛出异常。
        //3.CallerRunsPolicy，交由调用者线程执行新任务，如果调用者线程已关闭，则抛弃任务。
        //4.DiscardPolicy，直接抛弃新任务。
        // 拒绝策略：当线程池和队列都满了，说明线程池已经处于饱和状态，那么必须采取一种策略处理新提交的任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 关闭应用时 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 设置线程池中任务的等待时间，如果超过这个时间还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);

        // 初始化线程池
        executor.initialize();

        return executor;
    }
}
