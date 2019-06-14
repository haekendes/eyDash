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
import controls.MainController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
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

        if (args.length == 0) {
            DatabaseManager dm = new DatabaseManager();

            EyDashGoogleConnector connector = new EyDashGoogleConnector();

            Screen screen = new Screen();

            DisplayController dc = new DisplayController();
            new Thread(dc).start();

            MainController controller = new MainController(dm, screen, connector);

            BluetoothDeviceFinder bdf = new BluetoothDeviceFinder(controller);
            new Thread(bdf).start();
            
        } else if (args[0].equals("u")) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Create new user \n" + "First Name: ");
            String firstName = scanner.next();

            System.out.print("Last Name: ");
            String lastName = scanner.next();

            System.out.print("Bluetooth Device Name: ");
            String bluetoothName = scanner.next();

            String bluetoothAdress = "";

            BluetoothDeviceFinder bdf = new BluetoothDeviceFinder();

            boolean found = false;
            byte count = 0;
            while (!found && count < 6) {
                bdf.discoverDevices();
                for (RemoteDevice r : bdf.getDiscoveredDevices()) {
                    try {
                        if (r.getFriendlyName(false).equals(bluetoothName)) {
                            bluetoothAdress = r.getBluetoothAddress();
                            System.out.println("BLUETOOTH ADRESS: " + r.getBluetoothAddress());

                            found = true;
                            break;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                count++;
            }

            if (found) {
                EyDashGoogleConnector connector = new EyDashGoogleConnector();

                System.out.println("Attempting to connect to Google...");

                try {
                    File token = connector.getToken();

                    DatabaseManager dm = new DatabaseManager();
                    dm.createUser(firstName, lastName, Files.readAllBytes(token.toPath()), bluetoothName, bluetoothAdress);

                    token.delete();

                    System.out.println("--------------------");
                    System.out.println("Registration complete");
                    System.out.println("--------------------");
                } catch (GeneralSecurityException | IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Bluetooth device not found. \n" + "Exit user registration.");
            }
        }
    }

}
