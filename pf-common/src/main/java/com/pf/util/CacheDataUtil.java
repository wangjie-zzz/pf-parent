package com.pf.util;

import com.pf.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

/***
* @Title:
* @Param:
* @description: 获取用户缓存
*/
@Slf4j
public class CacheDataUtil {


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

	/***
	* @Title: getUserCacheBean
	* @Param: [redisTemplate, prefix]
	* @description: 根据消息头的标识从缓存中获取缓存数据模型
	*/
	public static <T> T getUserCacheBean(RedisTemplate redisTemplate) {
		String userIdentity = HttpHeaderUtil.getUserIdentity();
		if( !StringUtils.isEmpty(userIdentity) ){
			String key = CommonConstants.CACHE_KEY.SYS_USER_INFO_KEY_PREFIX + userIdentity;
			return getCacheBean(redisTemplate, key);
		}
		return null;
	}

}
