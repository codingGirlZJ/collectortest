package javaprogrammer.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args){
		BufferedReader br = null;
		PrintWriter pw = null;
		ServerSocket server = null;
		try{
			server = new ServerSocket(2000);
			while(true){
				Socket socket = server.accept();
				//获取输入流
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//获取输出流
				pw = new PrintWriter(socket.getOutputStream(), true);
				String s = br.readLine();	//获取接收的数据
				System.out.println("server接收：" + s);
				pw.println(s); 	//发送相同的数据给传来数据的客户端
				System.out.println("server发送：" + s);
				socket.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				br.close();
				pw.close();
				server.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
