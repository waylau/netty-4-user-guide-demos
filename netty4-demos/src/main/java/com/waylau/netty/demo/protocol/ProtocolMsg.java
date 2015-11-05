/**
 * 
 */
package com.waylau.netty.demo.protocol;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class ProtocolMsg {
	

	private ProtocolHeader protocolHeader = new ProtocolHeader();
	private ProtocolBody protocolBody = new ProtocolBody();
	
	/**
	 * 
	 */
	public ProtocolMsg() {
		// TODO Auto-generated constructor stub
	}
	
	public ProtocolHeader getProtocolHeader() {
		return protocolHeader;
	}

	public void setProtocolHeader(ProtocolHeader protocolHeader) {
		this.protocolHeader = protocolHeader;
	}

	public ProtocolBody getProtocolBody() {
		return protocolBody;
	}

	public void setProtocolBody(ProtocolBody protocolBody) {
		this.protocolBody = protocolBody;
	}

}
