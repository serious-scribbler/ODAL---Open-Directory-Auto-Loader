package de.pniehus.odal.GUI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.pniehus.odal.logic.IndexOfParser;
import de.pniehus.odal.logic.RemoteFile;
import de.pniehus.odal.logic.TaskController;
import de.pniehus.odal.logic.TaskMonitor;
import de.pniehus.odal.resources.Profile;
import de.pniehus.odal.utils.*;
import de.pniehus.odal.utils.ODALCheckBoxList.Listener;

public class OdalGui {
	
	private MultiWindowTextGUI gui;
	private List<Filter> filters;
	public static final String version = "v0.1.1-SNAPSHOT";
	private RemoteFile root;
	private int nextFilter = 0;
	private int totalFiles = 0;
	boolean filesSelected = false;
	private Profile profile;
	/**
	 * True = filters were already selected (used for automated screen selection)
	 */
	private boolean filtersSet = false;
	
	
	/**
	 * Creates a new instance of OdalGui with the given profile as configuration
	 * @param profile The profile that is used for this instance of ODAL
	 * @param filters All filters that should be accessible for application on File Structures
	 * @throws IOException
	 */
	public OdalGui(Profile profile, List<Filter> filters) throws IOException{
		this.filters = filters;
		this.profile = profile;
		initIO();
	}
	
	/**
	 * Initializes the IO
	 * @throws IOException when the creation of an interactive terminal UI fails
	 */
	private void initIO() throws IOException{
		if(!profile.isWindowsConsoleMode()){
			Terminal terminal = new DefaultTerminalFactory().createTerminal();
			Screen screen = new TerminalScreen(terminal);
			screen.startScreen();
			
			gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
			gui.addWindowAndWait(new URLWindow());
		}
		
		// TODO: Init Windows Console IO (if necessary)
	}
	
	/**
	 * Determines which dialog needs to be shown based on the programs state
	 */
	public void determineNextWindow(){ // TODO implement windows console mode
		if(profile.isWindowsConsoleMode()){
			IndexOfParser parser = new IndexOfParser(false);
			try {
				root = parser.parseURL(profile.getUrl(), profile.isParseSubdirectories(), "root");
				
			} catch (IOException e) {
				// TODO Kill ODAL
				e.printStackTrace();
			}
			
		}
		if(profile.getUrl() == null){
			URLWindow w = new URLWindow();
			gui.addWindow(w);
			gui.setActiveWindow(w);
		} else if(profile.getOutputPath() == null || profile.getOutputPath().equals("")){
			OutputSelector w = new OutputSelector();
			gui.addWindow(w);
			gui.setActiveWindow(w);
		} else if(!filtersSet){
			filtersSet = true;
			if(profile.isUserProfile()){
				if(profile.getFilters().size() == 0){
					filtersSet = true;
					determineNextWindow();
					return;
				} else{
					filtersSet = true;
				}
			} else{
				if(profile.isWindowsConsoleMode()){
					// TODO: don't set up filters
				} else{
					FilterSelector sel = new FilterSelector();
					gui.addWindow(sel);
					gui.setActiveWindow(sel);
				}
			}
			
		} else if(!profile.isSelectAll() && !filesSelected){
			// TODO apply filters beforehand
			BusyWindow b = new BusyWindow("Processing task...");
			gui.addWindow(b);
			gui.setActiveWindow(b);
			
			for(Filter f : filters){
				if(f.isEnabled()){
					b.setTaskInfo("Applying " + f.getName() + "-FILTER...");
					f.filter(root);
				}
			}
			filesSelected = true;
			FileSelector sel = new FileSelector(root);
			gui.addWindow(sel);
			gui.setActiveWindow(sel);
		} else{
			// TODO Now? seperate from ui, create new monitor for windows console & respect silent mode
			BusyWindow b = new BusyWindow("Processing task...");
			gui.addWindow(b);
			gui.setActiveWindow(b);
			TaskController ctrl = new TaskController("Download", profile.isKeepOriginalStructure(), root, new File(profile.getOutputPath()));
			ctrl.addMonitor(b);
			totalFiles = ctrl.getNumberOfFiles();
			if(totalFiles == 0){
				System.out.println("Nothing to download, exiting!");
				System.exit(0);
			}
			ctrl.start();
		}
	}
	
	/**
	 * Shows the configuration dialog for the next enabled filter, calls determineNextWindow() if all filters were configured
	 */
	public void configureNextFilter(){
		if(filters != null && filters.size() != 0){
			int filterX = 0; // counts the number of enabled filters
			Filter run = null;
			for(Filter f : filters){
				if(f.isEnabled()){
					if(filterX == nextFilter){
						nextFilter++;
						run = f;
						break;
					}
					filterX++;
				}
			}
			
			if(run != null){
				AbstractWindow w = run.getSettingUI(this);
				gui.addWindow(w);
				gui.setActiveWindow(w);
			} else{
				determineNextWindow();
			}
		}
	}
	
	/**
	 * Returns true, if the given directory is a writable directory, otherwise false
	 * @param f The directory to check for writability
	 * @return
	 */
	private boolean checkFile(File f){
		if(f.exists() && f.isDirectory() && f.canWrite()) return true;
		return false;
	}
	
	/**
	 * The URL selection window
	 * @author Phil Niehus
	 *
	 */
	private class URLWindow extends AbstractWindow{
		
		public URLWindow(){
			super("ODAL - URL Selection");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			final Label l = new Label("Enter your URL:");
			final OdalTextBox urlBox = new OdalTextBox(new TerminalSize(gui.getScreen().getTerminalSize().getColumns()-2, 1), TextBox.Style.SINGLE_LINE);
			if(profile.getUrl() != null) urlBox.setText(profile.getUrl());
			final CheckBox subdirs = new CheckBox("Parse subdirectories");
			final CheckBox structure = new CheckBox("Keep original file structure");
			final AbstractWindow self = this; // Used to re-enable the window when errors occur
			final Button parse = new Button("PARSE", new Runnable() {
				
				@Override
				public void run() {
					try {
						BusyWindow b = new BusyWindow("Parsing the directory listing");
						gui.addWindow(b);
						gui.setActiveWindow(b);
						gui.updateScreen();
						profile.setParseSubDirectories(subdirs.isChecked());
						profile.setKeepOriginalStructure(structure.isChecked());
						profile.setUrl(urlBox.getText());
						IndexOfParser parser = new IndexOfParser(false);
						root = parser.parseURL(profile.getUrl(), profile.isParseSubdirectories(), "root");
						determineNextWindow();// Show file selection dialog
					} catch (Exception e) {
						gui.setActiveWindow(self);
						String bla = "";
						StackTraceElement[] st = e.getStackTrace(); // TODO: Remove
						for(StackTraceElement emt : st) {
							bla += emt.toString() + "\n";
						}
						MessageDialog.showMessageDialog(gui, "ERROR", "The selected URL couldn't be parsed: \n" + bla);
					}
				}
			});
			p.addComponent(l);
			p.addComponent(urlBox);
			p.addComponent(subdirs);
			p.addComponent(structure);
			p.addComponent(parse);
			setComponent(p);
		}
	}
	
	/**
	 * This window contains a selector for files in a tree
	 * @author Phil Niehus
	 *
	 */
	private class FileSelector extends AbstractWindow{
		
		private ODALCheckBoxList<RemoteFile> boxList;
		
		public FileSelector(RemoteFile root){
			super("ODAL - File selector");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			p.addComponent(new Label("Select/Deselect files. (De-)Selecting a folder will also (de-)select its childs.\nSelecting big Parts of the tree can make the softare seem inresponsive."));
			TerminalSize tSize = gui.getScreen().getTerminalSize();
			boxList = new ODALCheckBoxList<RemoteFile>(new TerminalSize(tSize.getColumns()-2, tSize.getRows()-5));
			
			boxList.addListener(new Listener() {
				@Override
				public void onStatusChanged(int itemIndex, boolean checked){
					RemoteFile f = boxList.getItemAt(itemIndex);
					if(f.isDirectory()) setChildState(f, checked);
				}
			});
			addSubTree(root);
			Button finish = new Button("OK", new Runnable() {
				
				@Override
				public void run() {
					BusyWindow b = new BusyWindow("Filtering selection");
					gui.addWindow(b);
					gui.setActiveWindow(b);
					List<RemoteFile> deselected = boxList.getItems();
					deselected.removeAll(boxList.getCheckedItems());
					for(RemoteFile rm : deselected){
						if(rm == null) continue;
						if(rm.isRoot()) continue;
						RemoteFile parent = (RemoteFile)rm.getParent();
						if(parent == null) continue;
						parent.remove(rm); // Removes all files which haven't been selected by the user
					}
					determineNextWindow();
				}
			});
			
			p.addComponent(boxList);
			p.addComponent(finish);
			setComponent(p);
		}
		
		/**
		 * Recursively selects children and sets their state according to their parent
		 * @param f
		 * @param state
		 */
		private void setChildState(RemoteFile f, boolean state){
			boxList.setSilentlyChecked(f, state);
			if(f.isDirectory()){
				for(int i = 0; i < f.getChildCount(); i++){
					setChildState((RemoteFile) f.getChildAt(i), state);
				}
			}
		}
		
		/**
		 * Adds tree and all its child to the checkbox list
		 * @param tree
		 */
		private void addSubTree(RemoteFile tree){
			boxList.addItem(tree);
			for(int i = 0; i < tree.getChildCount(); i++){
				RemoteFile child = (RemoteFile) tree.getChildAt(i);
				addSubTree(child);
			}
		}
		
	}
	
	/**
	 * This Window is used to select which filters will be applied to the input
	 * @author Phil Niehus
	 *
	 */
	public class FilterSelector extends AbstractWindow{
		
		private ODALCheckBoxList<Filter> filterList;
		public FilterSelector(){
			super("ODAL - Select filters");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			p.addComponent(new Label("Select all filters you want to apply to the parsed directory structure:"));
			
			
			filterList = new ODALCheckBoxList<Filter>();
			for(Filter f : filters){
				filterList.addItem(f);
			}
			Button b = new Button("Configure filters", new Runnable() {
				@Override
				public void run() {
					
					List<Filter> selected = filterList.getCheckedItems();
					for(Filter f : selected){
						f.setEnabled(true); // Enables all selected filters
					}
					
					configureNextFilter();
				}
			});
			
			p.addComponent(filterList);
			p.addComponent(b);
			
			setComponent(p);
		}
		
	}
	
	/**
	 * This window is used to enter the output directory
	 * @author Phil Niehus
	 *
	 */
	public class OutputSelector extends AbstractWindow{
		
		public OutputSelector(){
			super("ODAL - Output directory input");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			p.addComponent(new Label("Enter the path in which ODAL should save the downloaded files:"));
			
			final OdalTextBox path = new OdalTextBox(new TerminalSize(gui.getScreen().getTerminalSize().getColumns()-2, 1), TextBox.Style.SINGLE_LINE);
			Button select = new Button("Save", new Runnable() {
				
				@Override
				public void run() {
					File o = new File(path.getText());
					if(checkFile(o)){
						profile.setOutputPath(o.getAbsolutePath());
						determineNextWindow();
					} else{
						MessageDialog.showMessageDialog(gui, "ERROR", "The selected path is invalid or not writable!");
					}
				}
			});
			
			p.addComponent(path);
			p.addComponent(select);
			
			setComponent(p);
		}
	}
	
	/**
	 * This Window is used to display, that ODAl is working on background tasks
	 * @author Phil
	 *
	 */
	private class BusyWindow extends AbstractWindow implements TaskMonitor{
		
		private Label taskInfo;
		
		/**
		 * Creates a BusyWindow
		 * @param text the text shown in the window
		 */
		public BusyWindow(String text){
			super("ODAL - Performing background task!");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			taskInfo = new Label("");
			p.addComponent(new Label(text));
			p.addComponent(taskInfo);
			setComponent(p);
		}
		
		/**
		 * Updates the updatable status label
		 * @param info
		 */
		public void setTaskInfo(String info){
			taskInfo.setText(info);
		}

		@Override
		public void errorOccured(String errorMessage) {
			System.out.println("ERROR: " + errorMessage);
		}

		@Override
		public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed) {
			taskInfo.setText("Downloaded " + (totalFiles - filesLeft) + " of " + totalFiles);		
			if(filesLeft == 0){
				System.out.println("Download finnished!");
				System.exit(0);
			}
		}
		
		
	}
}
