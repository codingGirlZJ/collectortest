package proxyTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class proxyServer {

	public static void main(String[] args) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();

		// httpClient.getHostConfiguration().setProxy("localhost", 808);
		httpClient.getHostConfiguration().setProxy("113.57.132.116", 3128);

		// 需要验证
//		 UsernamePasswordCredentials creds = new UsernamePasswordCredentials("chenlb","123456");
//		 httpClient.getState().setProxyCredentials(AuthScope.ANY, creds);

		// 设置http头
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);

		GetMethod method = new GetMethod("https://www.baidu.com/");
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(method);
			
			if(statusCode != HttpStatus.SC_OK){
				System.out.println("Method failed code=" + statusCode + ": " + method.getStatusLine());
			}else{
				System.out.println(new String(method.getResponseBody(),"gb2312"));
			}
		} finally{
			method.releaseConnection();
		}
	}
}
