package out;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 该对象并不是流体系中的一员，其封装了字节流，同时还封装了一个缓冲区（字符数组），
 * 通过内部的指针来操作字符数组中的数据。该对象特点：该对象只能操作文件，
 * 所以构造函数接收两种类型的参数：a.字符串文件路径；b.File对象。
 * 该对象既可以对文件进行读操作，也能进行写操作，在进行对象实例化时可指定操作模式(r,rw)
 * 注意：该对象在实例化时，如果要操作的文件不存在，会自动创建；
 * 如果文件存在，写数据未指定位置，会从头开始写，即覆盖原有的内容。
 * 可以用于多线程下载或多个线程同时写数据到文件。
 * @author ZJ
 *
 */
public class RandomAccessFileDemo {

	public static void main(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "download" + File.separator + "temp.txt";
		File f = new File(fileName);
		RandomAccessFile demo = new RandomAccessFile(f, "rw");
		//RandomAccessFile写入文件示例
		demo.writeBytes("asdsad");
		demo.writeInt(12);
		demo.writeBoolean(true);
		demo.writeChar('A');
		demo.writeFloat(1.21f);
		demo.writeDouble(12.123);
		demo.close();
	}
}
