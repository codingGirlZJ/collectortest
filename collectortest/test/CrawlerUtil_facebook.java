package collectortest.test;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;

public class CrawlerUtil_facebook extends BreadthCrawler {

	private Proxys proxys = new Proxys();

	public CrawlerUtil_facebook(String crawlPath, boolean autoParse, Proxys proxys) {
		super(crawlPath, autoParse);
		this.proxys = proxys;
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// TODO Auto-generated method stub
		if (page.url().matches("https://www.facebook.com/sport/basketball/(\\?page=[0-9]+)?")) {
			org.jsoup.nodes.Document document = page.doc();
			// System.out.println("html:" + document.html());
			Elements eles = document.select(".hidden_elem"); // 选出隐藏内容的div
			int countContent = 0; // 记录内容的数量
			for (Element ele : eles) {
				if (ele.childNodes().size() > 0) {
					String codeText = ele.child(0).html(); // 获取存放在<code>标签中的隐藏内容，不能用text()，被注释了的内容text()获取不到
					codeText = codeText.substring(5, codeText.length() - 3);
					Document doc = Jsoup.parse(codeText); // 获取注释掉的内容
					Elements contents = doc.select("div[class=_4-u2 mbm _4mrt _5jmm _5pat _5v3q _4-u8]"); // 获取存放内容的div
					for (Element content : contents) {
						countContent++;
						System.out.println("第" + countContent + "条内容：");
						String name;
						if(content.select("span[class=fwn fcg]").select(".profileLink").size() > 0){
							name = content.select(".profileLink").get(0).text(); // 发布人
						}else{
							name = content.select("span[class=fwb fcg]").get(0).child(0).text(); // 发布人
						}
						String date = content.select("abbr").attr("title"); // 发布时间
						String con = content.select("div[class=_5pbx userContent]").text(); // 发布内容
						if(content.select("._3x-2").select("div[class=mtm _5pcm]").size() > 0){
							con = con + " " + content.select("._3x-2").select("div[class=mtm _5pcm]").get(0).select("div[class=mtm _5pco]").text();
						}
						System.out.println("发布人：" + name + "\n发布时间：" + date + "\n发布内容：" + con);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Proxys proxys = new Proxys();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 65314)); // 开蓝灯翻墙后浏览器自动设置的代理地址和端口
		proxys.add(proxy); // 添加一个代理
		CrawlerUtil_facebook crawler = new CrawlerUtil_facebook("crawler", true, proxys);
		crawler.addSeed("https://www.facebook.com/sport/basketball/?page=2");
		// crawler.addSeed("http://blog.csdn.net/ajaxhu/article/details/39760889");

		Config.TIMEOUT_CONNECT = 5000;
		Config.TIMEOUT_READ = 20000;

		crawler.setResumable(false);
		crawler.setAutoParse(false);
		crawler.setThreads(3);
		crawler.start(3);
	}

	@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
		HttpRequest request = new HttpRequest(crawlDatum);
		request.setProxy(proxys.nextRandom()); // 随机选择一个代理
		return request.response();
	}

}
