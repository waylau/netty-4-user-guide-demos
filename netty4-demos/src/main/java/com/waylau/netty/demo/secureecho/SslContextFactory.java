/**
 * Welcome to https://waylau.com
 */
package com.waylau.netty.demo.secureecho;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * SslContext Factory.
 * 
 * @since 1.0.0 2019年12月25日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public final class SslContextFactory {
	private static final String PROTOCOL = "TLS";

	private static SSLContext SERVER_CONTEXT;// 服务器上下文

	private static SSLContext CLIENT_CONTEXT;// 客户端上下文

	public static SSLContext getServerContext(String pkPath, String caPath, String password) {
		if (SERVER_CONTEXT != null)
			return SERVER_CONTEXT;

		SERVER_CONTEXT = getContext(pkPath, caPath, password);

		return SERVER_CONTEXT;
	}

	public static SSLContext getClientContextgetContext(String pkPath, String caPath, String password) {
		if (CLIENT_CONTEXT != null)
			return CLIENT_CONTEXT;

		CLIENT_CONTEXT = getContext(pkPath, caPath, password);

		return CLIENT_CONTEXT;
	}

	public static SSLContext getContext(String pkPath, String caPath, String password) {
		if (CLIENT_CONTEXT != null)
			return CLIENT_CONTEXT;

		InputStream in = null;
		InputStream tIN = null;
		try {
			KeyManagerFactory kmf = null;
			if (pkPath != null) {
				KeyStore ks = KeyStore.getInstance("JKS");
				in = new FileInputStream(pkPath);
				ks.load(in, password.toCharArray());
				kmf = KeyManagerFactory.getInstance("SunX509");
				kmf.init(ks, password.toCharArray());
			}

			TrustManagerFactory tf = null;
			if (caPath != null) {
				KeyStore tks = KeyStore.getInstance("JKS");
				tIN = new FileInputStream(caPath);
				tks.load(tIN, password.toCharArray());
				tf = TrustManagerFactory.getInstance("SunX509");
				tf.init(tks);
			}

			CLIENT_CONTEXT = SSLContext.getInstance(PROTOCOL);
			CLIENT_CONTEXT.init(kmf.getKeyManagers(), tf.getTrustManagers(), null);

		} catch (Exception e) {
			throw new Error("Failed to initialize the client-side SSLContext");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}

			if (tIN != null) {
				try {
					tIN.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				tIN = null;
			}
		}

		return CLIENT_CONTEXT;
	}

}
