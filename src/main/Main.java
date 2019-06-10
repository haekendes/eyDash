/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.eyDash.databaseManager.DatabaseManager;
import com.eyDash.entities.EyDashUser;
import connector.EyDashGoogleConnector;
import controls.BluetoothDeviceFinder;
import controls.DisplayController;
import controls.MainController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import view.Screen;

/**
 *
 * @author Robin Christ
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DatabaseManager dm = new DatabaseManager();

        EyDashGoogleConnector connector = new EyDashGoogleConnector();

        Screen screen = new Screen();

        DisplayController dc = new DisplayController();
        new Thread(dc).start();

        MainController controller = new MainController(dm, screen, connector);
        
        BluetoothDeviceFinder bdf = new BluetoothDeviceFinder(controller);
        new Thread(bdf).start();
    }

}
