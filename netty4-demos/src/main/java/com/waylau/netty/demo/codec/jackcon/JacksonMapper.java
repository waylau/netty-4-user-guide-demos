package com.waylau.netty.demo.codec.jackcon;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 说明：ObjectMapper 单例。create once, reuse
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月9日
 */
public class JacksonMapper {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 *  create once, reuse
	 * @return ObjectMapper 单例
	 */
	public static ObjectMapper getInstance() {

		return MAPPER;
	}

}
