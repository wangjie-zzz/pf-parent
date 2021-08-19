package com.pf.bean;

import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisDistributionLock {

    private static final String UNLOCK_LUA;

    static {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        stringBuffer.append("then ");
        stringBuffer.append("    return redis.call(\"del\",KEYS[1]) ");
        stringBuffer.append("else ");
        stringBuffer.append("    return 0 ");
        stringBuffer.append("end ");

        UNLOCK_LUA = stringBuffer.toString();
    }
    //锁默认超时时间,20秒
    private static final long defaultExpireTime = 20 * 1000;


	private StringRedisTemplate stringRedisTemplate;
    private final String lockValue;

	public RedisDistributionLock(StringRedisTemplate stringRedisTemplate) {
	    this.stringRedisTemplate = stringRedisTemplate;
	    this.lockValue = UUID.randomUUID().toString();
    }

    /**
     * 加锁,锁默认超时时间20秒
     * @param resource
     * @return
     */
    public boolean lock(String resource) {
        return this.lock(resource, defaultExpireTime);
    }

    public boolean lock(String lockKey, long timeout) {
        return lock(lockKey, timeout, 0, 0);
    }

    public boolean lock(String lockKey, long timeout, long retryTimes) {
        return lock(lockKey, timeout, retryTimes, 1000);
    }

    /**
     * 加锁,同时设置锁超时时间
     * @param lockKey 分布式锁的key
     * @param timeout 单位是ms
     * @return
     */
    public boolean lock(String lockKey, long timeout, long retryTimes, long sleepMillis) {
        boolean isLocked = false;
        while (!isLocked && retryTimes >= 0) {
            isLocked = set(new StringRedisSerializer().serialize(lockKey), new StringRedisSerializer().serialize(lockValue), timeout);

            try {
                TimeUnit.MILLISECONDS.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }

            retryTimes--;
        }

        return isLocked;
    }
    
    public boolean checkDistributedLock(String key) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    public boolean unlock(String lockKey) {
        log.debug("redis unlock debug, start. resource:[{}].",lockKey);
        return del(new StringRedisSerializer().serialize(lockKey), new StringRedisSerializer().serialize(lockValue));
    }

    private boolean set(byte[] lockKey, byte[] lockValue, long timeout) {
        String result = stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            Object nativeConnection = connection.getNativeConnection();

            if (nativeConnection instanceof RedisAsyncCommands) {
                return ((RedisAsyncCommands) nativeConnection).getStatefulConnection().sync().set(lockKey, lockValue, SetArgs.Builder.nx().px(timeout));
            }

            if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                return ((RedisAdvancedClusterAsyncCommands) nativeConnection).getStatefulConnection().sync().set(lockKey, lockValue, SetArgs.Builder.nx().px(timeout));
            }

            return null;
        });

        return !StringUtils.isEmpty(result);
    }


    private boolean del(byte[] lockKey, byte[] lockValue) {
        Long result = stringRedisTemplate.execute((RedisCallback<Long>) connection -> {
            Object nativeConnection = connection.getNativeConnection();

            if (nativeConnection instanceof RedisAsyncCommands) {
                return (Long) ((RedisAsyncCommands) nativeConnection).getStatefulConnection().sync().eval(UNLOCK_LUA, ScriptOutputType.INTEGER, Collections.singletonList(lockKey).toArray(), Collections.singletonList(lockValue).toArray());
            }

            if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                return (Long) ((RedisAdvancedClusterAsyncCommands) nativeConnection).getStatefulConnection().sync().eval(UNLOCK_LUA, ScriptOutputType.INTEGER, Collections.singletonList(lockKey).toArray(), Collections.singletonList(lockValue).toArray());
            }

            return 0L;
        });

        return result != null && result > 0L;
    }
}
