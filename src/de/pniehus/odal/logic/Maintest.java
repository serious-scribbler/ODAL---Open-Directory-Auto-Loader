package de.pniehus.odal.logic;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.pniehus.odal.GUI.AnimatedGlassPane;
import de.pniehus.odal.GUI.GlassPaneAnimation;
import de.pniehus.odal.GUI.LockableAnimatedPanel;
import de.pniehus.odal.utils.DeepCopy;

public class Maintest {
	public static void main(String[] args) throws Exception {
		IndexOfParser parse = new IndexOfParser(false);
		JFrame test = new JFrame("Running");
		test.setSize(new Dimension(1280, 720));
		LockableAnimatedPanel lockable = new LockableAnimatedPanel(new GlassPaneAnimation(GlassPaneAnimation.readImagesFromDirectory(new File("C:/Users/Phil/Desktop/anim test")), 500l));
		lockable.getContentPane().setLayout(new GridLayout(1, 3));
		JButton st = new JButton("Start");
		
		lockable.getContentPane().add(st);
		lockable.getContentPane().add(new JButton("Stop"));
		lockable.getContentPane().add(new JButton("Nothing"));
		test.add(lockable);
//		AnimatedGlassPane anim = new AnimatedGlassPane(new GlassPaneAnimation(GlassPaneAnimation.readImagesFromDirectory(new File("C:/Users/Phil/Desktop/anim test")), 500l));
//		test.add(anim);
		test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		test.setVisible(true);
		Thread.sleep(1000);
		st.setEnabled(false);
		Thread.sleep(2000);
		long t = System.currentTimeMillis();
		lockable.lock();
//		anim.start();
		RemoteFile root = parse.parseURL("http://www.qsl.net/y/yo4tnv//docs/", true, "root");
		
		System.out.println(System.currentTimeMillis() - t);
		lockable.unlock();
//		anim.stop();
		
		final TaskController k = new TaskController("test", true, root, new File("D:/load"));
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
		test.setVisible(false);
		test.dispose();
	}
}
