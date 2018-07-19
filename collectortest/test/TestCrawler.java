package collectortest.test;

import java.io.File;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class TestCrawler extends BreadthCrawler {
	
	File downloadDir;

	public TestCrawler(String crawlPath,String downloadPath) {
		super(crawlPath, false);
		downloadDir = new File(downloadPath);
		if(!downloadDir.exists()){
			downloadDir.mkdirs();
		}
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// TODO Auto-generated method stub
		org.jsoup.nodes.Document doc = page.doc();
		Elements imgs = doc.select("img");
		System.out.println(imgs.size());
		for(Element img : imgs){
//			if(img.attr("abs:src").matches("http://images.wenku.it168.com/pdf/.*")){
				System.out.println(img.attr("abs:src"));
//			}
		}
	}

	public static void main(String[] args) throws Exception{
		TestCrawler crawler = new TestCrawler("testCrawler", "F://TempDownloads/product/");
		crawler.addSeed("http://wenku.it168.com/d_000379828.shtml");
		crawler.setResumable(true);
		crawler.setAutoParse(false);
		crawler.setThreads(5);
		crawler.start(3);
	}
}
