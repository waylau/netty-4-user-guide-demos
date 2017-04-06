/**
 * 
 */
package com.waylau.netty.demo.codec.jackcon;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年11月8日
 */
public class JsonParserDemo {

	private static final String FILE_PATH = "d:\\user.json";

	/**
	 * 
	 */
	public JsonParserDemo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonParseException
	 */
	public static void main(String[] args) throws JsonParseException,
			IOException {

		JsonFactory jfactory = new JsonFactory();

		JsonParser jParser = jfactory.createJsonParser(new File(FILE_PATH));

		// loop until token equal to "}"
		while (jParser.nextToken() != JsonToken.END_OBJECT) {

			String fieldname = jParser.getCurrentName();
			if ("name".equals(fieldname)) {

				// current token is "name",
				// move to next, which is "name"'s value
				jParser.nextToken();
				System.out.println(jParser.getText()); // display mkyong

			}

			if ("age".equals(fieldname)) {

				// current token is "age",
				// move to next, which is "name"'s value
				jParser.nextToken();
				System.out.println(jParser.getIntValue()); // display 29

			}

			if ("messages".equals(fieldname)) {

				jParser.nextToken(); // current token is "[", move next

				// messages is array, loop until token equal to "]"
				while (jParser.nextToken() != JsonToken.END_ARRAY) {

					// display msg1, msg2, msg3
					System.out.println(jParser.getText());

				}

			}

		}
		jParser.close();

	}

}
