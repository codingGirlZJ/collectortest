package collectortest.test;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * WebCollector抓取图片的例子
 * @author hu
 */
public class DemoImageCrawler_qiegaowz extends BreadthCrawler {

    //用于保存图片的文件夹
    File downloadDir;

    //原子性int，用于生成图片文件名
    AtomicInteger imageId;

    /**
     * 
     * @param crawlPath 用于维护URL的文件夹
     * @param downloadPath 用于保存图片的文件夹
     */
    public DemoImageCrawler_qiegaowz(String crawlPath, String downloadPath) {
        super(crawlPath, true);
        downloadDir = new File(downloadPath);
        if(!downloadDir.exists()){
            downloadDir.mkdirs();
        }
        computeImageId();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
//    	System.out.println(page.html());
        //根据http头中的Content-Type信息来判断当前资源是网页还是图片
        String contentType = page.getResponse().getContentType();
        if(contentType==null){
            return;
        }else if (contentType.contains("html")) {
            //如果是网页，则抽取其中包含图片的URL，放入后续任务
            Elements imgs = page.select("img[src$=.jpg]");
            for (Element img : imgs) {
                String imgSrc = img.attr("abs:src");
                System.out.println("图片路径：" + imgSrc);
                next.add(imgSrc);
            }

        } else if (contentType.startsWith("image") && page.getUrl().matches("http://www.qiegaowz.com/Content/UploadFiles/[0-9]{15}\\.jpg")) {
            //如果是图片，且符合要抓取的商品规则的，下载
        	System.out.println("要下载");
            String extensionName=contentType.split("/")[1];
            String imageFileName=imageId.incrementAndGet()+"."+extensionName;
            File imageFile=new File(downloadDir,imageFileName);
            try {
                FileUtils.writeFile(imageFile, page.getContent());
                System.out.println("保存图片 "+page.getUrl()+" 到 "+imageFile.getAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static void main(String[] args) throws Exception {
        DemoImageCrawler_qiegaowz demoImageCrawler = new DemoImageCrawler_qiegaowz("crawl", "download");
        //添加种子URLhttp://blog.csdn.net/
        demoImageCrawler.addSeed("http://www.qiegaowz.com/product/Default.html");
       // demoImageCrawler.addSeed("http://blog.csdn.net/");
        //限定爬取范围http://news.hfut.edu.cn   http://www.meishij.net/
        demoImageCrawler.addRegex("http://www.qiegaowz.com/product/.*");
        //设置为断点爬取，否则每次开启爬虫都会重新爬取
        demoImageCrawler.setResumable(false);
        demoImageCrawler.setThreads(5);
        Config.MAX_RECEIVE_SIZE = 1000 * 1000 * 10;
        demoImageCrawler.start(3);
    }

    public void computeImageId(){
        int maxId=-1;
        for(File imageFile:downloadDir.listFiles()){
            String fileName=imageFile.getName();
            String idStr=fileName.split("\\.")[0];
            int id=Integer.valueOf(idStr);
            if(id>maxId){
                maxId=id;
            }
        }
        imageId=new AtomicInteger(maxId);
    }

}