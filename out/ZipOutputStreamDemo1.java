package out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 单个文件压缩
 */
public class ZipOutputStreamDemo1 {
	public static void main(String[] args) throws IOException {
		File file = new File("d:\\download" + File.separator + "temp.txt");
		File zipFile = new File("d:\\download" + File.separator + "temp.zip");
		InputStream input = new FileInputStream(file);
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		// putNextEntry是设置读取到的第i个文件在压缩文件中的文件位置和文件名
		zipOut.putNextEntry(new ZipEntry(file.getName()));
		// 设置注释
		zipOut.setComment("hello");
		int temp = 0;
		while ((temp = input.read()) != -1) {
			zipOut.write(temp);
		}
		input.close();
		zipOut.close();
	}
}
