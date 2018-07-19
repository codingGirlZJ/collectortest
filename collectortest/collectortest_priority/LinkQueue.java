package collectortest.collectortest_priority;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class LinkQueue {

	// 已访问的 url 集合
	private static Set visitedUrl = new HashSet();

	// 待访问的 url 集合
	private static PriorityQueue unVisitedUrl = new PriorityQueue();

	// 获得URL队列
	public static PriorityQueue getUnVisitedUrl() {
		return unVisitedUrl;
	}

	// 添加到访问过的URL队列中
	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	// 移除访问过的URL
	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	// 未访问的URL出队列
	public static Object unVisitedUrlDeQueue() {
		return unVisitedUrl.poll();
	}

	// 保证每个 URL 只被访问一次
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url))
			unVisitedUrl.add(url);
	}

	// 获得已经访问的URL数目
	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}
	
	//获得已经访问过的url
	public static Set getVisitedUrl(){
		return visitedUrl;
	}

	// 判断未访问的URL队列中是否为空
	public static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.isEmpty();
	}

}
