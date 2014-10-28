
package com.bruce.gogo.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 * Jackson的简单封装.
 * 
 * @author liaojing
 */
public class JsonUtil {

	private ObjectMapper mapper;
	
	public JsonUtil(Inclusion inclusion) {
		mapper = new ObjectMapper();
		//设置输出包含的属性
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.getDeserializationConfig().set(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 创建输出全部属性到Json字符串的Binder.
	 */
	public static JsonUtil buildNormalBinder() {
		return new JsonUtil(Inclusion.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Binder.
	 */
	public static JsonUtil buildNonNullBinder() {
		return new JsonUtil(Inclusion.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Binder.
	 */
	public static JsonUtil buildNonDefaultBinder() {
		return new JsonUtil(Inclusion.NON_DEFAULT);
	}

	/**
	 * 如果对象为Null,返回"null".
	 * 如果集合为空集合,返回"[]".
	 */
	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
	
	 /**
     * 将json串从action输出到页面
     * 
     * @param String
     *            str
     * @return String
     */
	public static void doAjaxReturn(String obj,HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");// application/xml,application/text
		try {
			PrintWriter pw = response.getWriter();
			pw.write(obj == null ? "null" : obj);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
