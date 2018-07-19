package out;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 字符流
 * 从文件中读出内容
 * @author ZJ
 *
 */
public class ReadDemo1 {

	public static void main(String[] args) throws IOException{
		//直接读到char数组中去
		String fileName = "D:\\download" + File.separator + "temp.txt";
		File f = new File(fileName);
		char[] ch = new char[100];
		Reader read = new FileReader(f);
		int count = read.read(ch);	//当文件内容大于预设的字符数组长度时，只读到字符数组长度那么多的内容
		read.close();
		System.out.println("读入的长度为：" + count);
		System.out.println("内容为：" + new String(ch, 0, count));
		
		//以循环的方式从文件中读取内容
		char[] ch1 = new char[100];
		Reader read1 = new FileReader(f);
		int temp = 0;
		int count1 = 0;
		while((temp = read1.read()) != (-1)){
			ch1[count1++] = (char)temp;	//当文件内容比预设的字符数组长度要大时，会报ArrayIndexOutOfBoundsException
		}
		read1.close();
		System.out.println("内容1为：" + new String(ch1, 0, count1));
	}
}
