package testchat;


import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
public class Client extends JFrame implements ActionListener,Runnable{
    JTextArea jta;
    JTextField jtf;
    JPanel jp;
    JButton jb;
    BufferedReader in;
    PrintWriter out;
    JScrollPane jsp;
    public static void main(String args[]){
        Client c = new Client();
       // c.receive();
    }
    public Client(){
    	jp=new JPanel();
        jta=new JTextArea();
        jta.setEditable(false);
        jsp=new JScrollPane(jta);
        jb=new JButton("发送");
        jb.addActionListener(this);
        jtf=new JTextField(20);
        jp.add(jtf);
        jp.add(jb);
        this.add(jsp,"Center");
        this.add(jp,"South");
        
        try {
            Socket s = new Socket("127.0.0.1",5555);
            System.out.println("客户连接服务器成功");
            out = new PrintWriter(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));//包装成字符流
        } catch (Exception e) {
            e.printStackTrace();
        } 
    	Thread t=new Thread(this);
    	t.start();
        this.setSize(400,300);
        this.setLocation(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    //消息接收
//    private void receive(){
//        try {
//            while(true){
//                String str = in.readLine();//读BufferedReader
//                if(str==null){
//                    return ;
//                }
//                jta.append("说"+str+"\r\n");//加载到JTextArea中，显示
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String text=jtf.getText();
        out.println(text);
        out.flush();
        jtf.setText("");
	}
	@Override
	public void run() {
		try {
			while (true) {
				String str = in.readLine();
				if (str != null) {
					jta.append("说" + str + "\r\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
