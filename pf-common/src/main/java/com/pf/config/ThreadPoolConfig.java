//package com..mall.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
//@Configuration
//public class ThreadPoolConfig {
//
//	public static final String EXECUTOR = "Executor-";
//
//	public static final int CorePoolSize = 10;
//
//	public static final int AWAIT_TIME = 600; // 同步执行等待最长时间
//
////    @Autowired
////    private CalcThreadPoolUncaughtExceptionHandler calcThreadPoolUncaughtExceptionHandler;
//
//	@Bean("calcThreadPool")
//	public static ThreadPoolTaskExecutor CalcThreadPool() {
//        /*ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(CorePoolSize, new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                final AtomicInteger atomicInteger = new AtomicInteger(0);
//                Thread thread = new Thread(r, EXECUTOR + atomicInteger.getAndIncrement());
//                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//                    @Override
//                    public void uncaughtException(Thread t, Throwable e) {
//                        log.error("-----------------ThreadPoolConfig--{}, {}",e.getMessage(), e);
//                    }
//                });
//                return thread;
//            }
//        });
//        executor.setKeepAliveTime(30, TimeUnit.SECONDS);
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); // 抛出异常*/
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 设置核心线程数
//        executor.setCorePoolSize(CorePoolSize);
//        // 设置最大线程数
//        executor.setMaxPoolSize(CorePoolSize);
//        // 设置队列容量
//        executor.setQueueCapacity(10000);
//        // 设置线程活跃时间（秒）
//        executor.setKeepAliveSeconds(30);
//        // 设置默认线程名称
//        executor.setThreadNamePrefix(EXECUTOR);
//        // 设置拒绝策略
////        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); // 抛出异常
//        // 等待所有任务结束后再关闭线程池
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        // 核心线程池超过keepAliveTime是否销毁
//        executor.setAllowCoreThreadTimeOut(true);
//        executor.initialize();
//        return executor;
//    }
//
//}
