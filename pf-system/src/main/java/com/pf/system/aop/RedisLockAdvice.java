package com.pf.system.aop;

import com.pf.annotation.RedisLock;
import com.pf.bean.RedisDistributionLock;
import com.pf.util.Asserts;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
            try {
                if (!redisDistributionLock.lock(redisKey, redisLockAnnoation.expireTime(), redisLockAnnoation.retryTimes(), redisLockAnnoation.waitTime())) {
                    log.error("lock exception. key:{}, lockRetryTime:{}", redisKey, redisLockAnnoation.retryTimes());
                    Asserts.fail("请勿重复提交!");
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
                    Asserts.fail("请勿重复提交!");
                }
                return pjp.proceed();
            } finally {
                redisDistributionLock.unlock(redisKey);
            }
        }
    }

}
