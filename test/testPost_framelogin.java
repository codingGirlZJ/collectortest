package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

public class testPost_framelogin {

	public static void main(String[] args) throws IOException {
		//需要请求的restful地址
		URL url = new URL("http://localhost:8080/usermanager/framelogin");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");	//POST GET PUT DELETE
		//设置为表单提交
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setUseCaches(false);
		conn.setConnectTimeout(10000);	//连接超时	 单位：毫秒
		conn.setReadTimeout(2000);	//读取超时	单位：毫秒
		//发送post请求必须设置如下两行
		conn.setDoOutput(true);	//是否输入参数
		conn.setDoInput(true);
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		JSONObject obj = new JSONObject();
		obj.put("userName", "zhangjuan222");
		obj.put("password", "123");
		out.write(obj.toString().getBytes());
		out.flush();  
        out.close();
		
		
		//获得返回数据流
		InputStream inStream = conn.getInputStream();
		byte[] result = new byte[inStream.available()];
		inStream.read(result, 0, inStream.available());
		System.out.println(new String(result, "utf-8"));
		
		inStream.close();
		conn.disconnect();
	}
}
