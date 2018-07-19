package out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SetOutDemo {

	public static void main(String[] args) throws IOException {
		// OutputStream out = System.out;
		// out.write("hello".getBytes());
		// out.close();

		// 此刻直接输出到屏幕
		System.out.println("hello");
		File file = new File("d:\\download" + File.separator + "temp.txt");
		// 将输出重定向到文件去
		System.setOut(new PrintStream(new FileOutputStream(file)));
		System.out.println("你看不见这些内容，因为他们在文件里");

		// 将错误信息输出重定向到文件中，用这种方法保存错误信息
		System.err.println("hello");
		// 将输出重定向到文件去
		System.setErr(new PrintStream(new FileOutputStream(file,true)));
		System.err.println("你看不见这些错误信息，因为他们在文件里");
	}
}
