package de.pniehus.odal.logic;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import de.pniehus.odal.utils.DeepCopy;

public class Maintest {
	public static void main(String[] args) throws Exception {
		IndexOfParser parse = new IndexOfParser(true);
		long t = System.currentTimeMillis();
		RemoteFile root = parse.parseURL("http://www.qsl.net/y/yo4tnv//docs/", true, "root");
		
		System.out.println(System.currentTimeMillis() - t);
		final TaskController k = new TaskController("test", false, root, new File("D:/load"));
		k.addMonitor(new TaskMonitor() {
			
			@Override
			public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed) {
					System.out.println("Progress: " + (k.getTotalSize() - sizeLeft) * 100 / k.getTotalSize() + "%");			
			}

			@Override
			public void errorOccured(String errorMessage) {
				System.out.println(errorMessage);				
			}
		});
		
		System.out.println("Files: " + k.getNumberOfFiles() + " Size: " + k.getTotalSize());
		
		
		System.out.println("Start download (yes/no):");
		Scanner s = new Scanner(System.in);
		if(s.next().equals("yes")) k.start();
	}
}
