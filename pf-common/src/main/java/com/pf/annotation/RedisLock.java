package com.pf.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLock {

	String keyPrefix() default "";

    /**
     * 是否阻塞锁；
     * 1. true：获取不到锁，阻塞一定时间；
     * 2. false：获取不到锁，立即返回
     */
    boolean isSpin() default true;

    /**
     * 超时时间
     */
    int expireTime() default 10000;

    /**
     * 等待时间
     */
    int waitTime() default 50;

    /**
     * 获取不到锁的等待时间
     */
    int retryTimes() default 20;
    
}
