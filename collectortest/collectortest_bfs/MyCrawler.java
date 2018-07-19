package collectortest.collectortest_bfs;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCrawler {

	/**
	 * 使用种子初始化URL队列
	 * 
	 * @param seeds
	 */
	private void initCrawlerWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	/**
	 * 抓取过程
	 * 
	 * @param seeds
	 */
	public void crawling(String[] seeds) {
		// 定义过滤器。提取指定url开头的链接
		LinkFilter filter = new LinkFilter() {
			public boolean accept(String url) {
				Pattern pattern = Pattern.compile("^http://www.hubu.edu.cn/info/1246/2597\\d*.htm$");
				Matcher matcher = pattern.matcher(url);
				if (matcher.find()) {
					return true;
				} else {
					return false;
				}
			}
		};

		// 初始化URL队列
		initCrawlerWithSeeds(seeds);
		// 循环条件：待抓取的链接不空且抓取的网页不多于1000
		while (!LinkQueue.unVisitedUrlsEmpty() && LinkQueue.getVisitedUrlNum() <= 1000) {

			// 队头URL出队列
			String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
			if (visitUrl == null) {
				continue;
			}
			DownLoadFile downLoader = new DownLoadFile();

			// 下载网页
			downLoader.downloadFile(visitUrl);

			// 该URL放入已访问的URL中
			LinkQueue.addVisitedUrl(visitUrl);

			System.out.println(visitUrl);
			System.out.println(LinkQueue.getVisitedUrlNum());

			// 提取出下载网页中的URL
			Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);

			// 新的未访问的URL入队
			for (String link : links) {
				if (!LinkQueue.getUnVisitedUrl().contians(link)) {
					LinkQueue.addUnvisitedUrl(link);
				}
			}
		}
	}

	// main 方法入口
	public static void main(String[] args) {
		MyCrawler crawler = new MyCrawler();
		crawler.crawling(new String[] { "http://www.hubu.edu.cn/" });
		System.out.println(LinkQueue.getVisitedUrl());
	}
}
