package de.pniehus.odal.tools;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Maintest {
	public static void main(String[] args) throws IOException {
		RemoteFile root = new RemoteFile("root");
		root.add(new RemoteFile("lamefile.pdf", new RemoteFileInfo("CENSORED", 1024l)));
		root.add(new RemoteFile("coolfile.pdf", new RemoteFileInfo("CENSORED", 1024l)));
		RemoteFile subfile = new RemoteFile("subdir");
		subfile.add(new RemoteFile("littlefile.pdf", new RemoteFileInfo("CENSORED", 1024l)));
		root.add(subfile);
		
		final TaskController k = new TaskController("test", true, root, new File("D:/load"));
		k.addMonitor(new TaskMonitor() {
			
			@Override
			public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed) {
					System.out.println("Progress: " + (k.getTotalSize() - sizeLeft) * 100 / k.getTotalSize() + "%");			
			}
		});
		k.start();

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
