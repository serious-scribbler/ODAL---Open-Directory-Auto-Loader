package de.pniehus.odal.GUI;

import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This class can be used to display animations on {@link AnimatedGlassPane}
 * @author Phil Niehus
 */
public class GlassPaneAnimation {
	
	private Image[] images;
	private long msBetweenImages;
	
	public GlassPaneAnimation(Image[] images, long msBetweenImages){
		if(images == null || images.length < 1 || msBetweenImages == 0) throw new IllegalArgumentException("Invalid image or delay!");
		this.images = images;
		this.msBetweenImages = msBetweenImages;
	}
	
	public long getDelay(){
		return msBetweenImages;
	}
	
	public Image[] getImages(){
		return images;
	}
	
	/**
	 * Loads all png images from the specified directory to an image array
	 * @param dir
	 * @return
	 * @throws IOException gets thrown when the method is unable to read the files
	 */
	public static Image[] readImagesFromDirectory(File dir) throws IOException{
		if(dir == null || !dir.isDirectory() || !dir.canRead()) throw new IOException("invalid input directory");
		List<Image> imgs = new ArrayList<Image>();
		
		for(File f : dir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getAbsolutePath().endsWith(".png");
			}
		})){
			imgs.add(ImageIO.read(f));
		}
		Image[] loaded;
		return imgs.toArray(new Image[]{}); // Creates a new array, because the give one is to small
	}
}
