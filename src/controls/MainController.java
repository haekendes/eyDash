/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.eyDash.databaseManager.DatabaseManager;
import com.eyDash.entities.EyDashUser;
import connector.EyDashGoogleConnector;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
import view.Screen;

/**
 *
 * @author Robin Christ
 */
public class MainController {

    private DatabaseManager dm;
    private Screen screen;
    private EyDashGoogleConnector connector;

    public MainController(DatabaseManager dm, Screen screen, EyDashGoogleConnector connector) {
        this.dm = dm;
        this.screen = screen;
        this.connector = connector;
    }

    /**
     * Verwendet, nachdem die Bluetooth Device Suche abgeschlossen ist.
     */
    public void afterDeviceSearch() {
        for (EyDashUser e : dm.getUserList()) {
            try {
                e.setAppointments(connector.getAppointments(e.getToken()));

            } catch (GeneralSecurityException | IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        screen.setUserList(new ArrayList(dm.getUserList()));

        dm.getUserList().clear();
    }

    /**
     * Verwendet während der Bluetooth Device Suche.
     *
     * @param btDevice
     */
    public void saveUser(RemoteDevice btDevice) {
        dm.getUserByBluetoothAdress(btDevice.getBluetoothAddress());
    }
}
