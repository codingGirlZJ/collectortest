package out;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Scanner
 * 读取控制台输入
 * 从文件中读内容
 * @author ZJ
 *
 */
public class ScannerDemo {

	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		//读一个整数
//		int temp = sca.nextInt();
//		System.out.println(temp);
		//读取浮点数
//		float flo = sca.nextFloat();
//		System.out.println(flo);
		//读取字符等类似
		
		//Scanner例子：从文件中读内容
		File file = new File("d:\\download" + File.separator + "temp.txt");
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = "";
		while(fileReader.hasNext()){
			str += fileReader.next() + "\n";
		}
		System.out.println("从文件中读取到的内容是：\n" + str);
	}
}
