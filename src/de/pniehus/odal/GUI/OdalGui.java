package de.pniehus.odal.GUI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.pniehus.odal.logic.IndexOfParser;
import de.pniehus.odal.logic.RemoteFile;
import de.pniehus.odal.utils.*;
import de.pniehus.odal.utils.ODALCheckBoxList.Listener;

public class OdalGui {
	
	private MultiWindowTextGUI gui;
	private boolean parseSubdirs = true;
	private boolean skipFileSelection = false;
	private boolean keepStructure = true;
	private boolean silent = false;
	private File outputDir = null;
	private String url = null;
	private List<Filter> filters;
	private static String version = "v0.0.1";
	private RemoteFile root;
	/**
	 * True = filters were already selected (used for automated screen selection)
	 */
	private boolean filtersSet = false;
	
	public OdalGui(String[] args, List<Filter> filters) throws IOException{
		this.filters = filters;
		parseArgs(args);
		
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();
		
		gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
		
		// TODO init fileTree
		gui.addWindowAndWait(new URLWindow());
	}
	
	/**
	 * Parses all arguments
	 * @param args
	 */
	private void parseArgs(String[] args){
		for(int i = 0; i < args.length; i++){
			switch(args[i]){
				case "-noSub":
					parseSubdirs = false;
					break;
				case "-a":
					skipFileSelection = true;
					break;
				case "-noStructure":
					keepStructure = false;
					break;
				case "-silent":
					silent = true;
					break;
				case "-o":
					checkArgument(args, i);
					outputDir = new File(args[i+1]);
					if(!checkFile(outputDir)){
						System.out.println("The selected output directory does not exist or is not writeable!");
						System.exit(1);
					}
					i++;
					break;
				case "-url":
					checkArgument(args, i);
					url = args[i+1];
					i++;
					break;
				case "-f":
					checkArgument(args, i);
					checkArgument(args, i+1); // Checks for the existence of params
					// Enable selected filter
					filtersSet = true;
					i = i + 2;
				case "/?":
					printHelp();
				case "-help":
					printHelp();
				case "--help":
					printHelp();
				default:
					System.out.println("Invalid arguments!");
					printHelp();
			}
		}
	}
	
	/**
	 * Determines which dialog needs to be shown based on the programs state
	 */
	public void determineNextWindow(){
		if(url == null){
			URLWindow w = new URLWindow();
			gui.addWindow(w);
			gui.setActiveWindow(w);
		} else if(outputDir == null){
			OutputSelector w = new OutputSelector();
			gui.addWindow(w);
			gui.setActiveWindow(w);
		} else if(!skipFileSelection){
			FileSelector sel = new FileSelector(root);
			gui.addWindow(sel);
			gui.setActiveWindow(sel);
		} else if(!filtersSet && filters.size() != 0){
			// Show filter dialog
		} else{
			// RUN Filters, DOWNLOAD AND SHOW PROGRESS
		}
	}
	
	/**
	 * Exits the programm if i is the last argument or i is followed by an argument that starts with -
	 * @param args
	 * @param i
	 */
	private void checkArgument(String[] args, int i){
		if((i == args.length - 1) || args[i+1].startsWith("-")){
			System.out.println("ERROR: INVALID ARGUMENTS!");
			printHelp();
			
		}
	}
	
	/**
	 * Prints the help text and exits the programm
	 */
	private void printHelp(){
		System.out.println("------ Open Directory Auto Loader " + version + " Help------\n");
		System.out.println("ODAL is a software for downloading files or entire file structures from open directory listings.");
		System.out.println("The files from the parsed open directories can be selected and filtered before the download starts.\n");
		System.out.println("Commandline parameters:\n");
		System.out.println("-o <path>\t\tThe path to the output directories, where the downloaded file structure is saved");
		System.out.println("-url <url>\t\tThe url of the open directory which will be parsed and downloaded");
		System.out.println("-f <filter> <params>\tEnables the selected filter with the given parameters. Can be used multiple times");
		System.out.println("-silent\t\tPrevents the software from displaying progress information");
		System.out.println("-noStructure\t\tMakes the software copy all files into the same direcotry instead of copying the structure from the source");
		System.out.println("-a\t\tDownloads all files (except the ones removed by filters");
		System.out.println("-noSub\t\tOnly parses files and ignores directories entirely");
		System.out.println("/?\t\tDisplays this dialog");
		System.out.println("-help\t\tDisplays this dialog");
		System.out.println("--help\t\tDisplays this dialog");
		System.out.println("\n\nFilters:\n");
		for(Filter f : filters){
			System.out.println(f.getName() +"-filter\n");
			System.out.println(f.getHelpText());
			System.out.println("\n");
		}
		System.exit(1);
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
			final TextBox urlBox = new TextBox(new TerminalSize(gui.getScreen().getTerminalSize().getColumns()-2, 1), TextBox.Style.SINGLE_LINE);
			if(url != null) urlBox.setText(url);
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
						parseSubdirs = subdirs.isChecked();
						keepStructure = structure.isChecked();
						url = urlBox.getText();
						IndexOfParser parser = new IndexOfParser(false);
						root = parser.parseURL(url, parseSubdirs, "root");
						System.out.println("Parsed, Number of childs of root: " + root.getChildCount()); // TODO remove
						determineNextWindow();// Show file selection dialog
					} catch (Exception e) {
						gui.setActiveWindow(self);
						MessageDialog.showMessageDialog(gui, "ERROR", "The selected URL couldn't be parsed: \n" + e.getMessage());
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
		private boolean changing = false;
		
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
						RemoteFile parent = (RemoteFile)rm.getParent();
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
			
			final TextBox path = new TextBox(new TerminalSize(gui.getScreen().getTerminalSize().getColumns()-2, 1), TextBox.Style.SINGLE_LINE);
			Button select = new Button("Save", new Runnable() {
				
				@Override
				public void run() {
					File o = new File(path.getText());
					if(checkFile(o)){
						outputDir = o;
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
	private class BusyWindow extends AbstractWindow{
		
		/**
		 * Creates a BusyWindow
		 * @param text the text shown in the window
		 */
		public BusyWindow(String text){
			super("ODAL - Performing background task!");
			setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
			Panel p = new Panel(new LinearLayout(Direction.VERTICAL));
			p.addComponent(new Label(text));	
			setComponent(p);
		}
	}
}
