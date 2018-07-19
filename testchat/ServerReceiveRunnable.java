package testchat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerReceiveRunnable implements Runnable {

	private Socket s = null;

	public ServerReceiveRunnable(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		DataInputStream dataInputStream = null;
		try {
			while (true) {
				inputStream = s.getInputStream();
				dataInputStream = new DataInputStream(inputStream);
				System.out.println("Server received:" + dataInputStream.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
