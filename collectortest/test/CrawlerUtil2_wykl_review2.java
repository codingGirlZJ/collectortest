package collectortest.test;

import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * WebCollector抓取图片的例子
 * 
 * @author hu
 */
public class CrawlerUtil2_wykl_review2 extends BreadthCrawler {

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
	public CrawlerUtil2_wykl_review2(String crawlPath, String downloadPath) {
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

//			String url = "http://www.kaola.com/commentAjax/comment_list.html";
			String url = "https://www.amazon.cn/ss/customer-reviews/ajax/reviews/get/ref=cm_cr_getr_d_paging_btm_2";
			String result = "";
			try {  
	            URL realUrl = new URL(url);  
	            URLConnection conn = realUrl.openConnection();  
	            conn.setRequestProperty("accept", "*/*");  
	            conn.setRequestProperty("connection", "Keep-Alive");  
	            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");  
	            //post设置如下两行  
	            conn.setDoOutput(true);  
	            conn.setDoInput(true);  
	            PrintWriter out = new PrintWriter(conn.getOutputStream());  
//	            out.print("goodsId=1357061&pageNo=1&pageSize=30");  
	            out.print("sortBy=&reviewerType=&formatType=&filterByStar=&pageNumber=2&filterByKeyword=&shouldAppend=undefined&deviceType=desktop&reftag=cm_cr_getr_d_paging_btm_2&pageSize=20&asin=B00ID363S4&scope=reviewsAjax2");  
	            out.flush();  
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
	            String line;  
	            while((line = in.readLine()) != null){  
	                result +="\n" + line;  
//	                System.out.println("\n" + line);
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
			org.jsoup.nodes.Document cDoc = Jsoup.parse(result);

//			String[] all = result.split("\"commentContent\":\"|\",\"commentFeatures\"");
			String txt1 = "class=\\\\\"a-size-base review-text\\\\\">";
			String txt2 = "</span></div><div class=\\\\\"a-row a-spacing-top-small";
			String[] all = result.split(txt1 + "|" + txt2);
			int size = all.length/2;
			System.out.println("评论数量：" + size);
			String[] comments = new String[size];
			for(int i = 1,j = 0; i < all.length; i=i+2,j++){
				comments[j] = all[i];
				System.out.println("第" + (j+1) + "条评论：" + comments[j]);
			}
//			
//			Elements comments = cDoc.select("div[class=eachInfo clearfix]");
//			System.out.println("评论数量：" + comments.size());
//			for (Element comment : comments) {
//				String ckey = UUID.randomUUID().toString();
//				System.out.println(comment.select("span[class=itemDetail]").text());
//				String content = comment.select("span[class=itemDetail]").text();
//
//				Elements cImgs = comment.select("em");
//				System.out.println("评论图片：");
//				for (Element cImg : cImgs) {
//					System.out.println("http:" + cImg.text());
//					String imgPath = "http:" + cImg.text();
//					int index = imgPath.indexOf("?");
//					imgPath = imgPath.substring(0, index);
//				}
//			}
		}
	}

	private String getWebSource(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String temp, result = "";
		try {
			while (null != (temp = reader.readLine())) {
				result += temp;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
		CrawlerUtil2_wykl_review2 crawler = new CrawlerUtil2_wykl_review2("crawler", "F://TempDownloads/product/");
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