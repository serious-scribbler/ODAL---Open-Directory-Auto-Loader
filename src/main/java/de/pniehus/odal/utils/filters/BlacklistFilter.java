package de.pniehus.odal.utils.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.logic.RemoteFile;
import de.pniehus.odal.utils.Filter;
import de.pniehus.odal.utils.OdalTextBox;

/**
 * Removes files whose names contain blacklisted words
 * @author Phil Niehus
 *
 */
public class BlacklistFilter extends Filter{
	

	private List<String> blacklist = null; // This list contains all keywords
	
	public BlacklistFilter() {
		super("Blacklist");
	}

	@Override
	public AbstractWindow getSettingUI(OdalGui og) {
		return new TypeSettings(og);
	}	
	
	/**
	 * This method does the actual filtering
	 * @param filetree
	 * @return True if removed
	 */
	@Override
	public boolean applyFilter(RemoteFile filetree){
		String fileName = filetree.getName().toLowerCase();
		boolean match = false;
		for(String kw : blacklist){
			if(fileName.contains(kw)){
				match = true;
				break;
			}
		}
		if(match){
			filetree.removeFromParent();
			return true;
		}
		return false;
	}
	
	@Override
	public String getHelpText() {
		return "removes all files whose names contain at least one of the given keywords.\n\nParameters: Comma separated list of blacklist keywords.";
	}

	@Override
	public void setUp(String params) {
		if(params == null) return;
		String[] keywords = params.split(",");
		for(int i = 0; i < keywords.length; i++){
			keywords[i] = keywords[i].toLowerCase();
		}
		this.blacklist = Arrays.asList(keywords);
	}
	
	private class TypeSettings extends AbstractWindow{
		
		public TypeSettings(OdalGui o){
			super("Blacklist Filter - Settings");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			
			final OdalGui odal = o;
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			
			final OdalTextBox keywordBox = new OdalTextBox(new TerminalSize(70, 1), TextBox.Style.SINGLE_LINE);
			Button ready = new Button("OK", new Runnable() {
				
				@Override
				public void run() {
					setUp(keywordBox.getText());
					odal.configureNextFilter();
				}
			});
			
			p.addComponent(new Label("Removes all files whose name contains at least one of the given keywords. Seperate keywords by comma. For Example: car,house,manual"), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(new Label("Enter your keywords:"), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(keywordBox, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			p.addComponent(ready, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			setComponent(p);
		}
	}
}
