/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.decoder;

import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * My LineBasedFrameDecoder.
 * 
 * @since 1.0.0 2019年12月12日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyLineBasedFrameDecoder extends LineBasedFrameDecoder {

	private final static int MAX_LENGTH = 1024; // 帧的最大长度

	public MyLineBasedFrameDecoder() {
		super(MAX_LENGTH);
	}

}
