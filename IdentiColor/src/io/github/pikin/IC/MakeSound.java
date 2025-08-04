package io.github.pikin.IC;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
 
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
 
public class MakeSound extends JPanel{
 
  static double[] sines;
  static int vol;
 
  public static void main(String[] args){
 
    try {
        MakeSound.createTone(246, 100);
    } catch (LineUnavailableException lue) {
        System.out.println(lue);
    }
 
    //Frame object for drawing
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new MakeSound());
    frame.setSize(800,300);
    frame.setLocation(200,200);
    frame.setVisible(true);
  }
 
  public static void createTone(int Hertz, int volume)
    throws LineUnavailableException {
 
    float rate = 44100;
    byte[] buf;
    buf = new byte[1];
    sines = new double[(int)rate];
    vol=volume;
 
    AudioFormat audioF;
    audioF = new AudioFormat(rate,8,1,true,false);
 
    SourceDataLine sourceDL = AudioSystem.getSourceDataLine(audioF);
    sourceDL = AudioSystem.getSourceDataLine(audioF);
    sourceDL.open(audioF);
    sourceDL.start();
 
    for(int i=0; i<rate; i++){
      double angle = (i/rate)*Hertz*2.0*Math.PI;
      buf[0]=(byte)(Math.sin(angle)*vol);
      sourceDL.write(buf,0,1);
 
      sines[i]=(double)(Math.sin(angle)*vol);
    }
 
    sourceDL.drain();
    sourceDL.stop();
    sourceDL.close();
  }
 
  protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
 
        int pointsToDraw=4000;
        double max=sines[0];
        for(int i=1;i<pointsToDraw;i++)  if (max<sines[i]) max=sines[i];
        int border=10;
        int w = getWidth();
        int h = (2*border+(int)max);
 
        double xInc = 0.5;
 
        //Draw x and y axes
        g2.draw(new Line2D.Double(border, border, border, 2*(max+border)));
        g2.draw(new Line2D.Double(border, (h-sines[0]), w-border, (h-sines[0])));
 
        g2.setPaint(Color.red);
 
        for(int i = 0; i < pointsToDraw; i++) {
            double x = border + i*xInc;
            double y = (h-sines[i]);
            g2.fill(new Ellipse2D.Double(x-2, y-2, 2, 2));
        }
   }
}