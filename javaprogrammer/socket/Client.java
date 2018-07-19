package javaprogrammer.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args){
		BufferedReader br = null;
		PrintWriter pw = null;
		try{
			Socket socket = new Socket("127.0.0.1", 2000);
			//获取输入输出流
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			//向服务器发送数据
			while(true){
				Scanner in = new Scanner(System.in);
				String line = in.nextLine();
				pw.println(line);
				System.out.println("client发送：" + line);
				String s = null;
				while(true){
					s = br.readLine();
					if(s != null){
						break;
					}
				}
				System.out.println("接收回显：" + s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				br.close();
				pw.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
