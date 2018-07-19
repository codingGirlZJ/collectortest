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
public class CrawlerUtil2_wykl_product extends BreadthCrawler {

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
	public CrawlerUtil2_wykl_product(String crawlPath, String downloadPath) {
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
		if (page.url().matches("^http://www.kaola.com/product/.*\\.html")) {

			org.jsoup.nodes.Document document = page.doc();
			Elements eles = document.getElementsByTag("script");
			String script8 = eles.get(8).data();
			// System.out.println(eles.size());
			String script7 = eles.get(7).data();
			// System.out.println(script1);
			String proName = document.select(".product-title").text();	//商品名称
			String[] spl8 = script8.split("name:|price:|is_self:");
			int price_index1 = spl8[2].indexOf("'");
			int price_index2 = spl8[2].lastIndexOf("'");
			String proPrice = spl8[2].substring(price_index1 + 1, price_index2);	//商品价格
			String proDescription = page.select(".goods_parameter").text();		//商品描述
			String address = null;
			String seller = null;
			String shopName =null;
			String[] spl7 = script7
					.split("\"shopName\":|\"shopType\":|\"warehouseNameAlias\":|warehouseCityShow:|baoyouTipSwitch:");
			if (spl7.length > 4) {	//有卖家店铺信息
				int sell_index1 = spl7[3].indexOf("\"");
				int sell_index2 = spl7[3].lastIndexOf("\"");
				seller = spl7[3].substring(sell_index1 + 1, sell_index2);	//发货仓库
				int address_index1 = spl7[4].indexOf("'");
				int address_index2 = spl7[4].lastIndexOf("'");
				address = spl7[4].substring(address_index1 + 1, address_index2);	//发货地
				int shop_index1 = spl7[1].indexOf("\"");
				int shop_index2 = spl7[1].lastIndexOf("\"");
				shopName = spl7[1].substring(shop_index1 + 1, shop_index2);		//卖家店铺

			}else{	//自营
				spl7 = script7
						.split("\"warehouseNameAlias\":|warehouseCityShow:|baoyouTipSwitch:");
				int sell_index1 = spl7[1].indexOf("\"");
				int sell_index2 = spl7[1].lastIndexOf("\"");
				seller = spl7[1].substring(sell_index1 + 1, sell_index2);	//发货仓库
				
			}
			
			String commentLink = document.select("a[class=reviewComment f-hkdn]").attr("abs:href");		//全部评论链接
			System.out.println("商品名称：" + proName + "\n商品价格：" + proPrice + "\n商品描述：" + proDescription + "\n卖家店铺："
					+ shopName + "\n发货地：" + address + "\n发货仓库：" + seller + "\n评论链接：" + commentLink);

			Elements imgs = document.select("ul[id=litimgUl]").select("img");
			System.out.println("商品图片：");
			for(Element img : imgs){
				System.out.println(img.attr("abs:src"));
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
		CrawlerUtil2_wykl_product crawler = new CrawlerUtil2_wykl_product("crawler", "F://TempDownloads/product/");
		crawler.addSeed("http://www.kaola.com/product/1415839.html");
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