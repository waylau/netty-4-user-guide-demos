/**
 * 
 */
package com.waylau.netty.demo.protocol;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 说明：自定义协议客户端性能测试
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日
 */
public class ProtocolClientTest {

	private static final int POOL_SIZE_SEND = 100;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
 
		Executor executor = Executors.newFixedThreadPool(POOL_SIZE_SEND);
		for (int i = 0; i < POOL_SIZE_SEND; i++) {
			executor.execute(new ClientTask());
			Thread.sleep(100);
		}

	}

}
