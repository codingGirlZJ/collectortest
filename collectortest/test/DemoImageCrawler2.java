package collectortest.test;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;
import java.io.File;
import java.io.IOException;
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
public class DemoImageCrawler2 extends BreadthCrawler {

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
	 */
	public DemoImageCrawler2(String crawlPath, String downloadPath) {
		super(crawlPath, true);
		downloadDir = new File(downloadPath);
		if (!downloadDir.exists()) {
			downloadDir.mkdirs();
		}
		computeImageId();
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		System.out.println(page.url() + "hehhehe");
		if (page.url().matches("^http://www.w7a.net/product/info.*")) {
			org.jsoup.nodes.Document document = page.doc();
			String price = document.select(".nc-detail-price").text().split("¥|元")[1];
			String name = document.select(".nc-detail-hd").text();
			System.out.println(price + "peice");
			System.out.println(name + "name");
			Elements imgs = document.select("img[src]");
			System.out.println(imgs.size() + "sssssdddd");
			for (Element img : imgs) {
				String imgSrc = img.attr("abs:src");
				System.out.println(imgSrc + "imgsrc");
				next.add(imgSrc).meta("key", UUID.randomUUID().toString()).meta("name", name.trim());

			}
		} else {
			if (page.getResponse().getContentType().startsWith("image")) {
				System.out.println("要下载");
				String extensionName = page.response().contentType().split("/")[1];
				String imageFileName = page.meta("key") + imageId.incrementAndGet() + "." + extensionName;
				File imageFile = new File(downloadDir, imageFileName);
				try {
					FileUtils.writeFile(imageFile, page.getContent());
					System.out.println("保存图片 " + page.getUrl() + " 到 " + imageFile.getAbsolutePath());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		DemoImageCrawler2 demoImageCrawler = new DemoImageCrawler2("crawl1", "F:\\TempDownloads\\temp");
		// 添加种子URLhttp://blog.csdn.net/
		// demoImageCrawler.addSeed("http://www.meishij.net/");
		// demoImageCrawler.addSeed("http://blog.csdn.net/");
		// 限定爬取范围http://news.hfut.edu.cn http://www.meishij.net/
		// demoImageCrawler.addRegex("http://www.meishij.net/.*");
		demoImageCrawler.addSeed("http://www.w7a.net/");
		demoImageCrawler.addRegex("http://www.w7a.net/product/.*");
		demoImageCrawler.addRegex("-http://www.w7a.net/product/list.*");
		// 设置为断点爬取，否则每次开启爬虫都会重新爬取
		demoImageCrawler.setResumable(false);
		demoImageCrawler.setAutoParse(true);
		demoImageCrawler.setThreads(10);
		Config.MAX_RECEIVE_SIZE = 1000 * 1000 * 10;
		demoImageCrawler.start(3);
	}

	public void computeImageId() {
		int maxId = -1;
		for (File imageFile : downloadDir.listFiles()) {
			String fileName = imageFile.getName();
			String idStr = fileName.split("\\.")[0];
			int id = Integer.valueOf(idStr);
			if (id > maxId) {
				maxId = id;
			}
		}
		imageId = new AtomicInteger(maxId);
	}

}