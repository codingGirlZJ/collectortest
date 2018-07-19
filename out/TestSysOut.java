/**
 * 
 */
/**
 * @author ZJ
 *
 */
package out;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * TestSysOut.java
 */

/**
 * TestSysOut
 * 
 * @author admin
 * 
 */
public class TestSysOut {

	/**
	 * main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		int i = 150;
//		byte b = (byte)i;
////		int j = b;
//		System.out.println(i);
//		System.out.println(b);
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
		// cache stream
		PrintStream cacheStream = new PrintStream(baoStream);
		// old stream
		PrintStream oldStream = System.out;

		System.setOut(cacheStream);

		for(int i = 0; i < 1000; i++){
			System.out.println("hello world!");
		}

		String message = baoStream.toString();

		message = "<-- " + message + " -->";

		// Restore old stream
		System.setOut(oldStream);

		System.out.println(message);
	}
}