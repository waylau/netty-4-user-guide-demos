package com.waylau.netty.demo.codec;

/**
 * Message Type.
 * 
 * @since 1.0.0 2019年12月16日
 * @author <a href="https://waylau.com">Way Lau</a>
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
