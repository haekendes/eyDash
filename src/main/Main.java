/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.eyDash.databaseManager.DatabaseManager;
import connector.EyDashGoogleConnector;
import controls.BluetoothDeviceFinder;
import controls.DisplayController;

/**
 *
 * @author Robin Christ
 */
public class Main {
    
    public static void main(String[] args) {
        
        DatabaseManager dm = new DatabaseManager();
        
        EyDashGoogleConnector e = new EyDashGoogleConnector();
        
        DisplayController dc = new DisplayController();
        new Thread(dc).start();
        
        BluetoothDeviceFinder bdf = new BluetoothDeviceFinder(dm);
        new Thread(bdf).start();
    }
    
}
