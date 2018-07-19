package out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 写入会乱码，暂未解决
 * @author ZJ
 *
 */
public class ObjectOutputStreamDemo {

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		File file = new File("d:" + File.separator + "download" + File.separator + "temp.txt");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		System.out.println(new SerializableDemo("aa",23));
		oos.writeObject(new SerializableDemo("aab", 23).toString().getBytes("GBK"));
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Object obj = ois.readObject();
		System.out.println(obj);	//乱码？？？？
		
		oos.close();
	}
}
