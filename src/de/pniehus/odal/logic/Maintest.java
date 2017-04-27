package de.pniehus.odal.logic;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaParam;

import de.pniehus.odal.GUI.AnimatedGlassPane;
import de.pniehus.odal.GUI.GlassPaneAnimation;
import de.pniehus.odal.GUI.LockableAnimatedPanel;
import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.GUI.RescaleGridConstraints;
import de.pniehus.odal.GUI.RescaleGridLayout;
import de.pniehus.odal.utils.DeepCopy;
import de.pniehus.odal.utils.Filter;

public class Maintest {
	public static void main(String[] args) throws Exception {
		
		/* TEST 1
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
		RemoteFile root = parse.parseURL("http://entropy.soldierx.com/~kayin/archive/", true, "root");
		
		System.out.println(System.currentTimeMillis() - t);
		lockable.unlock();
//		anim.stop();
		
		final TaskController k = new TaskController("test", true, root, new File("D:/load/"));
		k.addMonitor(new TaskMonitor() {
			
			@Override
			public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed) {
					System.out.println("Files left: " + filesLeft);			
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
		
		*/
		
		/* TEST 2
		JFrame testing = new JFrame("Testing");
		testing.setSize(1280, 720);
		JPanel rescaleGridTest = new JPanel(new RescaleGridLayout(testing.getSize(), 16, 9, 0.5f, 0.5f));
		
		rescaleGridTest.add(new JButton("Oben, ganze breite"), new RescaleGridConstraints(0, 0, 17, 1));
		rescaleGridTest.add(new JButton("3te Reihe, viertel"), new RescaleGridConstraints(0, 2, 4, 1));
		rescaleGridTest.add(new JButton("2te Reihe, 2tes viertel"), new RescaleGridConstraints(4, 1, 8, 2));
		rescaleGridTest.add(new JButton("1x1"), new RescaleGridConstraints(0, 1, 1f, 1f, 0.5f, 0.5f));
		rescaleGridTest.add(new JButton("0.5²"), new RescaleGridConstraints(1, 1, 0.5f, 0.5f, 0f, 0.5f));
		
		rescaleGridTest.add(new JButton("+"), new RescaleGridConstraints(1, 1, 1f, 0.25f, 0f, 0f));
		rescaleGridTest.add(new JButton("-"), new RescaleGridConstraints(1, 1, 1f, 0.25f, 0f, 1f));
		
		testing.add(rescaleGridTest);
		testing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testing.setVisible(true);
		
		*/
		
		/* TEST 3
		if(args.length != 2){
			System.out.println("usage: odal.jar <path> <url>");
			System.exit(0);
		}
		IndexOfParser parser = new IndexOfParser(false);
		RemoteFile root = parser.parseURL(args[1], true, "root");
		TaskController control = new TaskController("task", true, root, new File(args[0]));
		control.addMonitor(new TaskMonitor() {
			
			@Override
			public void errorOccured(String errorMessage) {
				System.out.println("ERROR: " + errorMessage);				
			}
			
			@Override
			public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed) {
				System.out.println("Files left: " + filesLeft + " Elapsed time: " + ((double) timeElapsed)/1000 + "s");				
			}
		});
		Scanner s = new Scanner(System.in);
		System.out.println("Found " + RemoteFile.countFiles(root) + " files! Download (yes/no)?");
		String in = s.next();
		if(in.equalsIgnoreCase("yes") || in.equalsIgnoreCase("y")){
			control.start();
		}*/
		
		OdalGui ogui = new OdalGui(args, new ArrayList<Filter>());
	}
}
