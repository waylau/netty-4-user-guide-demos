/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.codec;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * My Codec.
 * 
 * @since 1.0.0 2019年12月17日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MyCodec extends CombinedChannelDuplexHandler<MyDecoder, MyEncoder> {
	public MyCodec() {
		super(new MyDecoder(), new MyEncoder());
	}

}
