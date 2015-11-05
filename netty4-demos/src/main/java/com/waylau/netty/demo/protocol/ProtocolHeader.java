/**
 * 
 */
package com.waylau.netty.demo.protocol;

/**
 * 说明：协议消息头
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月4日 
 */
public class ProtocolHeader {
	
	private byte frameHead; // 帧头
	private byte msgType;	// 消息类型
	private byte reserve;	// 保留字
	private short sn;		// 序列号
	private short len;		// 长度
	/**
	 * 
	 */
	public ProtocolHeader() {
		// TODO Auto-generated constructor stub
	}

	public byte getFrameHead() {
		return frameHead;
	}
	public void setFrameHead(byte frameHead) {
		this.frameHead = frameHead;
	}
	public byte getMsgType() {
		return msgType;
	}
	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}
	public byte getReserve() {
		return reserve;
	}
	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}
	public short getSn() {
		return sn;
	}
	public void setSn(short sn) {
		this.sn = sn;
	}
	public short getLen() {
		return len;
	}
	public void setLen(short len) {
		this.len = len;
	}
}
