package collectortest.test;

import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class NewsCrawler extends BreadthCrawler{

	public NewsCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		// TODO Auto-generated constructor stub
		this.addSeed("http://www.hubu.edu.cn/");
		
		this.addRegex("http://www.hubu.edu.cn/info/*.*htm");
		
		this.addRegex("-.*\\.(jpg|png|gif).*");
		
		this.addRegex("-.*#.*");
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// TODO Auto-generated method stub
		String url = page.getUrl();
		if(page.matchUrl("http://www.hubu.edu.cn/info/*.*htm")){
			Document doc = page.getDoc();
			
			String title = page.select("div[class=con-title]>h3").first().text();
			String content = page.select("div#vsb_content_501",0).text();
			
			System.out.println("URL:\n" + url);
	        System.out.println("title:\n" + title);
	        System.out.println("content:\n" + content);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
	    NewsCrawler crawler = new NewsCrawler("crawl", true);
	    crawler.setThreads(50);
	    crawler.setTopN(100);
	    //crawler.setResumable(true);
	    /*start crawl with depth of 4*/
	    crawler.start(4);
	    }

}
