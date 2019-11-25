import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import jxl.write.WritableWorkbook;

public class MyCrawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v" +
		    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
		/**
		* This method receives two parameters. The first parameter is the page
		* in which we have discovered this new url and the second parameter is
		* the new url. You should implement this function to specify whether
		* the given url should be crawled or not (based on your crawling logic).
		* In this example, we are instructing the crawler to ignore urls that
		* have css, js, git, ... extensions and to only accept urls that start
		* with "http://www.viterbi.usc.edu/". In this case, we didn't need the
		* referringPage parameter to make the decision.
		*/
		 @Override
		 public boolean shouldVisit(Page referringPage, WebURL url) {
			 String href = url.getURL().toLowerCase();
			 BufferedWriter writer;
				try {
					 writer = new BufferedWriter(new FileWriter("c:/data/crawl/urls_BBC.csv", true));
					 writer.newLine();   //Add new line
					 writer.write(href);
					 writer.write(",");
					 if(href.contains("bbc.com/")) {
						 writer.write("OK,,,,");
					 }
					 else {
						 writer.write("N_OK,,,,");
					 }
					 writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				/*PageFetcher pageFetcher;
				CrawlController crawlController = getMyController();
				pageFetcher = crawlController.getPageFetcher();
				PageFetchResult fetchResult = null;		
			    try {
					fetchResult = pageFetcher.fetchPage(url);
				} catch (InterruptedException | IOException | PageBiggerThanMaxSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
					String contentType = fetchResult.getEntity().getContentType().toString();
					*/
					String contentType = referringPage.getContentType();
					
					/*if(url.toString().contains("banner-")) {
						System.out.println("found it, url is: " + url.toString() +"contentType is "+contentType +"\n");
						
					}
					if(contentType != null && contentType.contains("image"))
					{
						System.out.println("image url is " + url.toString()+ "\n");
						
					}*/
					
					//if(contentType!= null) {
						
						if(contentType.contains("text/html")||contentType.contains("image/png")||contentType.contains("image/jpg")
								||contentType.contains("image/jpeg") ||contentType.contains("image/bmp") ||contentType.contains("image/gif")
								||contentType.contains("image/tiff")||contentType.contains("image/webp")||contentType.contains("application/pdf")
								||contentType.contains("application/msword")||contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
								||url.toString().endsWith(".jpg")||url.toString().endsWith(".pdf")||url.toString().endsWith(".jpeg")||url.toString().endsWith(".gif"))
						{
							if(href.contains("bbc.com/")) {
								
								return true;
							}		
							else {
									return false;
							}
					
						}
						
				  //}
				
					
				
				return false;
				
			
							
	}
			 
	/**
	* This function is called when a page is fetched and ready
	* to be processed by your program.
	*/
	@Override
	public void visit(Page page) {
		BufferedWriter writer;
		String url = page.getWebURL().getURL();
		//System.out.println("header " + page.getFetchResponseHeaders().toString() +"/n");
		//System.out.println("type " + page.getContentType()+"/n");
		
		//System.out.println("URL: " + url);
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			//System.out.println("Text length: " + text.length());
			//System.out.println("Html length: " + html.length());
			//System.out.println("Number of outgoing links: " + links.size());
		
				try {
					 writer = new BufferedWriter(new FileWriter("c:/data/crawl/visit_BBC.csv", true));
					 writer.newLine();   //Add new line
					 writer.write(url);
					 writer.write(",");
					 writer.write(Integer.toString(html.length()));
					 writer.write(",");
					 writer.write(Integer.toString(links.size()));
					 writer.write(",");
					 String contentType = page.getContentType();
					// writer.write(contentType.substring(0, contentType.indexOf(';')));
					 writer.write(contentType + ",,,,");
					 writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
	}
		
	
}
	
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
		BufferedWriter writer;
		
		try {
		writer = new BufferedWriter(new FileWriter("c:/data/crawl/fetch_BBC.csv", true));
		writer.newLine();   //Add new line
		writer.write(webUrl.toString());
		writer.write(",");
		writer.write(String.valueOf(statusCode) + ",,,,");
		writer.close();
	}
		catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
}
	@Override
	 protected WebURL handleUrlBeforeProcess(WebURL curURL) {
		    String newURL = curURL.toString().replace(",", "-");
		    curURL.setURL(newURL);
	        return curURL;
	    }

	
	
}
