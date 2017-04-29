package de.pniehus.odal.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;





import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.input.KeyStroke;

public class OdalTextBox extends TextBox{

	public OdalTextBox() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OdalTextBox(String initialContent, Style style) {
		super(initialContent, style);
		// TODO Auto-generated constructor stub
	}

	public OdalTextBox(String initialContent) {
		super(initialContent);
		// TODO Auto-generated constructor stub
	}

	public OdalTextBox(TerminalSize preferredSize, String initialContent,
			Style style) {
		super(preferredSize, initialContent, style);
		// TODO Auto-generated constructor stub
	}

	public OdalTextBox(TerminalSize preferredSize, String initialContent) {
		super(preferredSize, initialContent);
		// TODO Auto-generated constructor stub
	}

	public OdalTextBox(TerminalSize preferredSize, Style style) {
		super(preferredSize, style);
		// TODO Auto-generated constructor stub
	}

	public OdalTextBox(TerminalSize preferredSize) {
		super(preferredSize);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized Result handleKeyStroke(KeyStroke k) {
		if(k.isCtrlDown()){
			switch(k.getCharacter()){
				case 'v':
					Toolkit t = Toolkit.getDefaultToolkit();
					Clipboard c = t.getSystemClipboard();
					String s;
					try {
						s = (String) c.getData(DataFlavor.stringFlavor);
						setText(s);
					} catch (Exception e) {
					// ignore
					}
					return Result.HANDLED;
				case 'c':
					StringSelection sel = new StringSelection(getText());
					Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
					clip.setContents(sel, sel);
					return Result.HANDLED;
			}
		}
		return super.handleKeyStroke(k);
	}
}
