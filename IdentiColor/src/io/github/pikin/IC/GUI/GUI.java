package io.github.pikin.IC.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import io.github.pikin.IC.util.ColorRobot;
import io.github.pikin.IC.util.CopyToClipboardClass;


public class GUI extends JFrame implements ActionListener, ItemListener, KeyListener{

	private static final long serialVersionUID = 1L;

	JPanel colorPanel;
	JButton selectButton;
	JButton copyButton;

	private JLabel colorLabel;
	private Font colorLabelFont = new Font("Arial", 1, 20);

	private JCheckBox detectCheckBox;
	private Timer colorDetectionTimer;
	JLabel instructions;

	LineBorder border = new LineBorder(Color.black, 2);

	CopyToClipboardClass copy = new CopyToClipboardClass();


	public GUI(String title, int width, int height) {

		this.setTitle(title);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusable(true);
		this.setLayout(null);
		this.addKeyListener(this);

		//Top right corner
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMinX();
		int y = 0;
		this.setLocation(x, y);

		colorPanel = new JPanel();
		colorPanel.setBorder(border);
		colorPanel.setBounds(23, 20, 120, 60);
		colorPanel.setLayout(null);

		selectButton = new JButton("Select color");
		selectButton.setFocusable(false);
		selectButton.addActionListener(this);
		selectButton.setBorder(border);
		selectButton.setBackground(Color.white);
		selectButton.setBounds(23, 85, 120, 30);

		copyButton = new JButton("Copy to clipboard");
		copyButton.setFocusable(false);
		copyButton.addActionListener(this);
		copyButton.setBorder(border);
		copyButton.setBackground(Color.white);
		copyButton.setBounds(23, 120, 120, 30);

		colorLabel = new JLabel("");
		colorLabel.setBorder(border);
		colorLabel.setBounds(0, 0, 120, 60);
		colorLabel.setHorizontalAlignment(JLabel.CENTER);
		colorLabel.setVerticalAlignment(JLabel.CENTER);
		colorLabel.setFont(colorLabelFont);

		colorPanel.add(colorLabel);

		detectCheckBox = new JCheckBox("Detect color");
		detectCheckBox.setFocusable(false);
		detectCheckBox.setBorder(border);
		detectCheckBox.setBounds(23, 155, 100, 30);
		detectCheckBox.addItemListener(this);

		instructions = new JLabel("<html>Press S to turn on<br/>the color detector<br/><br/>Press C to copy the<br/>color selected into<br/>your clipboard</html>");
		instructions.setBounds(23, 190, 180, 100);
		
		this.add(instructions);
		this.add(detectCheckBox);
		this.add(colorPanel);
		this.add(selectButton);
		this.add(copyButton);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectButton) {

			JColorChooser colorChooser = new JColorChooser(colorPanel.getBackground());

			AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
			AbstractColorChooserPanel rgbPanel = null;

			for (AbstractColorChooserPanel panel : panels) {
				if (panel.getDisplayName().equals("RGB")) {
					rgbPanel = panel;
					break;
				}
			}

			for (AbstractColorChooserPanel panel : panels) {
				colorChooser.removeChooserPanel(panel);
			}

			if (rgbPanel != null) {
				colorChooser.addChooserPanel(rgbPanel);
			}

			javax.swing.JDialog dialog = JColorChooser.createDialog(
					this,
					"Choose a color",
					true,
					colorChooser,
					evt -> {
						Color selectedColor = colorChooser.getColor();

						if (selectedColor != null) {

							selectedColor = new Color(colorChooser.getColor().getRGB(), false);
							colorPanel.setBackground(selectedColor);

							String rgbText = String.format("#%02X%02X%02X",
									selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue());

							colorLabel.setText(rgbText);
							colorLabel.setForeground(ColorRobot.getComplementaryColor(selectedColor));
							System.out.println("RGB Color Selector has been closed\n");
						}
					},
					null
					);

			System.out.println("RGB Color Selector has been opened\n");
			dialog.setVisible(true);
		}

		if(e.getSource() == copyButton) {
			if(colorLabel.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "There is nothing!", "Click to continue", JOptionPane.ERROR_MESSAGE);
			}else {
				copy.CopyToClipboard(colorLabel.getText());
				JOptionPane.showMessageDialog(null, "Copied " + colorLabel.getText().toString() + " to the clipboard!", "Click to continue", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(detectCheckBox)) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("Detect color robot is on\n");
				instructions.setText("<html>Press S to turn off<br/>the color detector<br/><br/>Press C to copy the<br/>color selected into<br/>your clipboard</html>");

				colorDetectionTimer = new Timer(500, evt -> {
					ColorRobot.detectColorAtMouse();
					Color c = ColorRobot.getPixelColor();
					colorPanel.setBackground(c);
					colorLabel.setText(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
					colorLabel.setForeground(ColorRobot.getComplementaryColor(c));
				});
				colorDetectionTimer.start();
			} else if(e.getStateChange() == ItemEvent.DESELECTED){
				System.out.println("Detect color robot is off\n");
				instructions.setText("<html>Press S to turn on<br/>the color detector<br/><br/>Press C to copy the<br/>color selected into<br/>your clipboard</html>");
				if (colorDetectionTimer != null) {
					colorDetectionTimer.stop();
				}
			}
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {

	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'S' || e.getKeyChar() == 's') {
			if(!detectCheckBox.isSelected()) {
				detectCheckBox.setSelected(true);
			}else if(detectCheckBox.isSelected()) {
				detectCheckBox.setSelected(false);
			}
		}
		
		if(e.getKeyChar() == 'C' || e.getKeyChar() == 'c') {
			if(colorLabel.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "There is nothing!", "Click to continue", JOptionPane.ERROR_MESSAGE);
			}else {
				copy.CopyToClipboard(colorLabel.getText());
				JOptionPane.showMessageDialog(null, "Copied " + colorLabel.getText().toString() + " to the clipboard!", "Click to continue", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}