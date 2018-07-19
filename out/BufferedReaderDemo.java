package out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 使用缓冲区从键盘上读入内容
 * @author ZJ
 *
 */
public class BufferedReaderDemo {

	public static void main(String[] args) {
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		System.out.println("请输入内容");
		try {
			str = buf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("你输入的内容是：" + str);
	}
}
