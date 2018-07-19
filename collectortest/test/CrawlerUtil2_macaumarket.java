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
public class CrawlerUtil2_macaumarket extends BreadthCrawler {

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
	public CrawlerUtil2_macaumarket(String crawlPath, String downloadPath) {
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

			org.jsoup.nodes.Document doc = page.doc();
			Elements htmls = page.select("a");
			System.out.println("htmls:" + htmls.size());
			for (Element htm : htmls) {
				String htmUrl = htm.attr("abs:href");
				if (htmUrl.matches("^https://www.macaumarket.com/product/.*\\.html")) {
					System.out.println("详情页链接路径：" + htmUrl);
//					Pattern r = Pattern.compile("(?<=http(s)?://www.amazon.cn)(/[^/]+)?/(dp/[0-9a-zA-Z]{10}|gp/product/[0-9a-zA-Z]{10}).*");
//					Matcher m = r.matcher(htmUrl);
//					if(m.find()){
//						System.out.println(m.group());
//					}
//					next.add(htmUrl);
				}
			}

			 if(page.url().matches("^https://www.macaumarket.com/product/.*")){
				System.out.println("当前为详情页：" + page.url());
				String proName = doc.select(".h1").get(0).text();
				Elements elePrice = doc.select(".o-price-n").select("em");
				String proPrice = elePrice.get(0).text() + elePrice.get(1).select("span").text();
				String proDescription = doc.select(".descrition").text() + "规格参数：" + doc.select("div[id=tabCon-descrition]").text();
				System.out.println("商品名称：" + proName + "\n商品价格：" + proPrice + "\n商品描述：" + proDescription);
				
				Elements eleComment = doc.select("div[id=tabCon-zccCon1]").select(".comment-item-box").select(".comment-item");
				System.out.println(eleComment.size() + "条商品评论：");
				for(int i = 0; i < eleComment.size(); i++){
					String comment = eleComment.get(i).select(".detail").select("span").get(1).text();
					System.out.println("第" + (i+1) + "条：" + comment);
				}
				
				//Pattern pattern = Pattern.compile("(?<=评论内容：)([\\s\\S]*?)(?=评论日期：)");
				Pattern pattern = Pattern.compile("(?<=评论内容：</span><span class=\"dd\">)([\\s\\S]*?)(?=</span></p>([\\s\\S]*?)<p><span class=\"dt\">评论日期)");
				Matcher matcher = pattern.matcher(page.html());
				//System.out.println(page.html());
				while(matcher.find()){
					System.out.println(matcher.group().trim());
				}
				
				Elements eleImg = doc.select(".list-h").select("img");
				System.out.println(eleImg.size() + "个图片：");
				for(int i = 0; i < eleImg.size(); i++){
					String imgPath = eleImg.get(i).attr("abs:src");
					System.out.println("第" + (i+1) + "个图片路径：" + imgPath);
				}
				
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
		CrawlerUtil2_macaumarket crawler = new CrawlerUtil2_macaumarket("crawler", "F://TempDownloads/product/");
		crawler.addSeed("https://www.macaumarket.com/product/12388.html");
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