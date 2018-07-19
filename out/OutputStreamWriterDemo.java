package out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 字符流转化为字节流
 * @author ZJ
 *
 */
public class OutputStreamWriterDemo {

	public static void main(String[] args) throws IOException {
		String fileName = "d:\\download" + File.separator + "temp.txt";
		File file = new File(fileName);
		//下面这句的意思：
		//out通过OutputStreamWriter把输出的字符转化为字节，然后通过FileOutputStream字节流把字节写出去
		//附：如果想操作二进制文件必须使用OutputStream字节流操作？
		Writer out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("hello");
		out.close();
	}
}
