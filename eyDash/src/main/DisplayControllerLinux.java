/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;

/**
 *
 * @author Robin Christ
 */
public class DisplayControllerLinux {
    
    public static void turnOn() {
        try {
            Runtime.getRuntime().exec("xset dpms force on");
        } catch (IOException ex) {
        }
    }
    
    public static void standby() {
        try {
            Runtime.getRuntime().exec("xset dpms force standby");
        } catch (IOException ex) {
        }
    }
}
