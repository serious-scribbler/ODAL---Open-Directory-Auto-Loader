package de.pniehus.odal.tools;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class Maintest {
	public static void main(String[] args) throws IOException {
		IndexOfParser parse = new IndexOfParser(false);
		long t = System.currentTimeMillis();
		RemoteFile root = parse.parseURL("http://vuduberi.com/journal/", true, "root");
		System.out.println(System.currentTimeMillis() - t);
		final TaskController k = new TaskController("test", false, root, new File("D:/load"));
		k.addMonitor(new TaskMonitor() {
			
			@Override
			public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed) {
					System.out.println("Progress: " + (k.getTotalSize() - sizeLeft) * 100 / k.getTotalSize() + "%");			
			}
		});
		System.out.println("Files: " + k.getNumberOfFiles() + " Size: " + k.getTotalSize());
		System.out.println("Start download (yes/no):");
		Scanner s = new Scanner(System.in);
		if(s.next().equals("yes")) k.start();
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
