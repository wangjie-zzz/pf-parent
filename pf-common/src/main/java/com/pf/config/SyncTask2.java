//package com..mall.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
///**
// * 功能描述：线程池同步执行
// * @author wangjie
// * @date 2020年3月21日 上午12:51:16
// * @修改日志：
// */
//@Slf4j
//@Service
//public class SyncTask2<T, E> implements Runnable {
//	private final static Integer MAX_VALUE = 10000;
//	private BlockingQueue<T> resources;
//	private BlockingQueue<E> results;
//
//	@Resource(name = "calcThreadPool")
//	private ThreadPoolTaskExecutor calcThreadPool;
//	protected CountDownLatch countDownLatch;
//
//	public void execute(SyncTask2 task) {
//		int count = this.resources.size();
//		if(count > ThreadPoolConfig.CorePoolSize) {
//			count = ThreadPoolConfig.CorePoolSize;
//		}
//		this.countDownLatch = new CountDownLatch(count);
//		for(int i=0; i<count; i++) {
//			if(task == null) task = this;
//			calcThreadPool.execute(task);
//		}
//
//		try {
//			countDownLatch.await(ThreadPoolConfig.AWAIT_TIME, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			log.error(e.getMessage(), e);
//		} finally {
//
//			// 关闭线程池
////			threadPoolTaskExecutor.shutdown();
//		}
//	}
//
//	@Override
//	public void run() {
//		boolean over = false;
//		while(!over) {
//			//取走BlockingQueue里排在首位的对象,取不到时返回null;
//			T data = this.resources.poll();
//			try {
//				if(data != null) {
//					log.info(data+"-------handler-start--------");
//					handler(data);
//					log.info(data+"-------handler-finish--------");
//				}
//				/*可能等待队列中还有等待的任务，
//				 * 因此不能用data!=null判断是否可以latch.countDown()
//				 * 此处用结果集中的条数判断
//				 */
//				if(this.resources.isEmpty()) { // 资源已全部处理完成
//					over = true;
//					log.info("---NoResources-handler---"+Thread.currentThread().getName());
//				}
//			} catch (Exception e) {
//				over = true;
//				e.printStackTrace();
//				log.error(e.getMessage(), e);
//			} finally {
//				if(over) {
//					countDownLatch.countDown();
//					log.info("---thread-countDownLatch-notWhile---"+Thread.currentThread().getName());
//				}
//			}
//		}
//	}
//	/*
//	* 处理数据
//	* */
//	public boolean handler(T t) {
//		return true;
//	}
//	/*
//	* 处理完成数据后添加至结果集
//	* */
//	public void addResult(E e) {
//		if(this.results == null) results = new LinkedBlockingQueue<>(MAX_VALUE);
//		//如果BlockingQueue可以容纳,则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
//		this.results.offer(e);
//	}
//
//	public void setResources (List<T> list) {
//		this.resources = new LinkedBlockingQueue<>(MAX_VALUE);
//		list.forEach(e -> {
//			//如果BlockingQueue可以容纳,则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
//			this.resources.offer(e);
//		});
//	}
//	public List<E> getResults() {
//		if(this.results == null) return null;
//		return new ArrayList<>(this.results);
//	}
//}
