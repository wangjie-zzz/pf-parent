package com.pf.util;

import com.pf.constant.CommonConstants;
import com.pf.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/***
* @Title:
* @Param:
* @description: 获取用户缓存
*/
@Slf4j
public class CacheDataUtil {


	public static void setUser(HttpServletRequest request, RedisTemplate redisTemplate, SecurityUser user) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			log.info("redis添加用户缓存：{}， {}", session.getId(), user);
			redisTemplate.boundHashOps(CommonConstants.CACHE_KEY.USER).put(session.getId(), user);
		} else {
			log.info("redis添加用户缓存：{}", session);
		}
	}
	public static void removeUser(HttpServletRequest request, RedisTemplate redisTemplate) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			log.info("redis清除用户缓存：{}", session.getId());
			redisTemplate.boundHashOps(CommonConstants.CACHE_KEY.USER).delete(session.getId());
		} else {
			log.info("redis清除用户缓存：{}", session);
		}
	}
	/***
	* @Title: getCacheBean
	* @Param: [redisTemplate, prefix, clazz]
	* @description: 从缓存中获取缓存数据模型
	*/
	public static <T> T getCacheBean(RedisTemplate redisTemplate, String key) {
		try {
			return (T) redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
