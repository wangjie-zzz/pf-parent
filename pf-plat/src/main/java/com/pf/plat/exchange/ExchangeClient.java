package com.pf.plat.exchange;


import com.pf.plat.model.domain.RequestTempFile;
import com.pf.plat.model.domain.ResponseTempFile;

public interface ExchangeClient {

	String sendData(String data) throws Exception;
	
	ResponseTempFile download(String data) throws Exception;
	
	String upload(String data, RequestTempFile requestTempFile) throws Exception;
}
