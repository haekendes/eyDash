/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controls.BluetoothDeviceFinder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.bluetooth.RemoteDevice;

/**
 *
 * @author Robin Christ
 */
public class FindDeviceTestMain {

//        public static final Vector/*<RemoteDevice>*/ devicesDiscovered = new Vector();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BluetoothDeviceFinder bdf = new BluetoothDeviceFinder();
        new Thread(bdf).start();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (RemoteDevice rd : bdf.getDiscoveredDevices()) {
                        try {
                            System.out.println("Print: " + rd.getFriendlyName(false) + " " + rd.getBluetoothAddress());
                        } catch (IOException ex) {
                        }
                    }
                    
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
        t.start();
    }
}
