//package com..mall.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.stream.Collectors;
//
///**
// * 功能描述：线程池同步执行
// * @author wangjie
// * @date 2020年3月21日 上午12:51:16
// * @修改日志：
// */
//@Slf4j
//@Service
//public class Test2 {
//
////	@Resource(name = "calcThreadPool")
//	private static ThreadPoolTaskExecutor calcThreadPool = ThreadPoolConfig.CalcThreadPool();
//
//	public static void main(String[] args) {
//		AsyncTask2<String> task = new AsyncTask2<>();
////		AsyncTask3<String> task = new AsyncTask3<>();
//		for (int i=0; i<10010; i++) {
//			task.addResources(String.valueOf(i));
////			task.addResources("-----"+String.valueOf(i)+"-------");
//		}
//		System.out.println(">>>"+task.countResources());
//		for(int i=0; i<5; i++) {
//			calcThreadPool.execute(task);
//		}
//		System.out.println("start sleep...................");
//		try {
//			Thread.sleep( 1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
////		calcThreadPool.shutdown();
//		System.out.println("end sleep...................");
//		List<String> res = task.getResults();
////		BlockingQueue<String> res = task.getResults();
//		System.out.println(">>>"+res.size());
//		/*res.stream().sorted();
//		res.forEach(e -> {
//			System.out.println(e);
//		});*/
//		System.out.println("distint>>>"+res.stream().distinct().collect(Collectors.toList()).size());
////		Map<String, String> mapRes = res.stream().collect(Collectors.toMap(e -> e, Function.identity()));
////		System.out.println(mapRes.size());
//	}
//}
//
//class AsyncTask3<T> implements Runnable {
//	private BlockingQueue<T> resources;
//	private BlockingQueue<String> results;
//	public AsyncTask3() {
//		this.resources = new LinkedBlockingQueue<>(10000);
//		this.results = new LinkedBlockingQueue<>(10000);
//	}
//	public AsyncTask3(BlockingQueue<T> list) {
//		this.resources = list;
//	}
//	public boolean addResources(T t) {
//		return this.resources.offer(t);
//	}
//	public T getResources() { //throws InterruptedException
//		return this.resources.poll();
////		return this.resources.poll(60, TimeUnit.SECONDS);
//	}
//	public int countResources() {
//		return this.resources.size();
//	}
//
//	public BlockingQueue<String> getResults() {
//		return this.results;
//	}
//	@Override
//	public void run() {
//		while (true) {
//			T t = this.getResources();
//			if(t != null) {
//
//				this.results.offer(t+"---");
//			}else {
//				break;
//			}
//		}
//	}
//
//}
//
//class AsyncTask2<T> implements Runnable {
//
//	private List<T> resources;
//	private List<String> results;
//	public AsyncTask2() {
//		this.resources = new ArrayList<>();
//	}
//	public AsyncTask2(List<T> list) {
//		this.resources = list;
//	}
//	public boolean addResources(T t) {
//		return this.resources.add(t);
//	}
//	public List<String> getResults() {
//		return this.results;
//	}
//	public int countResources() {
//		return this.resources.size();
//	}
//	@Override
//	public void run() {
//		boolean over = false;
//		while (!over) {
//			T t = this.getResource();
//			if(t != null) {
//				String res = handler(t);
//				this.addResult(res);
//			}
//			if(this.resources.isEmpty()) {
//				over = true;
//				System.out.println(Thread.currentThread().getName()+"----over");
//			}
//		}
//	}
//	private String handler(T t) {
//		return t.toString()+"---";
//	}
//	public synchronized T getResource() {
//		T t = null;
//		if(!this.resources.isEmpty()) {
//			t = this.resources.get(0);
//			this.resources.remove(0);
//		}
//		return t;
//	}
//	public synchronized void addResult(String e) {
//		if(this.results == null) results = new ArrayList<>();
//		this.results.add(e);
//	}
//}
