//package com..mall.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
///**
// * 功能描述：线程池同步执行
// * @author wangjie
// * @date 2020年3月21日 上午12:51:16
// * @修改日志：
// */
//@Slf4j
//@Service
//public class Test {
//
////	@Resource(name = "calcThreadPool")
//	private static ThreadPoolTaskExecutor calcThreadPool = ThreadPoolConfig.CalcThreadPool();
//
//	public static void main(String[] args) {
//		AsyncTask<Integer, String> task = new AsyncTask<>(null);
//		Future<String> ft = calcThreadPool.submit(task);
//		while (true) {
//			if(ft.isDone()) {
//				try {
//					String result = ft.get();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					e.printStackTrace();
//				}
//				break;
//			} else {
//				System.out.println("未完成。。。。。等待中。。。。。。");
//			}
//		}
//		System.out.println("结束。。。。。");
//	}
//}
//
//class AsyncTask<T, E> implements Callable<E> {
//	private List<T> resources;
//	public AsyncTask(List<T> list) {
//		this.resources = list;
//	}
//	@Override
//	public E call() throws Exception {
//		Thread.currentThread().sleep(10);
//		int i  = 1/0;
//		return null;
//	}
//}
