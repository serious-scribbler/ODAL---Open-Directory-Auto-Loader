package de.pniehus.odal.utils.filters;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.logic.RemoteFile;
import de.pniehus.odal.utils.Filter;
import de.pniehus.odal.utils.OdalTextBox;

/**
 * Filters a RemoteFile structure by keyword
 * @author Phil Niehus
 *
 */
public class KeywordFilter extends Filter{
	

	private List<String> keywords = null; // This list contains all keywords
	
	public KeywordFilter() {
		super("Keyword");
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
		for(String kw : keywords){
			if(fileName.contains(kw)){
				match = true;
				break;
			}
		}
		if(!match){
			filetree.removeFromParent();
			return true;
		}
		return false;
	}
	
	@Override
	public String getHelpText() {
		return "selects all files whose names contain at least one of the given keywords.\n\nParameters: Comma separated list of keywords.";
	}

	@Override
	public void setUp(String params) {
		if(params == null) return;
		String[] keywords = params.split(",");
		for(int i = 0; i < keywords.length; i++){
			keywords[i] = keywords[i].toLowerCase();
		}
		this.keywords = Arrays.asList(keywords);
	}
	
	private class TypeSettings extends AbstractWindow{
		
		public TypeSettings(OdalGui o){
			super("Keyword Filter - Settings");
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
			
			p.addComponent(new Label("Selects all files whose names contain at least one of the given keywords. Seperate keywords by comma. For Example: car,house,manual"), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(new Label("Enter your keywords:"), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(keywordBox, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			p.addComponent(ready, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			setComponent(p);
		}
	}
}
