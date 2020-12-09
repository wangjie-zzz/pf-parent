package com.pf.plat.config;

import com.pf.bean.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @功能描述：生成雪花算法实例
 * @修改日志：
 */
@Configuration
@Slf4j
public class IdWorkerConfig {

	@Bean
	public SnowflakeIdWorker idWorker() {
		SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(SnowflakeIdWorker.SYSTEM_ID[0], SnowflakeIdWorker.SYSTEM_ID[1]);
		SnowflakeIdWorker.setInstance(snowflakeIdWorker);
		return snowflakeIdWorker;
	}
}
