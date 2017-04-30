package de.pniehus.odal.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Behaves like a normal JPanel, but has the ability to be locked.
 * When the panel is locked, an overlay with a customizable animation
 * is displayed
 * @author Phil Niehus
 *
 */
public class LockableAnimatedPanel extends JLayeredPane{
	
	private AnimatedGlassPane lockLayer;
	private JPanel contentLayer = new JPanel();
	
	/**
	 * Creates a lockable panel with a lock animation
	 * @param animation The animation to use
	 * @throws IOException 
	 */
	public LockableAnimatedPanel(GlassPaneAnimation animation) throws IOException{
		if(animation == null) animation = new GlassPaneAnimation(GlassPaneAnimation.readImagesFromDirectory(new File("C:/Users/Phil/Desktop/anim test")), 500l); // TODO remove
		lockLayer = new AnimatedGlassPane(animation);
		contentLayer.setVisible(true);
		super.add(contentLayer, new Integer(0));
		lockLayer.setVisible(false);
		super.add(lockLayer, new Integer(1));
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				contentLayer.setBounds(0, 0, getWidth(), getHeight());
				lockLayer.setBounds(0, 0, getWidth(), getHeight());		
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				contentLayer.setBounds(0, 0, getWidth(), getHeight());
				lockLayer.setBounds(0, 0, getWidth(), getHeight());
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				
				
			}
		});
	}
	
	/**
	 * Prevents layout fails by blocking layout changes on the underlying JLayeredPane
	 */
	@Override
	public void setLayout(LayoutManager mgr){
		return;
	}
	
	/**
	 * Prevents adding components to the underlying JLayeredPane
	 * @return 
	 */
	@Override
	public void add(Component comp, Object constraints){
		contentLayer.add(comp, constraints);
	}
	
	/**
	 * Returns the JPanel that hold any visible content
	 * All Components that should be visible and any operations applying to that
	 * should be performed on this panel
	 * @return
	 */
	public JPanel getContentPane(){
		return contentLayer;
	}
	
	/**
	 * This method can be used to switch content panes
	 * @param panel
	 */
	public void setContentPane(JPanel panel){
		contentLayer = panel;
	}
	
	/**
	 * Locks the lockable panel and displays the animation
	 */
	public void lock(){
		if(!lockLayer.isRunning()){
			lockLayer.setVisible(true);
			lockLayer.start();
		}
	}
	
	/**
	 * Unlocks the panel and hides the animation
	 */
	public void unlock(){
		if(lockLayer.isRunning()){
			lockLayer.stop();
			lockLayer.setVisible(false);
		}
	}
	
	/**
	 * Returns true, if the panel is locked
	 * @return
	 */
	public boolean isLockes(){
		return lockLayer.isRunning();
	}
	
}
