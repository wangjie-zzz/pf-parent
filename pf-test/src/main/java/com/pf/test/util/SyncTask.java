package com.pf.test.util;

import com.pf.test.config.ThreadPoolConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：线程池同步执行
 * @author wangjie
 * @date 2020年3月21日 上午12:51:16
 * @修改日志：
 */
@Slf4j
public class SyncTask<T, E> implements Runnable {

	@Setter
	private List<T> resources;
	@Getter
	private List<E> results;
	private CountDownLatch countDownLatch;
	private ThreadLocal<T> tThreadLocal = new ThreadLocal<>();

	@Resource(name = "calcThreadPool")
	private ThreadPoolTaskExecutor calcThreadPool;

	protected void execute(SyncTask task) {
		int count = this.resources.size();
		if(count > ThreadPoolConfig.CorePoolSize) {
			count = ThreadPoolConfig.CorePoolSize;
		}
		this.countDownLatch = new CountDownLatch(count);
		for(int i=0; i<count; i++) {
			if(task == null) task = this;
			calcThreadPool.execute(task);
		}

		try {
			countDownLatch.await(ThreadPoolConfig.AWAIT_TIME, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {

			// 关闭线程池
//			threadPoolTaskExecutor.shutdown();
		}
	}

	@Override
	public void run() {
		T data = this.getResource();
		boolean over = false;
		while(!over) {
			try {
				if(data != null) {
					log.info(data+"-------handler-start--------");
					handler(data);
					log.info(data+"-------handler-finish--------");
				}
				/*可能等待队列中还有等待的任务，
				 * 因此不能用data!=null判断是否可以latch.countDown()
				 * 此处用结果集中的条数判断
				 */
				if(this.resources.isEmpty()) { // 资源已全部处理完成
					over = true;
					log.info("---NoResources-handler---"+Thread.currentThread().getName());
				}
			} catch (Exception e) {
				over = true;
				e.printStackTrace();
				log.error(e.getMessage(), e);
			} finally {
				tThreadLocal.remove();
				if(over) {
					countDownLatch.countDown();
					log.info("---thread-countDownLatch-notWhile---"+Thread.currentThread().getName());
				}
			}
		}
	}
	protected boolean handler(T t) {
		tThreadLocal.get();
		return true;
	}
	private synchronized T getResource() {
		T data = null;
		if(!CollectionUtils.isEmpty(this.resources)) {
			data = this.resources.get(0);
			this.resources.remove(0);
			tThreadLocal.set(data);
		}
		return data;
	}
	protected void addResult(E e) {
		synchronized(this){
			if(this.results == null) results = new ArrayList<>();
		}
		this.results.add(e);
	}
}
