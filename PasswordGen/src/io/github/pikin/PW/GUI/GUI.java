package io.github.pikin.PW.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import io.github.pikin.PW.functions.CopyToClipboardClass;
import io.github.pikin.PW.functions.PasswordGenerator;


public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JLabel label;
	JTextField generatedPw;
	JButton copyButton;
	JButton genButton;
	
	JComboBox<Integer> lengthlist;

	CopyToClipboardClass copy = new CopyToClipboardClass();
	
	public GUI(String title, int width, int height) {
		
		this.setTitle(title);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		label = new JLabel("Password Generator");
		label.setFont(new Font("Arial", Font.PLAIN, 35));
		label.setBounds(80, 20, 320, 30);
		
		generatedPw = new JTextField();
		generatedPw.setEditable(false);
		generatedPw.setText("");
		generatedPw.setPreferredSize(new Dimension(220, 30));
		generatedPw.setFont(new Font("Arial", Font.BOLD, 15));
		generatedPw.setBackground(Color.white);
		generatedPw.setBounds(15, 70, 220, 30);
		
		copyButton = new JButton("Copy password");
		copyButton.setFocusable(false);
		copyButton.setBounds(250/4, 110, 130, 30);
		copyButton.addActionListener(this);
		
		genButton = new JButton("Generate password");
		genButton.setFocusable(false);
		genButton.setBounds((250/4) +220, 110, 150, 30);
		genButton.addActionListener(this);
		
		lengthlist = new JComboBox<Integer>();
		lengthlist.addItem(4);
		lengthlist.addItem(5);
		lengthlist.addItem(6);
		lengthlist.addItem(7);
		lengthlist.addItem(8);
		lengthlist.addItem(9);
		lengthlist.addItem(10);
		lengthlist.addItem(11);
		lengthlist.addItem(12);
		lengthlist.addItem(13);
		lengthlist.addItem(14);
		lengthlist.addItem(15);
		lengthlist.addItem(16);
		lengthlist.addItem(17);
		lengthlist.addItem(18);
		lengthlist.addItem(19);
		lengthlist.addItem(20);
	
		lengthlist.setPreferredSize(new Dimension(200, 30));
		lengthlist.setBackground(Color.white);
		lengthlist.setBounds(250, 70, 220, 30);
		
		this.add(label);
		this.add(generatedPw);
		this.add(lengthlist);
		this.add(genButton);
		this.add(copyButton);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == copyButton) {
			if(generatedPw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "There is nothing!", "Click to continue", JOptionPane.ERROR_MESSAGE);
			}else {
				copy.CopyToClipboard(generatedPw.getText());
				JOptionPane.showMessageDialog(null, "Copied " + generatedPw.getText() + " to the clipboard!", "Click to continue", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(e.getSource() == genButton) {
			String password = PasswordGenerator.gen((int) lengthlist.getSelectedItem());
			generatedPw.setText(password);
		}
	}
}
