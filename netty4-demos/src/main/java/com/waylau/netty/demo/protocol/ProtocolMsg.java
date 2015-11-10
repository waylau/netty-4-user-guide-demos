/**
 * 
 */
package com.waylau.netty.demo.protocol;

import java.io.Serializable;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class ProtocolMsg implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2997057445525218441L;
	private ProtocolHeader protocolHeader = new ProtocolHeader();
	//private ProtocolBody protocolBody = new ProtocolBody();
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String body = "";
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

//	public ProtocolBody getProtocolBody() {
//		return protocolBody;
//	}
//
//	public void setProtocolBody(ProtocolBody protocolBody) {
//		this.protocolBody = protocolBody;
//	}

}
