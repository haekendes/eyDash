/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.*;

/**
 *
 * @author Robin Christ
 */
public class ConnectionTestMain {

        public static final Vector/*<RemoteDevice>*/ devicesDiscovered = new Vector();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                devicesDiscovered.addElement(btDevice);
                try {
                    System.out.println("     name " + btDevice.getFriendlyName(false));
                } catch (IOException cantGetDeviceName) {
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            boolean started = false;
            try {
                started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            } catch (BluetoothStateException ex) {
                Logger.getLogger(ConnectionTestMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                try {
                    inquiryCompletedEvent.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionTestMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(devicesDiscovered.size() +  " device(s) found");
            }
        }
    }
}
