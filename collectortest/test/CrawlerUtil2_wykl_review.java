package collectortest.test;

import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.text.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * WebCollector抓取图片的例子
 * 
 * @author hu
 */
public class CrawlerUtil2_wykl_review extends BreadthCrawler {

	// 用于保存图片的文件夹
	File downloadDir;

	// 原子性int，用于生成图片文件名
	AtomicInteger imageId;

	/**
	 * 
	 * @param crawlPath
	 *            用于维护URL的文件夹
	 * @param downloadPath
	 *            用于保存图片的文件夹
	 * @param testgoodservice
	 *            用于保存数据到数据库的service
	 */
	public CrawlerUtil2_wykl_review(String crawlPath, String downloadPath) {
		super(crawlPath, true);
		downloadDir = new File(downloadPath);
		if (!downloadDir.exists()) {
			downloadDir.mkdirs();
		}
		computeImageId();
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		// String contentType = page.getResponse().getContentType();
		System.out.println(page.getResponse().headers() + "11111111111111111111111");
		if (page.url().matches("^http://www.kaola.com/review/.*\\.html")) {

			org.jsoup.nodes.Document document = page.doc();
			Elements comments = document.select("div[class=eachInfo clearfix]");
			System.out.println(comments.size());
			for(Element comment : comments){
				System.out.println(comment.select("span[class=itemDetail]").text());
			}
			
		}
	}

	public void computeImageId() {
		int maxId = -1;
		for (File imageFile : downloadDir.listFiles()) {
			String fileName = imageFile.getName();
			String idStr = fileName.split("\\.")[0];
			idStr = idStr.substring(36);
			int id = Integer.valueOf(idStr);
			if (id > maxId) {
				maxId = id;
			}
		}
		imageId = new AtomicInteger(maxId);
	}

	public static void main(String[] args) throws Exception {
		CrawlerUtil2_wykl_review crawler = new CrawlerUtil2_wykl_review("crawler", "F://TempDownloads/product/");
		crawler.addSeed("http://www.kaola.com/review/1357061.html");
		// crawler.addRegex("http://www.kaola.com/product/.*\\.html");
		// crawler.addRegex("-http://www.w7a.net/product/list.*");
		// 设置为断点爬取，否则每次开启爬虫都会重新爬取
		crawler.setResumable(false);
		// 设置是否自动抽取符合正则的链接并加入后续任务
		crawler.setAutoParse(false);
		// 每次开启10个线程
		crawler.setThreads(10);
		// Config.MAX_RECEIVE_SIZE = 1000 * 1000 * 10;
		// 开始爬取，设置迭代次数为5
		crawler.start(3);

		// Thread.sleep(10000);
	}

}