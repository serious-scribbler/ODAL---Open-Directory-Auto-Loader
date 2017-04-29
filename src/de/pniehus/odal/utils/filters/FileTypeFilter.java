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
 * Filters a RemoteFile structure by filetype
 * @author Phil Niehus
 *
 */
public class FileTypeFilter extends Filter{
	
	private List<String> types = null;
	private boolean selectMatches = true;
	
	public FileTypeFilter() {
		super("Filetype");
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
		String fileName = filetree.getName(); //Replaces matching strings with nothing
		boolean match = false;
		for(String s : types){
			if(fileName.endsWith(s)){
				match = true;
				break;
			}
		}
		if(selectMatches){
			if(!match){
				filetree.removeFromParent();
				return true;
			}
		} else{
			if(match){
				filetree.removeFromParent();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getHelpText() {
		return "matches filetypes gainst the given ones.\n\nParameters: Filetypes without do seperated by comma, put a '!' at the beginning to take all files that don't match.\nExamples:\n'!rar,zip,cfg' (without ' ) selects everything but .rar, .zip and .cfg files\n'pdf,epub,mobi' (whithout ') selects all .pdf and .mobi files";
	}

	@Override
	public void setUp(String params) {
		if(params == null) return;
		if(params.contains("!")) selectMatches = false;
		String[] types = params.split(",");
		for(int i = 0; i < types.length; i++){
			types[i] = "." + types[i].replaceAll("[\\W]", "");
			System.out.println(types[i]);
		}
		this.types = Arrays.asList(types);
	}
	
	private class TypeSettings extends AbstractWindow{
		
		public TypeSettings(OdalGui o){
			super("Filetype Filter - Settings");
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
			
			p.addComponent(new Label("Seperate filetypes by comma. For Example: pdf,mobi,epub"));
			p.addComponent(new Label("A ! as first charcter selects all files that don't match the give types."));
			p.addComponent(new Label("Enter your filetypes:"));
			p.addComponent(typeBox);
			p.addComponent(ready);
			setComponent(p);
		}
	}
}
