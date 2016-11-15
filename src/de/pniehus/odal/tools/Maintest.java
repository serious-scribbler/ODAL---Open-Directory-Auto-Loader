package de.pniehus.odal.tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Maintest {
	public static void main(String[] args) throws MalformedURLException {
		RemoteFile root = new RemoteFile("root");
		root.add(new RemoteFile("lamefile", new RemoteFileInfo("http://example.com", 1024l)));
		root.add(new RemoteFile("coolfile", new RemoteFileInfo("http://example.com", 1024l*1024l)));
		root.add(new RemoteFile("largefile", new RemoteFileInfo("http://example.com", 1024l*1024l*120l)));
		RemoteFile subfile = new RemoteFile("subdir");
		subfile.add(new RemoteFile("littlefile", new RemoteFileInfo("http://example.com", 10l)));
		root.add(subfile);
		System.out.println("treeSize: " + RemoteFile.countSize(root));
		System.out.println("Number of files: " + RemoteFile.countFiles(root));
		
	}
	
	private static int getFileSize(URL url) {
	    HttpURLConnection conn = null;
	    try {
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        return conn.getContentLength();
	    } catch (IOException e) {
	        return -1;
	    } finally {
	        conn.disconnect();
	    }
	}
}
