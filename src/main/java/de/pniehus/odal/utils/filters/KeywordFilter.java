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
 * Filters a RemoteFile structure by a set of keywords
 * @author Phil Niehus
 *
 */
public class KeyWordFilter extends Filter{
	
	private List<String> exclude = new ArrayList<String>();
	private List<String> include = new ArrayList<String>();
	
	public KeyWordFilter() {
		super("Keyword");
		setSampleConfig("cake,recipe,!flour");
	}

	@Override
	public AbstractWindow getSettingUI(OdalGui og) {
		return new TypeSettings(og);
	}	
	
	/**
	 * This method checks if the given file name contains one or more keywords and no keyword from a blacklist
	 * @param filetree
	 * @return True if removed
	 */
	@Override
	public boolean applyFilter(RemoteFile filetree){
		String fileName = filetree.getName();
		boolean match = false;
		for(String s : include){
			if(fileName.contains(s)){
				match = true;
				break;
			}
		}
		for(String s : exclude){
			if(fileName.contains(s)){
				filetree.removeFromParent();
				return true; // Contains a keyword from the blacklist -> removed
			}
		}
		
		if(match){
			return false; // Keyword is contained, not removing
		} else{
			if(include.size() == 0) return false;
			filetree.removeFromParent();
			return true; // keyword not contained removing
		}
	}
	
	@Override
	public String getHelpText() {
		return "Filters file names for a set of key words.\n\nParameters: Comma separated keywords, a ! at the beginning of a keyword means, that it may not be included in file names.\nExamples:\n'!fish,blue,red' (without ' ) would leave 'bluecow.jpg' and 'redbird.jpg' in the filestructure and would remove 'bluefish.jpg' from the file structure.";
	}

	@Override
	public void setUp(String params) {
		if(params == null) return;
		String[] list = params.split(",");
		for(int i = 0; i < list.length; i++){
			if(list[i].startsWith("!")){
				list[i] = list[i].replaceFirst("!", "");
				exclude.add(list[i]);
			} else{
				include.add(list[i]);
			}
		}
	}
	
	private class TypeSettings extends AbstractWindow{ // TODO modify for keyword filter, test filter
		
		public TypeSettings(OdalGui o){
			super("Keyword Filter - Settings");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			
			final OdalGui odal = o;
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			
			final OdalTextBox typeBox = new OdalTextBox(new TerminalSize(70, 1), TextBox.Style.SINGLE_LINE);
			Button ready = new Button("OK", new Runnable() {
				
				@Override
				public void run() {
					setUp(typeBox.getText());
					odal.configureNextFilter();
				}
			});
			
			p.addComponent(new Label("Seperate keywords by comma. For Example: cake,recipe,flour"), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(new Label("A ! as first charcter of a keyword leads to the removal of files whose names contain the specific keyword."), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(new Label("Enter your keywords:"), LinearLayout.createLayoutData(LinearLayout.Alignment.Beginning));
			p.addComponent(typeBox, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			p.addComponent(ready, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			setComponent(p);
		}
	}
}
