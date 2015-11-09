package com.waylau.netty.demo.codec.jackcon;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月8日
 */
public class JsonGeneratorDemo {

	private static final String FILE_PATH = "d:\\user.json";

	/**
	 * 
	 */
	public JsonGeneratorDemo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {

			JsonFactory jfactory = new JsonFactory();
 
			JsonGenerator jGenerator = jfactory.createJsonGenerator(new File(
					FILE_PATH), JsonEncoding.UTF8);
			jGenerator.writeStartObject(); // {

			jGenerator.writeStringField("name", "mkyong"); // "name" : "mkyong"
			jGenerator.writeNumberField("age", 29); // "age" : 29

			jGenerator.writeFieldName("messages"); // "messages" :
			jGenerator.writeStartArray(); // [

			jGenerator.writeString("msg 1"); // "msg 1"
			jGenerator.writeString("msg 2"); // "msg 2"
			jGenerator.writeString("msg 3"); // "msg 3"

			jGenerator.writeEndArray(); // ]

			jGenerator.writeEndObject(); // }

			jGenerator.close();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		}
	}
}
