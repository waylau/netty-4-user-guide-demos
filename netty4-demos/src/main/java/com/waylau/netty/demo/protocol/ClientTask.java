package com.waylau.netty.demo.protocol;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月5日 
 */
public class ClientTask implements Runnable {

	/**
	 * 
	 */
	public ClientTask() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ProtocolClient client = new ProtocolClient("localhost", 8082);
			
			client.run();
 
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
