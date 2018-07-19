package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class testPost {

	public static void main(String[] args) throws IOException {
		//需要请求的restful地址
		URL url = new URL("http://10.121.4.78:9080/7,093d3a1422a05a");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		//定义数据分割线（可自定义，在Content-Type后面设置一下即可）
		String BOUNDARY = "---------7d4a6d158c9";
		//定义最后的数据分割线
		byte[] end_data = ("\r\n--" + BOUNDARY + "\r\n").getBytes();
		
		conn.setRequestMethod("POST");	//POST GET PUT DELETE
		//设置为表单提交
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setConnectTimeout(10000);	//连接超时	 单位：毫秒
		conn.setReadTimeout(2000);	//读取超时	单位：毫秒
		//发送post请求必须设置如下两行
		conn.setDoOutput(true);	//是否输入参数
		conn.setDoInput(true);
		
		//添加参数file
		File file = new File("C:\\Users\\ZJ\\Desktop\\dola.jpg");
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"");
		sb.append("\r\n");
		sb.append("Content-Type:application/octet-stream");
		sb.append("\r\n");
		sb.append("\r\n");
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(sb.toString().getBytes());
		
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while((bytes = in.read(bufferOut)) != -1){
			out.write(bufferOut, 0, bytes);
		}
//		out.write("\r\n".getBytes());	//多个文件时，两个文件之间加入这个
		in.close();
		out.write(end_data);
		out.flush(); 	//flush输出流的缓冲
		
		//获得返回数据流
		InputStream inStream = conn.getInputStream();
		byte[] result = new byte[inStream.available()];
		inStream.read(result, 0, inStream.available());
		System.out.println(new String(result, "gbk"));
		
	}
}
