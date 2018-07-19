package testchat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSendRunnable implements Runnable{
	
	private Socket s = null;
	
	public ClientSendRunnable(Socket s){
		this.s = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		OutputStream outputStream = null;
		DataOutputStream dataOutputStream = null;
		
		try{
			while(true){
				outputStream = s.getOutputStream();
				dataOutputStream = new DataOutputStream(outputStream);
				Scanner in = new Scanner(System.in);
				String line = in.nextLine();
				dataOutputStream.writeUTF(line);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
				
	}

}
