package com.pf.test.aop;

import com.pf.annotation.RedisLock;
import com.pf.bean.RedisDistributionLock;
import com.pf.util.Asserts;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class RedisLockAdvice {

	@Autowired
    private RedisDistributionLock redisDistributionLock;

    @Before("@annotation(redisLock)")
    public void before(JoinPoint point, RedisLock redisLock) {
        System.out.println(point.getArgs());
        for (Object arg : point.getArgs()) {
            System.out.println(arg.toString());
        }
    }
    @Around("@annotation(redisLock)")
    public Object processAround(ProceedingJoinPoint pjp, RedisLock redisLock) throws Throwable {
        //获取方法上的注解对象
        String methodName = pjp.getSignature().getName();
        Class<?> classTarget = pjp.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        RedisLock redisLockAnnoation = objMethod.getDeclaredAnnotation(RedisLock.class);


        Object[] args = pjp.getArgs();
        StringBuilder temp = new StringBuilder();
        temp.append(redisLockAnnoation.keyPrefix());
        for (Object o : args) {
            temp.append(o.toString()).append("_");
        }
        String redisKey = StringUtils.removeEnd(temp.toString(), "_");

        //执行分布式锁的逻辑
        if (redisLockAnnoation.isSpin()) {
            //阻塞锁
            int lockRetryTime = 0;
            try {
                while (!redisDistributionLock.lock(redisKey, redisLockAnnoation.expireTime())) {
                    if (lockRetryTime++ > redisLockAnnoation.retryTimes()) {
                        log.error("lock exception. key:{}, lockRetryTime:{}", redisKey, lockRetryTime);
                        Asserts.fail("请勿重复提交!");
                    }
                    Thread.currentThread().sleep(redisLockAnnoation.waitTime());
                }
                return pjp.proceed();
            } finally {
                redisDistributionLock.unlock(redisKey);
            }
        } else {
            //非阻塞锁
            try {
                if (!redisDistributionLock.lock(redisKey)) {
                	log.error("lock exception. key:{}", redisKey);
                    Asserts.fail("分布式锁异常!");
                }
                return pjp.proceed();
            } finally {
                redisDistributionLock.unlock(redisKey);
            }
        }
    }

}
