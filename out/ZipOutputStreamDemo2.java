package out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 一次性压缩多个文件
 */
public class ZipOutputStreamDemo2 {
	public static void main(String[] args) throws IOException {
		// 要被压缩的文件夹
		File file = new File("d:" + File.separator + "download");
		// 压缩文件的位置和名字
		File zipFile = new File("d:" + File.separator + "zipFile.zip");
		InputStream input = null;
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOut.setComment("hello");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; ++i) {
				input = new FileInputStream(files[i]); // 读取文件夹下的第i个文件
				// putNextEntry是设置读取到的第i个文件在压缩文件中的文件位置和文件名
				zipOut.putNextEntry(new ZipEntry(file.getName() + "_" + i + File.separator
						+ files[i].getName().split("\\.")[0] + "_" + i + "." + files[i].getName().split("\\.")[1]));
				int temp = 0;
				// 复制文件内容
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
				input.close();
			}
		}
		zipOut.close();
	}
}
