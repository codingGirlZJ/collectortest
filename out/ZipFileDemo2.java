package out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 解压缩文件（压缩文件中只有一个文件的情况）
 */
public class ZipFileDemo2 {
	public static void main(String[] args) throws IOException {
		// 要解压缩的压缩文件——压缩文件中只有一个文件
		File file = new File("d:\\download" + File.separator + "temp.zip");
		// 解压后的文件
		File outFile = new File("d:\\download" + File.separator + "unTemp.txt");
		ZipFile zipFile = new ZipFile(file);
		// 获取压缩文件中的temp.txt文件，如果文件名不对获取不到会报错的
		ZipEntry entry = zipFile.getEntry("temp.txt");
		// 读入文件
		InputStream input = zipFile.getInputStream(entry);
		// 新建输出流流向解压后的文件
		OutputStream output = new FileOutputStream(outFile);
		int temp = 0;
		// 复制文件内容
		while ((temp = input.read()) != -1) {
			output.write(temp);
		}
		// 关闭输入输出流
		input.close();
		output.close();
	}
}