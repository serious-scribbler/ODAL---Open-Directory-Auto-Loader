package de.pniehus.odal.tools;

public class Maintest {
	public static void main(String[] args) {
		RemoteFile root = new RemoteFile("root");
		root.add(new RemoteFile("lamefile", new RemoteFileInfo("http://example.com", 1024l)));
		root.add(new RemoteFile("coolfile", new RemoteFileInfo("http://example.com", 1024l*1024l)));
		root.add(new RemoteFile("largefile", new RemoteFileInfo("http://example.com", 1024l*1024l*120l)));
		RemoteFile subfile = new RemoteFile("subdir");
		subfile.add(new RemoteFile("littlefile", new RemoteFileInfo("http://example.com", 10l)));
		root.add(subfile);
		System.out.println("treeSize: " + RemoteFile.countSize(root));
		System.out.println("Number of files: " + RemoteFile.countFiles(root));
	}
}
