package out;

import java.io.File;
import java.io.IOException;

public class FileDemo1 {

	public static void main(String[] args) throws IOException {
		// 新建一个文件
		File f = new File("D:" + File.separator + "download" + File.separator + "temp11.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(File.separator);
		System.out.println(File.pathSeparator);

		// 删除一个文件
		File f1 = new File("D:" + File.separator + "download" + File.separator + "temp111.txt");
		if (f1.exists()) {
			f1.delete();
		} else {
			System.out.println("文件不存在！");
		}

		// 列出目录下的所有文件
		String fileName = "D:" + File.separator + "download";
		File f2 = new File(fileName);
		String[] str = f2.list(); // 获取目录下的所有文件名，返回String数组，且是相对路径
		File[] files = f2.listFiles(); // 获取目录下的所有文件，返回File数组，要获取目录下的文件的完整路径可用这种方式，当然，也可用目录名拼接上文件名的方式
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
			System.out.println(files[i]);
		}

		// 使用isDirectory判断一个指定的路径是否为目录
		File ff = new File("D:" + File.separator + "download");
		if (ff.isDirectory()) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

		/**
		 * File类的获取文件路径的三种方式区别 1.
		 * getPath()方法：获取文件定义时的路径，可能是相对路径，也可能是绝对路径,其中定义时若用的绝对路径，则三种方式都没差 2.
		 * getAbsolutePath()方法：获取文件的绝对路径，但不会处理"."和".."的情况，如下f3和f4 3.
		 * getCanonicalPath()方法：返回的是规范化的绝对路径，相当于将getAbsolutePath()
		 * 中的"."和".."解析成对应的正确路径
		 */
		File f3 = new File("..\\temp.txt");
		System.out.println(f3.getPath());
		System.out.println(f3.getAbsolutePath());
		System.out.println(f3.getCanonicalPath());
		System.out.println("----------------------------------");

		File f4 = new File(".\\temp.txt");
		System.out.println(f4.getPath());
		System.out.println(f4.getAbsolutePath());
		System.out.println(f4.getCanonicalPath());
		System.out.println("----------------------------------");

		File f5 = new File("\\temp.txt");
		System.out.println(f5.getPath());
		System.out.println(f5.getAbsolutePath());
		System.out.println(f5.getCanonicalPath());
		System.out.println("----------------------------------");

		File f6 = new File("D:\\download\\temp.txt");
		System.out.println(f6.getPath());
		System.out.println(f6.getAbsolutePath());
		System.out.println(f6.getCanonicalPath());
	}
}
