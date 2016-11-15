package de.pniehus.odal.tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class IndexOfParser {
	
	public RemoteFile parse(String url, boolean parseSubdirs) throws IOException{
		if(url == null) throw new NullPointerException("URLS may not be null");
		
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		
		return null;
	}
	
	/**
	 * Returns the info for the current url
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	private URLInfo getInfo(String urlString) throws IOException{
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
		connection.setRequestMethod("HEAD");
		connection.connect();
		String contentType = connection.getContentType();
		long size = connection.getContentLengthLong();
		connection.disconnect();
		return new URLInfo(contentType, size);
	}
	
	/**
	 * wrapper Class for information about a url
	 * @author Phil Niehus
	 *
	 */
	private class URLInfo{
		
		public String mimetype;
		public long size;
		
		public URLInfo(String mimetype, long size){
			this.mimetype = mimetype;
			this.size = size;
		}
	}
}
