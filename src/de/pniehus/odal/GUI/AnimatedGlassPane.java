package de.pniehus.odal.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimatedGlassPane extends JPanel{
	
	private boolean run = false;
	private JLabel image;
	private GlassPaneAnimation animation;
	// TODO add text feature
	
	/**
	 * Creates an animated Glasspane with the given animation
	 * @param animation
	 */
	public AnimatedGlassPane(GlassPaneAnimation animation){
		this.animation = animation;
		setLayout(new GridLayout());
		image = new JLabel(new ImageIcon(animation.getImages()[0]));
		add(image);
	}
	
	/**
	 * Starts the animation
	 */
	public void start(){
		if(run == false){
			run = true;
			setVisible(true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(run){
						for(int i = 0; i < animation.getImages().length; i++){
							image.setIcon(new ImageIcon(animation.getImages()[i]));
							try {
								Thread.sleep(animation.getDelay());
							} catch (InterruptedException e) {
								// TODO no handling necessary/useful ?
							}
						}
					}
					
				}
			}).start();
		}
	}
	
	/**
	 * Stops the animation
	 */
	public void stop(){
		if(run){
			run = false;
		}
	}
	
	/**
	 * Returns true if the animation is running
	 * @return
	 */
	public boolean isRunning(){
		return run;
	}
	
	/**
	 * Draws the panel with opacity
	 */
	@Override
	public void paintComponent(Graphics g){
		setOpaque(true);
		setBackground(new Color(64, 64, 64, 120));
		super.paintComponent(g);
		setOpaque(false);
	}
	
}
