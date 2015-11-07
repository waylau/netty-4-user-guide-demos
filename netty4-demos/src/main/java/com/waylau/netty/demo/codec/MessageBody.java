/**
 * 
 */
package com.waylau.netty.demo.codec;

import java.io.Serializable;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月7日 
 */
public class MessageBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1362794425307249946L;
 
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
 
	private Object message;
	/**
	 * 
	 */
	public MessageBody() {
		// TODO Auto-generated constructor stub
	}

}
