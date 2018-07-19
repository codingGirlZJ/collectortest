package testchat;

import java.net.ServerSocket;
import java.net.Socket;

public class TestThreadServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket ss = null;
		ss = new ServerSocket(5652);
		while(true){
			Socket s = ss.accept();
			Runnable r1 = new ServerReceiveRunnable(s);
			Thread t1 = new Thread(r1);
			t1.start();
			
			Runnable r2 = new ServerSendRunnable(s);
			Thread t2 = new Thread(r2);
			t2.start();
		}
	}
}
