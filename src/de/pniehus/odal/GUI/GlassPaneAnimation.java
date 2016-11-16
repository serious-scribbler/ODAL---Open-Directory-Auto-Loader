package de.pniehus.odal.GUI;

import java.awt.Image;

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
}
