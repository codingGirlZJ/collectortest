package testchat;

import java.net.Socket;

public class TestThreadClient {

	public static void main(String[] args) throws Exception {
		Socket s = null;
		
		s = new Socket("127.0.0.1",5652);
		Runnable r1 = new ClientSendRunnable(s);
		Thread t1 = new Thread(r1);
		t1.start();
		
		Runnable r2 = new ClientReceiveRunnable(s);
		Thread t2 = new Thread(r2);
		t2.start();
	}
}
