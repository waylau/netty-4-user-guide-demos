/**
 * 
 */
package com.waylau.netty.demo.protocol;

/**
 * 说明：消息类型
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public enum MsgType {
	EMGW_LOGIN_REQ((byte) 0x00),
	EMGW_LOGIN_RES((byte) 0x01);
	
	private byte value;

	public byte getValue() {
		return value;
	}

	private MsgType(byte value) {
		this.value = value;
	}
}
