package testchat;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Server extends JFrame implements Runnable,ActionListener{
	JPanel jp;
	JButton jb,jb2;
	ServerSocket ss;
	Socket s;
	ArrayList al=new ArrayList();
	HuThread ht;
	JTextArea jta;
	JScrollPane jsp;
	boolean hufan=true;
	public static void main(String[] args) {
		new Server();
	}
	public Server(){
		jp=new JPanel();
		jb=new JButton("启动服务器");
		jb2=new JButton("关闭服务器");
		jb.addActionListener(this);
		jb2.addActionListener(this);
		jta=new JTextArea();
		jsp=new JScrollPane(jta);
		jp.add(jb);
		jp.add(jb2);
		this.add(jsp);
		this.add(jp,"North");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void run() {
		while(hufan){
			try {
				s=ss.accept();
				al.add(s);
				ht=new HuThread(s,al);
				Thread tt=new Thread(ht);
				tt.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("服务器接收异常" + hufan);
				e.printStackTrace();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb){
			try {
				ss=new ServerSocket(5555);
				hufan=true;
				jta.setText("服务器已经启动");
				Thread t=new Thread(this);
				t.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource()==jb2){
			try {
				hufan=false;
				ss.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("服务器关闭异常");
				e1.printStackTrace();
			}
			jta.setText("服务器已经关闭");
		}
	}
}
class HuThread implements Runnable{
	Socket s;
	ArrayList al;
	public HuThread(Socket s,ArrayList al){
		this.s=s;
		this.al=al;
	}
	public void run() {
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				String info=br.readLine();
				for(int i=0;i<al.size();i++){
					Socket s2=(Socket)al.get(i);
					PrintWriter pw=new PrintWriter(s2.getOutputStream());
					pw.println(info);
					pw.flush();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
