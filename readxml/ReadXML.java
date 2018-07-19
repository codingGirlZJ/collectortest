package readxml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 想读取xml文件内容
 * D:\Software\apache-tomcat-7.0.68\conf\Catalina\localhost\collector.xml
 * @author ZJ
 *
 */
public class ReadXML {

	public static void main(String[] args) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		String path = "D:\\Software\\apache-tomcat-7.0.68\\conf\\Catalina\\localhost\\collector.xml";
		Document document = db.parse(new File(path));
		
		NodeList node = document.getElementsByTagName("Resource");
		Element ele = (Element)node.item(0);
		String name = ele.getAttribute("name");
		String fac = ele.getAttribute("factory");
		String auth = ele.getAttribute("auth");
		String type = ele.getAttribute("type");
		String driverClassName = ele.getAttribute("driverClassName");
		String url = ele.getAttribute("url");
		String username = ele.getAttribute("username");
		String password = ele.getAttribute("password");
		String maxActive = ele.getAttribute("maxActive");
		String maxWait = ele.getAttribute("maxWait");
		String removeabandoned = ele.getAttribute("removeabandoned");
		String removeabandonedtimeout = ele.getAttribute("removeabandonedtimeout");
		String logabandoned = ele.getAttribute("logabandoned");
		String poolPreparedStatements = ele.getAttribute("poolPreparedStatements");
		String maxPoolPreparedStatementPerConnectionSize = ele.getAttribute("maxPoolPreparedStatementPerConnectionSize");
		String filters = ele.getAttribute("filters");
		System.out.println("name = " + name);
		System.out.println("factory = " + fac);
		System.out.println("auth = " + auth);
		System.out.println("type = " + type);
		System.out.println("driverClassName = " + driverClassName);
		System.out.println("url = " + url);
		System.out.println("username = " + username);
		System.out.println("password = " + password);
		System.out.println("maxActive = " + maxActive);
		System.out.println("maxWait = " + maxWait);
		System.out.println("removeabandoned = " + removeabandoned);
		System.out.println("removeabandonedtimeout = " + removeabandonedtimeout);
		System.out.println("logabandoned = " + logabandoned);
		System.out.println("poolPreparedStatements = " + poolPreparedStatements);
		System.out.println("maxPoolPreparedStatementPerConnectionSize = " + maxPoolPreparedStatementPerConnectionSize);
		System.out.println("filters = " + filters);
	}
}
