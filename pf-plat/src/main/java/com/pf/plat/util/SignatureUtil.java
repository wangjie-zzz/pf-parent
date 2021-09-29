package com.pf.plat.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
public class SignatureUtil {

	//必要参数 SECRET_KEY
    private final String secretKey;
    private final String body;
    private final Map<String, String[]> params;
    private final String[] paths;
    private String hmacAlgorithm;

    private SignatureUtil(Builder builder) {
    	this.secretKey = builder.secretKey;
    	this.body = builder.body;
    	this.params = builder.params;
    	this.paths = builder.paths;
    	this.hmacAlgorithm = builder.hmacAlgorithm;
    }

    public static class Builder {
    	//必要参数
        private final String secretKey;

        //可选参数
        private String body;
        private Map<String, String[]> params;
        private String[] paths;
        private String hmacAlgorithm;

		public Builder(String secretKey) {
        	this.secretKey = secretKey;
        }

		public Builder body(String body) {
			this.body = body;
			return this;
		}

		public Builder params(Map<String, String[]> params) {
			this.params = params;
			return this;
		}

		public Builder paths(String[] paths) {
			this.paths = paths;
			return this;
		}

		public Builder hmacAlgorithm(String hmacAlgorithm) {
			this.hmacAlgorithm = hmacAlgorithm;
			return this;
		}

		public SignatureUtil build() {
            return new SignatureUtil(this);
        }
    }

    public String sign() {
    	if( StringUtils.isEmpty(this.hmacAlgorithm) ) {
    		hmacAlgorithm = HmacAlgorithms.HMAC_SHA_256.getName();
    	}
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(this.body)) {
        	log.info("\r\nbody:{}",body);
        	stringBuilder.append(body).append('#');
        }

        if (!CollectionUtils.isEmpty(this.params)) {
            params.entrySet()
	            .stream()
	            .sorted(Map.Entry.comparingByKey())
	            .forEach(paramEntry -> {
	                String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
	                log.info("\r\nparams-{}:{}", paramEntry.getKey(), paramValue);
	                stringBuilder.append(paramEntry.getKey()).append("=").append(paramValue).append('#');
	            });
        }

        if (ArrayUtils.isNotEmpty(this.paths)) {
            String pathValues = String.join(",", Arrays.stream(paths).sorted().toArray(String[]::new));
            log.info("\r\npathValues:{}",pathValues);
            stringBuilder.append(pathValues);
        }

        String createSign = new HmacUtils(hmacAlgorithm, this.secretKey).hmacHex(stringBuilder.toString());
        return createSign;
    }

    /**
     * 兼容低版本jdk
     */
    public String sign0() {
    	if( StringUtils.isEmpty(this.hmacAlgorithm) ) {
    		hmacAlgorithm = HmacAlgorithms.HMAC_SHA_256.getName();
    	}
        final StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(this.body)) {
        	log.info("\r\nbody:{}",body);
        	stringBuilder.append(body).append('#');
        }

        if (!CollectionUtils.isEmpty(this.params)) {
        	List<Map.Entry<String, String[]>> list = new ArrayList<Map.Entry<String, String[]>>(params.entrySet());
        	Collections.sort(list,new Comparator<Map.Entry<String, String[]>>() {
        		@Override
            	public int compare(Map.Entry<String,String[]> c1, Map.Entry<String,String[]> o2) {
            		return c1.getKey().compareTo(o2.getKey());
            	}
            });
        	for (Map.Entry<String, String[]> paramEntry : list) {
        		String[] values = paramEntry.getValue();
        		List<String> valueList = Arrays.asList(values);
        		Collections.sort(valueList,new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

                });
        		String paramValue = StringUtils.join(valueList, ",");
        		log.info("\r\nparams-{}:{}", paramEntry.getKey(), paramValue);
                stringBuilder.append(paramEntry.getKey()).append("=").append(paramValue).append('#');
			}

        }

        if (ArrayUtils.isNotEmpty(this.paths)) {
        	List<String> valueList = Arrays.asList(this.paths);
    		Collections.sort(valueList,new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}

            });
            String pathValues = StringUtils.join(valueList, ",");
            log.info("\r\npathValues:{}",pathValues);
            stringBuilder.append(pathValues);
        }

        String createSign = new HmacUtils(hmacAlgorithm, this.secretKey).hmacHex(stringBuilder.toString());
        return createSign;
    }
}
