package out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 编码
 * @author ZJ
 *
 */
public class CharSetDemo {

	public static void main(String[] args) throws IOException {
		//取得本地默认编码
		System.out.println("系统默认编码为：" + System.getProperty("file.encoding"));
		
		//乱码的产生
		File file = new File("D:" + File.separator + "download" + File.separator + "temp.txt");
        OutputStream out = new FileOutputStream(file);
        byte[] bytes = "你好".getBytes("ISO8859-1");
        out.write(bytes);	//输出结果为乱码，系统默认编码为UTF-8，而此处编码为ISO8859-1
        out.close();
	}
}
