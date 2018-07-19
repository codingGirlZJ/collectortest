package out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 解压缩（一个压缩文件中包含多个文件的情况）
 */
public class ZipFileDemo3 {
	public static void main(String[] args) throws IOException {
		//要解压缩的压缩文件
		File file = new File("d:" + File.separator + "zipFile.zip");
		File outFile = null;
		ZipFile zipFile = new ZipFile(file);
		//用ZipInputStream来依次读取压缩文件中的文件
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
		ZipEntry entry = null;
		InputStream input = null;
		OutputStream output = null;
		while ((entry = zipInput.getNextEntry()) != null) {
			System.out.println("解压缩" + entry.getName() + "文件");
			//新建输出文件
			outFile = new File("d:\\download" + File.separator + entry.getName());
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdir();
			}
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			//读入压缩文件中的下一个文件
			input = zipFile.getInputStream(entry);
			//将输出流流向输出文件
			output = new FileOutputStream(outFile);
			int temp = 0;
			//复制文件内容
			while ((temp = input.read()) != -1) {
				output.write(temp);
			}
			input.close();
			output.close();
		}
	}
}