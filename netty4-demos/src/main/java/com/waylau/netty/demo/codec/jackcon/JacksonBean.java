package com.waylau.netty.demo.codec.jackcon;

/**
 * 说明：一个用户 POJO
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月9日 
 */
public class JacksonBean {
 
	private int age;
	private String name;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 */
	public JacksonBean() {
		// TODO Auto-generated constructor stub
	}

}
