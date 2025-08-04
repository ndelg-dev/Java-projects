package io.github.pikin.IC.util;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class CopyToClipboardClass {
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public void CopyToClipboard(String text) {
		StringSelection stringSelection = new StringSelection(text);
		
		clipboard.setContents(stringSelection, null); 
	}
}
