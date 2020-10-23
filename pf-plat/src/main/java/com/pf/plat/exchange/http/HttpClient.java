package com.pf.plat.exchange.http;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pf.enums.DataFormatsEnum;
import com.pf.util.HttpHeaderUtil;
import com.pf.util.JacksonsUtils;
import com.pf.plat.exchange.ExchangeClient;
import com.pf.plat.exchange.http.ssl.HttpsClientRequestFactory;
import com.pf.plat.model.domain.RequestTempFile;
import com.pf.plat.model.domain.ResponseTempFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpClient implements ExchangeClient {
	
	//必要参数
    private final String url;
    private String requestMethod;
    private DataFormatsEnum requestDataFormatsEnum;
    private DataFormatsEnum responseDataFormatsEnum; 
    private Boolean ssl;
    private Map<String, String> headers;
    private Map<String, String[]> urlParams;
    private String charsets;
    
    private HttpClient(Builder builder) {
    	this.url = StringUtils.isNotBlank(builder.url)? builder.url: "";
    	this.requestMethod = builder.requestMethod != null ? builder.requestMethod: HttpMethod.POST.name();
    	this.requestDataFormatsEnum = builder.requestDataFormatsEnum != null ? builder.requestDataFormatsEnum: DataFormatsEnum.JSON;
    	this.responseDataFormatsEnum = builder.responseDataFormatsEnum != null ? builder.responseDataFormatsEnum: DataFormatsEnum.JSON;
    	this.ssl = builder.ssl != null ? builder.ssl: false;
    	this.headers = builder.headers != null && !CollectionUtils.isEmpty(builder.headers) ? builder.headers: Maps.newHashMap();
    	this.urlParams = builder.urlParams != null && !CollectionUtils.isEmpty(builder.urlParams) ? builder.urlParams: Maps.newHashMap();
    	this.charsets = StringUtils.isNotBlank(builder.charsets) ? builder.charsets: StandardCharsets.UTF_8.name();
    }
    
    public static class Builder {
    	
    	//必要参数
        private final String url;
        //可选参数
        private String requestMethod;
        private DataFormatsEnum requestDataFormatsEnum;
        private DataFormatsEnum responseDataFormatsEnum;
        private Boolean ssl;
        private Map<String, String> headers;
        private Map<String, String[]> urlParams;
        private String charsets;
        
        public Builder(String url) {
        	this.url = url;
        }

        public Builder requestMethod(String requestMethod) {
			this.requestMethod = requestMethod;
			return this;
		}
        
		public Builder requestDataFormatsEnum(DataFormatsEnum requestDataFormatsEnum) {
			this.requestDataFormatsEnum = requestDataFormatsEnum;
			return this;
		}
		
		public Builder responseDataFormatsEnum(DataFormatsEnum responseDataFormatsEnum) {
			this.responseDataFormatsEnum = responseDataFormatsEnum;
			return this;
		}
		
		public Builder ssl(Boolean ssl) {
			this.ssl = ssl;
			return this;
		}
		
		public Builder headers(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}
		
		public Builder urlParams(Map<String, String[]> urlParams) {
			this.urlParams = urlParams;
			return this;
		}
		
		public Builder charsets(String charsets) {
			this.charsets = charsets;
			return this;
		}
		
		public HttpClient build() {
            return new HttpClient(this);
        }
    }

	@Override
	public String sendData(String data) throws Exception {
		RestTemplate restTemplate = this.getInstance();
        
		HttpEntity<Object> request = new HttpEntity<Object>(this.initBody(data,null), initHttpHeaders());
		URI uri = this.initUri();
		HttpMethod httpMethod = HttpMethod.resolve(this.requestMethod);
		log.info("\r\n转发地址:\r\n{}", this.url);
		log.info("\r\n传输内容*****:\r\n{}",data);
		ResponseEntity<String> response = restTemplate.exchange(uri, httpMethod, request, String.class);
		log.info("\r\n请求发送成功:\r\n{}",response.getBody());
		return response.getBody();
	}

	@Override
	public ResponseTempFile download(String data) throws Exception {
		RestTemplate restTemplate = this.getInstance();
        
		HttpEntity<Object> request = new HttpEntity<Object>(this.initBody(data, null), initHttpHeaders());
		URI uri = this.initUri();
		HttpMethod httpMethod = HttpMethod.resolve(this.requestMethod);
		try {
			log.info("\r\n转发地址:\r\n{}", this.url);
			ResponseEntity<byte[]> response = restTemplate.exchange(uri, httpMethod,request, byte[].class);
			
			ResponseTempFile tempFile = new ResponseTempFile();
			tempFile.setFileBytes(response.getBody());
			
	        log.info(">> {}", response.getStatusCodeValue());
	        String fileName = URLDecoder.decode(response.getHeaders().getContentDisposition().getFilename(),"UTF-8");
	        log.info(">> {}", response.getHeaders().getContentType().getSubtype());
	        String baseName = String.valueOf( System.currentTimeMillis() );
	        if( StringUtils.isNotBlank(fileName) ) {
	        	baseName = FilenameUtils.getBaseName(fileName);
	        }
	        tempFile.setFilename(baseName + "." + response.getHeaders().getContentType().getSubtype());
	        tempFile.setBaseName(baseName);
	        tempFile.setExtension(response.getHeaders().getContentType().getSubtype());
	        return tempFile;
		} catch (Exception e) {
			e.printStackTrace();
//			Shift.fatalThrow(SysStatusCode.PLAT_DATA_SEND_ERROR);
		}
		return null;
	}

	@Override
	public String upload(String data, RequestTempFile requestTempFile) throws Exception {
		RestTemplate restTemplate = this.getInstance();
        
		HttpEntity<Object> request = new HttpEntity<Object>(this.initBody(data, requestTempFile), initHttpHeaders());
		URI uri = this.initUri();
		HttpMethod httpMethod = HttpMethod.resolve(this.requestMethod);
		try {
			log.info("\r\n转发地址:\r\n{}", this.url);
			ResponseEntity<String> response = restTemplate.exchange(uri, httpMethod, request, String.class);
			log.info("\r\n请求发送成功:\r\n{}",response.getBody());
			return response.getBody();
		} catch (Exception e) {
			log.error("上传文件错误：",e);
//			Shift.fatalThrow(SysStatusCode.PLAT_DATA_SEND_ERROR);
		}
		return null;
	}

	private Object initBody(Object data, RequestTempFile requestTempFile) throws Exception {
		if( data instanceof String ) {
			Map<String, Object> map = null;
			switch ( this.requestDataFormatsEnum ) {
				case FORM_URLENCODED:
					MultiValueMap<String, Object> formUrlencodedParameters = new LinkedMultiValueMap<String, Object>();
					map = JacksonsUtils.getMapper().readValue((String)data, Map.class);
					for (Entry<String, Object> entry : map.entrySet()) {
						formUrlencodedParameters.add(entry.getKey(), entry.getValue());
					}
					return formUrlencodedParameters;
				case FORM_DATA:
					MultiValueMap<String, Object> formDataParameters = new LinkedMultiValueMap<String, Object>();
					map = JacksonsUtils.getMapper().readValue((String)data, Map.class);
					for (Entry<String, Object> entry : map.entrySet()) {
						formDataParameters.add(entry.getKey(), entry.getValue());
					}
					if( requestTempFile != null && StringUtils.isNotBlank(requestTempFile.getFileParameterName()) && requestTempFile.getFiles() != null ) {
//						File[] files = FileUtil.multipartFileTransferToFile( requestTempFile.getFiles() );
//						for( File file: files ) {
//							formDataParameters.add( requestTempFile.getFileParameterName(), new FileSystemResource(file));
//						}
					}
					return formDataParameters;
				case JSON:
					return data;
				default:
					return data;
			}
		}
		return data;
	}

	private RestTemplate getInstance() {
		RestTemplate restTemplate;
		if( this.ssl ) {
			restTemplate = new RestTemplate(new HttpsClientRequestFactory());
		} else {
			restTemplate = new RestTemplate();
		}
		
		//对中文格式数据进行处理
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if(httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName(this.charsets));
                break;
            }
        }
	    return restTemplate;
	}

	private HttpHeaders initHttpHeaders(){
		HttpHeaders httpHeaders = HttpHeaderUtil.initHttpHeaders(requestDataFormatsEnum, responseDataFormatsEnum);
    	/*自定义消息头*/
    	for (Entry<String, String> entry : headers.entrySet()) {
    		httpHeaders.set(entry.getKey(), entry.getValue());
    	}
    	return httpHeaders;
    }

	private URI initUri() {
		StringBuffer stringBuffer = new StringBuffer(this.url);
		final List<String> urlParamsList = Lists.newArrayList();
		this.urlParams.entrySet()
        .stream()
        .sorted(Entry.comparingByKey())
        .forEach(paramEntry -> {
            String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
            String paramUri = paramEntry.getKey() + "=" + paramValue;
            urlParamsList.add(paramUri);
        });
		if( !CollectionUtils.isEmpty(urlParamsList) ) {
			String urlParamsStr = "?" + String.join("&", urlParamsList);
			stringBuffer.append(urlParamsStr);
		}
		URI uri = URI.create(stringBuffer.toString());
		return uri;
	}

}
