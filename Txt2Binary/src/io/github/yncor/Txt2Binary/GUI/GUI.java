package io.github.yncor.Txt2Binary.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import io.github.yncor.Txt2Binary.converter.Converter;
import io.github.yncor.Txt2Binary.converter.CopyToClipboardClass;

public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	JTextArea textAreaTxt;
	JTextArea textAreaBin;
	JScrollPane scrollPaneTxt;
	JScrollPane scrollPaneBin;
	JLabel labelTxt;
	JLabel labelBin;
	JLabel label;
	JButton copyButton;
	CopyToClipboardClass copy = new CopyToClipboardClass();
	
	String text;
	
	
	public GUI(String title, int width, int height) {
		
		this.setTitle(title);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout());
		
		textAreaTxt = new JTextArea();
		textAreaTxt.setLineWrap(true);
		textAreaTxt.setWrapStyleWord(true);
		textAreaTxt.setFont(new Font("Arial", Font.PLAIN, 20));
		
		textAreaBin = new JTextArea();
		textAreaBin.setLineWrap(true);
		textAreaBin.setWrapStyleWord(true);
		textAreaBin.setFont(new Font("Arial", Font.PLAIN, 15));
		textAreaBin.setEditable(false);
		
		scrollPaneTxt = new JScrollPane(textAreaTxt);
		scrollPaneTxt.setPreferredSize(new Dimension(450, 230));
		scrollPaneTxt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollPaneBin = new JScrollPane(textAreaBin);
		scrollPaneBin.setPreferredSize(new Dimension(450, 230));
		scrollPaneBin.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		labelTxt = new JLabel("Text");
		labelBin = new JLabel("Binary");
		label = new JLabel("Txt2Binary");
		label.setFont(new Font("Arial", Font.PLAIN, 40));
		
		copyButton = new JButton("Copy binary text");
		copyButton.setFocusable(false);
		copyButton.addActionListener(this);

		this.add(labelTxt);
		this.add(scrollPaneTxt);
		this.add(labelBin);
		this.add(scrollPaneBin);
		this.add(label);
		this.add(copyButton);
		this.setVisible(true);
		
		StringToBinary();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(textAreaBin.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "There is nothing!", "Click to continue", JOptionPane.ERROR_MESSAGE);
		}else {
			copy.CopyToClipboard(textAreaBin.getText());
			JOptionPane.showMessageDialog(null, "Copied " + textAreaBin.getText().toString() + " to the clipboard!", "Click to continue", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void StringToBinary() {
		while(true) {
			String result = Converter.convertStringToBinary(textAreaTxt.getText());
			textAreaBin.setText(Converter.prettyBinary(result, 8, " "));
		}
	}
}
