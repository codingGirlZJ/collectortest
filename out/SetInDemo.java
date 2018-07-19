package out;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SetInDemo {

	public static void main(String[] args) throws IOException{
		File file = new File("d:\\download" + File.separator + "temp.txt");
		if(!file.exists()){
			System.out.println("要读取的文件不存在！");
		}else{
			System.setIn(new FileInputStream(file));
			
			byte[] bytes = new byte[1024];
			int len = 0;
			len = System.in.read(bytes);
			System.out.println("读入的内容为：");
			System.out.println(new String(bytes, 0, len));
		}
	}
}
