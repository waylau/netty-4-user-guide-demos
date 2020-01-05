package com.waylau.netty.demo.encoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * AbsIntegerEncoder Test.
 * 
 * @since 1.0.0 2020年1月4日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
class AbsIntegerEncoderTest {
	
	@Test
	void testEncoded() {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 1; i < 10; i++) {
			buf.writeInt(i * -1); //（1）
		}
		
		EmbeddedChannel channel = 
				new EmbeddedChannel(new AbsIntegerEncoder()); //（2）
		assertTrue(channel.writeOutbound(buf)); //（3）
		assertTrue(channel.finish()); //（4）
		
		// 读字节
		for (int i = 1; i < 10; i++) {
			assertEquals(Integer.valueOf(i+""), 
					channel.readOutbound()); //（5）
		}
		assertNull(channel.readOutbound());
	}
}
