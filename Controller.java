import edu.uci.ics.crawler4j.crawler.CrawlConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "/data/crawl";
		int numberOfCrawlers =5;
		int maxDepthOfCrawling = 16;
		int maxPagesToFetch = (20000);
		int politnessDelay = 300;
		//String userAgentString = "A";
		
		CrawlConfig config1 = new CrawlConfig();
		
		config1.setCrawlStorageFolder(crawlStorageFolder + "/crawler1");
		
	    config1.setIncludeBinaryContentInCrawling(true);
	    config1.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config1.setMaxPagesToFetch(maxPagesToFetch);
		config1.setPolitenessDelay(politnessDelay);
		config1.setCrawlStorageFolder(crawlStorageFolder);
		config1.setIncludeHttpsPages(true);
	
		//config1.setUserAgentString(userAgentString);
		/*
		* Instantiate the controller for this crawl.
		*/
		PageFetcher pageFetcher1 = new PageFetcher(config1);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher1);
		
		CrawlController controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer);

		/*
		* For each crawl, you need to add some seed urls. These are the first
		* URLs that are fetched and then the crawler starts following links
		* which are found in these pages
		*/
		controller1.addSeed("https://www.bbc.com/");
		
		
		/*
		* Start the crawl. This is a blocking operation, meaning that your code
		* will reach the line after this only when crawling is finished.
		*/
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("c:/data/crawl/fetch_BBC.csv", true));
			 writer.write("URL");
			 writer.write(",");
			 writer.write("Status,,,,");
			 writer.close();
			 writer = new BufferedWriter(new FileWriter("c:/data/crawl/urls_BBC.csv", true));
			 writer.write("URL");
			 writer.write(",");
			 writer.write("Resides?,,,,");
			 writer.close();
			 writer = new BufferedWriter(new FileWriter("c:/data/crawl/visit_BBC.csv", true));
			 writer.write("URL");
			 writer.write(",");
			 writer.write("Size");
			 writer.write(",");
			 writer.write("Outgoing Links");
			 writer.write(",");
			 writer.write("Content type,,,,");
			 writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller1.start(MyCrawler.class, numberOfCrawlers);
		//controller1.startNonBlocking(MyCrawler.class, numberOfCrawlers);
        
        //controller1.waitUntilFinish();
        //logger.info("Crawler 1 is finished.");

        /*
        List<Object> crawlersLocalData1 = controller1.getCrawlersLocalData();
        long totalLinks = 0;
        long totalTextSize = 0;
        int totalProcessedPages = 0;
        for (Object localData : crawlersLocalData1) {
            CrawlStatistics stat = (CrawlStatistics) localData;
            totalLinks += stat.getTotalLinks();
            totalTextSize += stat.getTotalTextSize();
            totalProcessedPages += stat.getTotalProcessedPages();
        }


        logger.info("Aggregated Statistics:");
        logger.info("\tProcessed Pages: {}", totalProcessedPages);
        logger.info("\tTotal Links found: {}", totalLinks);
        logger.info("\tTotal Text Size: {}", totalTextSize);
        */
	}

}

