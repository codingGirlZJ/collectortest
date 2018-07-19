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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * WebCollector抓取图片的例子
 * 
 * @author hu
 */
public class CrawlerUtil2_amazon extends BreadthCrawler {

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
	public CrawlerUtil2_amazon(String crawlPath, String downloadPath) {
		super(crawlPath, true);
		downloadDir = new File(downloadPath);
		if (!downloadDir.exists()) {
			downloadDir.mkdirs();
		}
		computeImageId();
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		String contentType = page.getResponse().getContentType();
		if (contentType == null) {
			return;
		} else if (contentType.contains("html")) {

			org.jsoup.nodes.Document document = page.doc();
			Elements htmls = page.select("a");
			System.out.println("htmls:" + htmls.size());
			for (Element htm : htmls) {
				String htmUrl = htm.attr("abs:href");
				if (htmUrl.matches("^http(s)?://www.amazon.cn(/[^/]+)?/(dp/[0-9a-zA-Z]{10}|gp/product/[0-9a-zA-Z]{10}).*")) {
					System.out.println("详情页链接路径：" + htmUrl);
					Pattern r = Pattern.compile("(?<=http(s)?://www.amazon.cn)(/[^/]+)?/(dp/[0-9a-zA-Z]{10}|gp/product/[0-9a-zA-Z]{10}).*");
					Matcher m = r.matcher(htmUrl);
					if(m.find()){
						System.out.println(m.group());
					}
//					next.add(htmUrl);
				}
			}

			 if(page.url().matches("^http(s)?://www.amazon.cn(/[^/]+)?/(dp/[0-9a-zA-Z]{10}|gp/product/[0-9a-zA-Z]{10}).*")){
				System.out.println("当前为详情页：" + page.url());
				// org.jsoup.nodes.Document document = page.doc();
				// Elements eles = document.getElementsByTag("script");

				// Elements imgs = document.select("li[class=a-spacing-small
				// item]").select("img");
				// Elements imgs = document.select("div[class=a-column a-span3
				// a-spacing-micro imageThumb thumb]").select("img");
				// Elements e = document.select("input[name=ASIN]");
				// System.out.println(e.size() + "----" +
				// e.get(0).attr("value"));
				// for(Element e1:e){
				// if(e1.attr("id").equals("ASIN")){
				// System.out.println(e1.attr("value"));
				// }
				// }
				// System.out.println("商品图片：");
				// for(Element img : imgs){
				// System.out.println(img.attr("abs:src"));
				// }

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
		CrawlerUtil2_amazon crawler = new CrawlerUtil2_amazon("crawler", "F://TempDownloads/product/");
		crawler.addSeed("https://www.amazon.cn/");
		// crawler.addRegex("http://www.kaola.com/product/.*\\.html");
		// crawler.addRegex("-http://www.w7a.net/product/list.*");
		// 设置为断点爬取，否则每次开启爬虫都会重新爬取
		crawler.setResumable(false);
		// 设置是否自动抽取符合正则的链接并加入后续任务
		crawler.setAutoParse(false);
		// 每次开启10个线程
		crawler.setThreads(2);
		// Config.MAX_RECEIVE_SIZE = 1000 * 1000 * 10;
		// 开始爬取，设置迭代次数为5
		crawler.start(3);

		// Thread.sleep(10000);
	}

}