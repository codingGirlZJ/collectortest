package collectortest.test;

import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class NewsCrawler2 extends BreadthCrawler{

	public NewsCrawler2(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		// TODO Auto-generated constructor stub
		this.addSeed("http://www.qiegaowz.com/product/Default.html");
		
//		this.addRegex("http://www.qiegaowz.com/product/[a-z0-9]{8}\\-([a-z0-9]{4}\\-){3}[a-z0-9]{12}.html");
		this.addRegex("http://www.qiegaowz.com/product/.*\\.html");
		
//		this.addRegex("-.*\\.(jpg|png|gif).*");
		
//		this.addRegex("-.*#.*");
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// TODO Auto-generated method stub
		String url = page.getUrl();
//		System.out.println(url + "aaaaaaaaaa");
		if(page.matchUrl("http://www.qiegaowz.com/product/.*")){
			Document doc = page.getDoc();
			
			String title = page.select("span>a[href$=.html]",1).attr("title");
			String content = page.select("img[src$=.jpg]",1).attr("src");
			
			System.out.println("URL:\n" + url);
	        System.out.println("title:\n" + title);
	        System.out.println("content:\n" + content);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
	    NewsCrawler2 crawler = new NewsCrawler2("crawl", true);
	    crawler.setThreads(50);
	    crawler.setTopN(100);
	    //crawler.setResumable(true);
	    /*start crawl with depth of 4*/
	    crawler.start(4);
	    }

}
