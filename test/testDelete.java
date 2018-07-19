package test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class testDelete {

	public static void main(String[] args) throws IOException {
		//需要请求的restful地址
		URL url = new URL("http://10.121.4.78:9080/7,093d3a1422a05a");
		
		//打开restful链接
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		//请求方式
		conn.setRequestMethod("DELETE");	//POST GET PUT DELETE
		
		//设置为表单提交
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		conn.setConnectTimeout(10000);	//连接超时	 单位：毫秒
		conn.setReadTimeout(2000);	//读取超时	单位：毫秒
		
//		conn.setDoOutput(true);	//是否输入参数
		conn.setDoInput(true);
		
		StringBuffer params = new StringBuffer();
		//表单参数与get形式予以
		params.append("param1").append("=").append("value1ggg");
//		byte[] bytes = params.toString().getBytes();
//		conn.getOutputStream().write(bytes); 	//传入参数
		InputStream inStream = conn.getInputStream();
		byte[] bytes = new byte[inStream.available()];
		inStream.read(bytes, 0, inStream.available());
		System.out.println(new String(bytes, "gbk"));
		
	}
}
