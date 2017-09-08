package de.pniehus.odal.logic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Parses index of pages for files
 * @author Phil Niehus
 *
 */
public class IndexOfParser {
	
	private List<String> dirs = new ArrayList<String>();
	public boolean enableSizeFetch = false;
	
	// TODO add error listener support
	
	/**
	 * Size fetching allows to see the total file size before the download is finished
	 * This is really time consuming (average 150-175ms) and therefore only recommended for directories
	 * with a small amount of files
	 * @param enableSizeFetching
	 */
	public IndexOfParser(boolean enableSizeFetching){
		setSizeFetching(enableSizeFetching);
		 System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
	}
	
	/**
	 * Size fetching allows to see the total file size before the download is finnished
	 * This is really time consuming (average 150-175ms) and therefore only recommended for directories
	 * with a scmall amount of files
	 * @param enableSizeFetching
	 */
	public void setSizeFetching(boolean b){
		enableSizeFetch = b;
	}
	
	public RemoteFile parseURL(String url, boolean parseSubdirs,
			String directoryName) throws IOException {
		RemoteFile f = parse(url, parseSubdirs, directoryName, 0);
		dirs.clear();
		return f;
	}
	
	private RemoteFile parse(String url, boolean parseSubdirs,
			String directoryName, int level) throws IOException {
		if (url == null)
			throw new NullPointerException("URLS may not be null");
		dirs.add(url);
		
		RemoteFile tree = new 
		RemoteFile(directoryName, level);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");

		for (Element link : links) {

			String currentLink = link.attr("abs:href");
			if(currentLink.contains("?") || !currentLink.contains(url)) continue;
			
			String name = getResourceName(currentLink);
			
			try {
				long filesize = 0;
				if(enableSizeFetch){
					
					URLInfo linkInfo = getInfo(currentLink);
					if (isDirectory(linkInfo, currentLink)) {
						if (parseSubdirs) {
							if(dirs.contains(currentLink)) continue;
							dirs.add(currentLink);
							RemoteFile sub = parse(currentLink, true, name, level+1);
							if (sub != null && sub.isDirectory())
								tree.add(sub);
						}
						continue;
					}
					filesize = linkInfo.size;
				} else{
					if(lastCharIsSlash(currentLink)){
						// directory or site
						if(parseSubdirs){
							URLInfo linkInfo = getInfo(currentLink);
							if (isDirectory(linkInfo, currentLink)) {
								if(dirs.contains(currentLink)) continue;
								dirs.add(currentLink);
								RemoteFile sub = parse(currentLink, true, name, level+1);
								if (sub != null && sub.isDirectory())
									tree.add(sub);
							}
						}
						continue;
					}
				}
				
				RemoteFileInfo info = new RemoteFileInfo(currentLink,
						filesize);
				
				tree.add(new RemoteFile(name, info, level+1));
			} catch (IOException ioE) {
				continue; // TODO maybe handle better
			}

		}
		return tree;
	}
	
	
	/**
	 * Checks if the last char of a link is a slash
	 * @param link
	 * @return
	 */
	private boolean lastCharIsSlash(String link){
		return (link.charAt(link.length() -1) == '/');
	}
	
	/**
	 * Gets the files or directory's name from a link
	 * @param link
	 * @return
	 * @throws UnsupportedEncodingException Does not happen
	 */
	private String getResourceName(String link) throws UnsupportedEncodingException{
		String[] splitPath = link.split("/");
		String name = splitPath[splitPath.length - 1];
		return java.net.URLDecoder.decode(name, "UTF-8");
	}
	
	/**
	 * Checks if the given link is a directory
	 * 
	 * @param info
	 *            the info associated to the link
	 * @param link
	 * @return
	 */
	private boolean isDirectory(URLInfo info, String link) {
		if (info.mimetype.contains("html") && lastCharIsSlash(link)) {
			try {
				Document doc = Jsoup.connect(link).get();
				if (doc.title().contains("Index of")) {
					return true;
				}
			} catch (IOException e) {
				// TODO handle?
			}
		}
		return false;
	}

	/**
	 * Returns the info for the current url
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	private URLInfo getInfo(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("HEAD");
		connection.connect();
		String contentType = connection.getContentType();
		long size = connection.getContentLengthLong();
		connection.disconnect();
		return new URLInfo(contentType, size);
	}

	/**
	 * wrapper Class for information about a url
	 * 
	 * @author Phil Niehus
	 *
	 */
	private class URLInfo {

		public String mimetype;
		public long size;

		public URLInfo(String mimetype, long size) {
			this.mimetype = mimetype;
			this.size = size;
		}
	}
}
