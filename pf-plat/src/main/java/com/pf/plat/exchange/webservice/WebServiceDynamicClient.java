package com.pf.plat.exchange.webservice;

import com.pf.util.JacksonsUtils;
import com.pf.plat.exchange.ExchangeClient;
import com.pf.plat.model.domain.RequestTempFile;
import com.pf.plat.model.domain.ResponseTempFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.nio.charset.StandardCharsets;

@Slf4j
public class WebServiceDynamicClient implements ExchangeClient {
	
	private final String method;
	private final String url;
    private String charsets;
    
    private WebServiceDynamicClient(Builder builder) {
    	this.method = StringUtils.isNotBlank(builder.method)? builder.method: "";
    	this.url = StringUtils.isNotBlank(builder.url)? builder.url: "";
    	this.charsets = StringUtils.isNotBlank(builder.charsets) ? builder.charsets: StandardCharsets.UTF_8.name();
    }
    
    public static class Builder {
    	
    	private final String method;
    	private final String url;
        private String charsets;
        
        public Builder(String method, String url) {
        	this.method = method;
        	this.url = url;
        }
        
		public Builder charsets(String charsets) {
			this.charsets = charsets;
			return this;
		}
		
		public WebServiceDynamicClient build() {
            return new WebServiceDynamicClient(this);
        }
    }

	@Override
	public String sendData(String data) {
		JaxWsDynamicClientFactory jaxWsDynamicClientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = jaxWsDynamicClientFactory.createClient(this.url);
        //设置超时单位为毫秒  
        HTTPConduit http = (HTTPConduit) client.getConduit();        
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();        
        httpClientPolicy.setConnectionTimeout(5000);  //连接超时      
        httpClientPolicy.setAllowChunking(false);    //取消块编码   
        httpClientPolicy.setReceiveTimeout(20000);     //响应超时  
        http.setClient(httpClientPolicy);  
        
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects = null;
        try {
            objects = client.invoke(this.method, data);
            if( objects.length > 0 ) {
            	log.info("\r\n返回数据：{}", JacksonsUtils.writeValueAsString(objects[0]) );
            	return JacksonsUtils.writeValueAsString(objects[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Shift.fatalThrow(SysStatusCode.PLAT_DATA_SEND_ERROR);
        }
        return null;
	}

	@Override
	public ResponseTempFile download(String data) throws Exception {
		return null;
	}

	@Override
	public String upload(String data, RequestTempFile requestTempFile) throws Exception {
		return null;
	}
}
