package de.pniehus.odal;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.resources.Profile;
import de.pniehus.odal.utils.Filter;
import de.pniehus.odal.utils.filters.BlacklistFilter;
import de.pniehus.odal.utils.filters.FileTypeFilter;
import de.pniehus.odal.utils.filters.KeywordFilter;
import de.pniehus.odal.utils.filters.RegexFilter;
import de.pniehus.scribblerlib.logging.ConsolePrintLogHandler;
import de.pniehus.scribblerlib.logging.ScribblerLogFormat;
import de.pniehus.scribblerlib.logging.SimpleLoggingSetup;

/**
 * Hello world!
 *
 */
public class App {
	
	public static Logger mainLogger;
	
	public static void main(String[] args) throws IOException {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new RegexFilter());
		filters.add(new FileTypeFilter());
		filters.add(new KeywordFilter());
		filters.add(new BlacklistFilter());
		Profile p = parseArgs(args, filters);
		
		String fileName = "log-" + new Date().toString().replace(":", "-") + ".txt";
		File logPath = new File(p.getLogDirectory() + fileName);
		
		if(!logPath.getParentFile().isDirectory() && !logPath.getParentFile().mkdirs()) {
			logPath = new File(fileName);
		}
		
		if(logPath.canWrite()) {
			SimpleLoggingSetup.configureRootLogger(logPath.getAbsolutePath(), p.getLogLevel(), !p.isSilent());
		} else {
			Logger root = Logger.getLogger("");
			
			for(Handler h : root.getHandlers()) { // Removing default console handlers
				if(h instanceof ConsoleHandler) {
					root.removeHandler(h);
				}
			}
			
			ConsolePrintLogHandler cplh = new ConsolePrintLogHandler();
			cplh.setFormatter(new ScribblerLogFormat(SimpleLoggingSetup.DEFAULT_DATE_FORMAT));
			root.addHandler(cplh);
			
			System.out.println("Unable to create log: insufficient permissions!");
		}
		
		
		mainLogger = Logger.getLogger(App.class.getCanonicalName());
		untrustedSSLSetup();
		mainLogger.info("Successfully intitialized ODAL");
		if(!p.isLogging()) mainLogger.setLevel(Level.OFF);
		if(p.isWindowsConsoleMode() && !p.isLogging()) {
			Logger root = Logger.getLogger("");
			for(Handler h : root.getHandlers()) {
				if(h instanceof FileHandler) {
					root.removeHandler(h); // Removes FileHandler to allow console output through logging
				}
			}
			mainLogger.setLevel(p.getLogLevel());
		}
		mainLogger.info("Test");
		OdalGui ogui = new OdalGui(p, filters);
	}

	/**
	 * Parses the command line arguments
	 * 
	 * @param args
	 * @param filters
	 * @return
	 */
	public static Profile parseArgs(String[] args, List<Filter> filters) {
		boolean windowsConsole = false;
		
		Profile profile = new Profile(filters);
		
		Options options = new Options();
		Options helpOptions = new Options();
		
		Option profileOption = Option.builder("p").longOpt("profile").hasArg().argName("profile name").desc("Loads or generates the profile with the given name").build();
		
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			Console console = System.console();
			if (console != null) {
				windowsConsole = true;
				profileOption.setRequired(true);
			}
		}
		
		options.addOption(profileOption);
		options.addOption(Option.builder("url").hasArg().argName("url").desc("Sets the url of the open directory which will be parsed and downloaded").build());
		options.addOption(Option.builder("a").longOpt("select-all").desc("Downloads all available files (except the ones removed by filters), overrules the corresponding setting if a profile is used").build());
		options.addOption(Option.builder("o").longOpt("outputDir").hasArg().argName("directory path").desc("Sets the output directory to the given directory, overrules the corresponding setting if a profile is used").build());
		options.addOption(Option.builder("w").longOpt("windows-mode").desc("Enables the windows console mode on non-windows systems. Requires -url and -p to be used.").build());
		Option helpOption = Option.builder("h").longOpt("help").desc("Displays this help dialog").build();
		
		helpOptions.addOption(helpOption);
		options.addOption(helpOption);
		
		CommandLineParser cliParser = new DefaultParser();
		
		try {
			CommandLine cmd = cliParser.parse(helpOptions, args, true);
			
			
			if(cmd.getOptions().length == 0){
				cmd = cliParser.parse(options, args);
				
				if(cmd.hasOption("w")) {
					windowsConsole = true;
					if(!cmd.hasOption("p")) {
						System.out.println("ERROR: The profile option is required for windows mode!");
						printHelp(filters, options);
					}
				}
				
				if(cmd.hasOption("p")){
					String profileName = cmd.getOptionValue("p");
					File profileFile = new File(profileName + ".odal");
					if(profileFile.exists()){
						try {
							profile = Profile.loadProfile(profileName);
							profile.setUserProfile(true);
						} catch (IOException e) {
							System.out.println("An error occured while loading the specified profile!");
						}
					} else{
						try {
							Profile.saveProfile(profileName, profile);
							System.out.println("The profile " + profileFile.getName() + " has been created!");
							System.exit(0);
						} catch (IOException e) {
							System.out.println("An error occured during the creation of the profile " + profileFile.getName() + " : " + e.getMessage());
							System.out.println("Terminating.");
							System.exit(1);
						}
					}
					
				}
				
				if(cmd.hasOption("a")){
					profile.setSelectAll(true);
				}
				
				if(cmd.hasOption("o")){
					File out = new File(cmd.getOptionValue("o"));
					if(out.isDirectory() || out.canWrite()){
						profile.setOutputPath(out.getAbsolutePath());
					} else{
						System.out.println(out.getAbsolutePath() + " is not a directory or not writeable!");
						System.out.println("Terminating.");
						System.exit(1);
					}
				}
				
				if(cmd.hasOption("url")){
					profile.setUrl(cmd.getOptionValue("url"));
				} else if(windowsConsole){
					System.out.println("ERROR: The -url argument is required for console use on windows systems.");
					printHelp(filters, options);
					System.exit(1);
				}
				
			} else{
				printHelp(filters, options);
				System.exit(0);
			}
		} catch (ParseException e) {
			System.out.println("\nUnable to parse command line arguments: " + e.getLocalizedMessage() + "\n");
			printHelp(filters, options);
			System.exit(1);
		}
		
		if(windowsConsole){
			profile.setWindowsConsoleMode(true);
		}
		
		return profile;
	}

	/**
	 * Prints the help text
	 * 
	 * @param filters
	 *            A List that contains all filters. This is used to display
	 *            their help texts
	 */
	private static void printHelp(List<Filter> filters, Options opt) {
		HelpFormatter helpFormatter = new HelpFormatter();
		
		String header = "\n---- Open Directory Auto Loader " + OdalGui.version + " ----\n";
		if(System.getProperty("os.name").toLowerCase().contains("windows")) header += "\nIMPORTANT INFORMATION ON CONSOLE USE ON WINDOWS SYSTEMS:\nStarting the programm from the terminal requires the use of -p and -url and automatically enables -a.\n ";
		
		String footer = "\n\nFilter configuration:\n";
		footer += "Profiles enable all filters with sample data by default,\nremove their corresponding configuration line from your profile to disable them\n";
		for (Filter f : filters) {
			footer += "\n" + f.getName() + "-filter\n";
			footer += f.getHelpText();
			footer += "\n";
		}
		
		footer += "\nPLEASE REPORT ANY ISSUES AT https://github.com/serious-scribbler/ODAL---Open-Directory-Auto-Loader/issues";
		String jarName = "";
		
		try {
			jarName = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getName();
		} catch (URISyntaxException e) {
			jarName = "odal.jar";
		}
		
		helpFormatter.printHelp("java -jar " + jarName, header, opt, footer, true);
	}

	/**
	 * This method initializes a Trustmanager that accepts self signed ssl
	 * certificates
	 * 
	 * This code of this method has been taken from
	 * 
	 * @see <a href="https://stackoverflow.com/a/4453908">this Stackoverflow
	 *      post</a> and is licensed under the MIT License
	 * 
	 *      Copyright (c) 2010 nogudnik
	 * 
	 *      Permission is hereby granted, free of charge, to any person
	 *      obtaining a copy of this software and associated documentation files
	 *      (the "Software"), to deal in the Software without restriction,
	 *      including without limitation the rights to use, copy, modify, merge,
	 *      publish, distribute, sublicense, and/or sell copies of the Software,
	 *      and to permit persons to whom the Software is furnished to do so,
	 *      subject to the following conditions:
	 * 
	 *      The above copyright notice and this permission notice shall be
	 *      included in all copies or substantial portions of the Software.
	 * 
	 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	 *      EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	 *      MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	 *      NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
	 *      BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
	 *      ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
	 *      CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	 *      SOFTWARE.
	 * 
	 */
	public static void untrustedSSLSetup() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			mainLogger.severe("Unable to setup support for unverified SSL certificates: " + e.getMessage());
		}
	}
}
