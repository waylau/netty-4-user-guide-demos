package com.waylau.netty.demo.protocol;
 

/**
 * 说明：协议消息头
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月4日 
 */
public class ProtocolHeader{
 
	private byte magic; 	// 魔数
	private byte msgType;	// 消息类型
	private short reserve;	// 保留字
	private short sn;		// 序列号
	private int len;		// 长度
	
	public byte getMagic() {
		return magic;
	}
	public void setMagic(byte magic) {
		this.magic = magic;
	}
	public byte getMsgType() {
		return msgType;
	}
	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public short getSn() {
		return sn;
	}
	public void setSn(short sn) {
		this.sn = sn;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public ProtocolHeader() {
	}
	/**
	 * 
	 */
	public ProtocolHeader(byte magic, byte msgType,short reserve,short sn,int len) {
		this.magic = magic;
		this.msgType = msgType;
		this.reserve = reserve;
		this.sn = sn;
		this.len = len;
	}

}
