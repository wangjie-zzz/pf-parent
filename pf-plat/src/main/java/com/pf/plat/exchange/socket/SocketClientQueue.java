package com.pf.plat.exchange.socket;

import com.pf.plat.exchange.ExchangeClient;
import com.pf.plat.model.domain.RequestTempFile;
import com.pf.plat.model.domain.ResponseTempFile;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class SocketClientQueue implements ExchangeClient {
	
	private BlockingQueue<SocketClient> socketClientQueue = new LinkedBlockingQueue<SocketClient>();

	@Override
	public String sendData(String data) throws Exception {
		log.info("发送指令数据打印*****"+data);
		String response = null;
		SocketClient socketClient = this.socketClientQueue.take();
		try {
			response = socketClient.sendData(data);
		}catch(Exception e){
			log.error("\r\n third发送失败"+data,e);
		}finally {
			this.socketClientQueue.offer(socketClient);
		}
		return response;
	}

	@Override
	public ResponseTempFile download(String data) throws Exception {
		return null;
	}

	@Override
	public String upload(String data, RequestTempFile requestTempFile) throws Exception {
		return null;
	}
	
	public void add(SocketClient socketClient) {
		this.socketClientQueue.offer(socketClient);
	}
	
	public int getQueueSize() {
		return this.socketClientQueue.size();
	}

}
