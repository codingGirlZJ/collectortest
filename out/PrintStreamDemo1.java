package out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 使用PrintStream向文件进行输出
 * @author ZJ
 *
 */
public class PrintStreamDemo1 {

	public static void main(String[] args) throws FileNotFoundException {
		//要设置是否清空原文件内容还是在文件内容后加数据，可在OutputStream构造时设置，如下：
		PrintStream print = new PrintStream(new FileOutputStream(new File("d:\\download" + File.separator + "temp.txt"),true));
//		print.println(true);	//为true则在文件最后输出，默认为false会清空原文件再输出
		print.println("hahaha");
		print.close();
	}
}
