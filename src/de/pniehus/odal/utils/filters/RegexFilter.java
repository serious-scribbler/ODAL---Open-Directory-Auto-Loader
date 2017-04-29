package de.pniehus.odal.utils.filters;

import java.util.Arrays;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.logic.RemoteFile;
import de.pniehus.odal.utils.Filter;
import de.pniehus.odal.utils.OdalTextBox;

/**
 * Filters a RemoteFile with set regex
 * @author Phil Niehus
 *
 */
public class RegexFilter extends Filter{
	
	private String filterBy = "[\\s\\S]*"; // Default = match anything
	public RegexFilter() {
		super("REGEX");
	}

	@Override
	public AbstractWindow getSettingUI(OdalGui og) {
		return new RegexSettings(og);
	}

	@Override
	public String getHelpText() {
		return "matches filenames against the given regex\nUsage: provide any regex as parameter\n";
	}

	@Override
	public void setUp(String params) {
		filterBy = params;		
	}
	
	private class RegexSettings extends AbstractWindow{
		
		public RegexSettings(OdalGui o){
			super("Regex Filter - Settings");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			
			final OdalGui odal = o;
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			
			final OdalTextBox regexBox = new OdalTextBox(new TerminalSize(70, 1), TextBox.Style.SINGLE_LINE);
			Button ready = new Button("OK", new Runnable() {
				
				@Override
				public void run() {
					setUp(regexBox.getText());
					odal.configureNextFilter();
				}
			});
			
			p.addComponent(new Label("Enter your regex:"));
			p.addComponent(regexBox);
			p.addComponent(ready);
			setComponent(p);
		}
	}
	
	@Override
	public boolean applyFilter(RemoteFile filetree) {
		String filtered = filetree.getName().replaceAll(filterBy, ""); //Replaces matching strings with nothing
		if(filtered.length() > 0){ // if filtered contains something, the name doesn't match
			filetree.removeFromParent();
			return true;
		}
		return false;
	}
}
