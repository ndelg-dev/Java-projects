package io.github.pikin.IC.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class ColorRobot {
	
	static Color pixelColor;

	public static Color getComplementaryColor(Color color) {
		int r = 255 - color.getRed();
		int g = 255 - color.getGreen();
		int b = 255 - color.getBlue();
		return new Color(r, g, b);
	}
    
    public static void detectColorAtMouse() {
    	try {
    		//Get mouse location
    		Point p = MouseInfo.getPointerInfo().getLocation();
        	int x = p.x;
        	int y = p.y;
        	
            Robot robot = new Robot();
            
            pixelColor = robot.getPixelColor(x, y);
            
            int r = pixelColor.getRed();
            int g = pixelColor.getGreen();
            int b = pixelColor.getBlue();

            System.out.println("Color at (" + x + ", " + y + "): " + 
            "r: " + r + " g: " + g + " b: " + b);

        } catch (AWTException e) {
            e.printStackTrace();
            System.err.println("Error activando el detector, asegurese de que Java tiene los permisos necesarios.");
        }
    }
    
    public static Color getPixelColor() {
		return pixelColor;
	}
}