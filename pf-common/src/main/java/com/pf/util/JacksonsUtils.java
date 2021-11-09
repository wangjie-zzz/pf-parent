package com.pf.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pf.base.ApiException;

import java.io.IOException;

/**
 *
 * @功能描述：json类型数据转换
 * @修改日志：
 */
public final class JacksonsUtils {
	
    private static ObjectMapper mapper;
    
    private static JsonFactory jsonFactory = new JsonFactory();
    
    static {
    	mapper = new ObjectMapper();
    	// 对于空的对象转json的时候不抛出错误
    	mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用遇到未知属性抛出异常
    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    	mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 低层级配置
    	// 该特性决定parser将是否允许解析使用Java/C++ 样式的注释（包括'/'+'*' 和'//' 变量）。 由于JSON标准说明书上面没有提到注释是否是合法的组成，所以这是一个非标准的特性；
    	mapper.configure(JsonParser.Feature.ALLOW_COMMENTS,true);
    	// 这个特性决定parser是否将允许使用非双引号属性名字， （这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
    	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
    	// 该特性决定parser是否允许单引号来包住属性名称和字符串值。
    	mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
    
    /**
    * @Title: writeValueAsString
    * @Param: [value]
    * @description: 对象转换为JSON字符串
    * @return: java.lang.String
    * @throws:
    */
    public static String writeValueAsString(Object value) {
    	try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new ApiException(e);
		}
    }

    /***
    * @Title: writeValueAsBytes
    * @Param: [value]
    * @description: 对象转换为字节流
    * @return: byte[]
    * @throws:
    */
	public static byte[] writeValueAsBytes(Object value) {
		try {
			return mapper.writeValueAsBytes(value);
		} catch (JsonProcessingException e) {
			throw new ApiException(e);
		}
	}
    
    /**
    * @Title: readValue
    * @Param: [content, valueType]
    * @description: JSON转对象
    * @return: T
    * @throws:
    */
    public static <T> T copy(Object obj, Class<T> valueType) {
    	try {
			return mapper.readValue(writeValueAsString(obj), valueType);
		} catch (IOException e) {
			throw new ApiException(e);
		}
    }
    /**
    * @Title: readValue
    * @Param: [content, valueType]
    * @description: JSON转对象
    * @return: T
    * @throws:
    */
    public static <T> T readValue(String content, Class<T> valueType) {
    	try {
			return mapper.readValue(content, valueType);
		} catch (IOException e) {
			throw new ApiException(e);
		}
    }
    
    public static <T> T readValue(byte[] bytes, Class<T> valueType) {
    	try {
    		JsonParser jsonParser = jsonFactory.createParser(bytes);
			return mapper.readValue(jsonParser, valueType);
		} catch (IOException e) {
			throw new ApiException(e);
		}
    }
    
    /**
     * 
     * @Title: readValue
     * @Description: 
     * @param content
     * @param valueTypeRef
     * @return T
     * @modifyLog：
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T readValue(String content, TypeReference valueTypeRef) {
    	try {
			return (T)mapper.readValue(content, valueTypeRef);
		} catch (IOException e) {
			throw new ApiException(e);
		}
    }
    
    @SuppressWarnings({ "unchecked"})
    public static <T> T readValue(String content, Class<?> parametrized, Class<?>... parameterClasses) {
    	try {
    		JavaType javaType = getCollect(parametrized, parameterClasses);
    		return (T)mapper.readValue(content, javaType);
		} catch (IOException e) {
			throw new ApiException(e);
		}
    }
    
    /**
     * 
     * @Title: getCollect
     * @Description: 
     * @param parametrized
     * @param parameterClasses
     * @modifyLog：
     */
    public static JavaType getCollect(Class<?> parametrized, Class<?>... parameterClasses) {
    	return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }
}
