package out;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 字节输入流转化为字符输入流
 * 
 * @author ZJ
 *
 */
public class InputStreamReaderDemo {

	public static void main(String[] args) throws IOException {
		String fileName = "d:" + File.separator + "hello.txt";
		File file = new File(fileName);
		//把通过字节流读入的字节，通过InputStreamReader转化为字符流
		Reader read = new InputStreamReader(new FileInputStream(file));
		char[] b = new char[100];
		int len = read.read(b);
		System.out.println(new String(b, 0, len));
		read.close();
	}
}
