package de.pniehus.odal.GUI;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;

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
	 */
	public LockableAnimatedPanel(GlassPaneAnimation animation){
		lockLayer = new AnimatedGlassPane(animation);
		super.setLayout(new GridLayout(1, 1));
		super.add(contentLayer, new Integer(0));
		lockLayer.setVisible(false);
		super.add(lockLayer, new Integer(1));
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
	public void add(Component comp, Object contstarints){
		return;
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
