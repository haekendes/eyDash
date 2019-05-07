/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Robin Christ
 */
public class SwingTestMain {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        JFrame frame = new JFrame();

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });
        
        
        
        frame.add(new Label("BLAAAAAAAAAAAAAAAAA"));
        
        setFrameSize(frame, 0.75, 0.75);
    }
    
    private static void setFrameSize(Frame frame, double width, double height) {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        System.out.println((int)(screenSize.width * width));
        frame.setBounds((int)(screenSize.width * (1 - width) /2), (int)(screenSize.height * (1-height) /2), (int)(screenSize.width * width), (int)(screenSize.height * height));
    }
}
