package io.github.pikin.IC;

import javax.swing.JOptionPane;

import io.github.pikin.IC.GUI.GUI;

public class MainClass {

	public static void main(String[] args) {
		try {
			new GUI("IdentiColor 0.1", 180, 400);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}