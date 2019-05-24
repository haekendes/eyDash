/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.eyDash.databaseManager.DatabaseManager;
import com.eyDash.entities.EyDashUser;
import connector.EyDashGoogleConnector;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin Christ
 */
public class ConnectorTestMain {

    public static void main(String[] args) {
        String firstName = "Hubi";
        String lastName = "Schmacht";
        String bluetoothName = "Lebensraumbaum";
        String bluetoothAdress = "";

        DatabaseManager dm = new DatabaseManager();
//        dm.createUser(firstName, lastName, mailAdress, password, bluetoothName, bluetoothAdress);

        EyDashGoogleConnector e = new EyDashGoogleConnector();

        File token = null;
        try {
//            token = e.getToken();
//            System.out.println(token.getAbsolutePath());
//
//            byte[] filebytes = Files.readAllBytes(token.toPath());
//            dm.createUser(firstName, lastName, filebytes, bluetoothName, bluetoothAdress);
//            if (token.exists()) {
//                token.delete();
//                token.deleteOnExit();
//            }
//            System.out.println("Exists?: " + token.exists());

            EyDashUser user = dm.getUserByID(33);
            System.out.println(user.getFirstName());
            List<String> appointments = e.getAppointments(user.getToken());
            EyDashUser user2 = dm.getUserByID(34);
            System.out.println(user2.getFirstName());
            List<String> appointments2 = e.getAppointments(user2.getToken());

        } catch (GeneralSecurityException ex) {
            Logger.getLogger(ConnectorTestMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectorTestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
