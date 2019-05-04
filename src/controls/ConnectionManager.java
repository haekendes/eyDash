/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.util.ArrayList;
import java.util.List;
import javax.bluetooth.RemoteDevice;

/**
 *
 * @author Robin Christ
 */
public class ConnectionManager {
    
    private List<RemoteDevice> discoveredDevices;
    private List<RemoteDevice> pairedDevices;
    
    public ConnectionManager() {
        this.discoveredDevices = new ArrayList();
        this.pairedDevices = new ArrayList();
    }
    
    public void discoverDevices() {
        
    }
}
