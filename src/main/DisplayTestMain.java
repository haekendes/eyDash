/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controls.DisplayController;



/**
 *
 * @author Robin Christ
 */
public class DisplayTestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Thread(new DisplayController()).start();
    }
    
}
