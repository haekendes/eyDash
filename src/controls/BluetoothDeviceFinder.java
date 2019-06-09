/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.eyDash.databaseManager.DatabaseManager;
import com.eyDash.entities.EyDashUser;
import com.intel.bluetooth.RemoteDeviceHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

/**
 *
 * @author Robin Christ
 */
public class BluetoothDeviceFinder implements Runnable {

    private List<RemoteDevice> newDevices;
    private List<RemoteDevice> discoveredDevices;

    private boolean isRunning;

    private SimpleDateFormat formatter;

    private DatabaseManager dm;

    public BluetoothDeviceFinder(DatabaseManager dm) {
        this.dm = dm;

        this.newDevices = new ArrayList();
        this.discoveredDevices = new ArrayList();
        this.isRunning = true;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }

    private void getUserInfo(RemoteDevice btDevice) {
        EyDashUser user = dm.getUserByBluetoothAdress(btDevice.getBluetoothAddress());
        System.out.println(user.getId() + " | " + user.getFirstName() + " | " + user.getLastName());
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println("------------------------");

            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));

            discoverDevices();
        }
    }

    public void discoverDevices() {
        final Object inquiryCompletedEvent = new Object();
        DiscoveryListener discoveryListener = initDiscoveryListener(inquiryCompletedEvent);

        discoveredDevices = new ArrayList(newDevices);
        newDevices.clear();

        synchronized (inquiryCompletedEvent) {
            boolean started = false;

            try {
                started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, discoveryListener);

            } catch (BluetoothStateException ex) {
            }

            if (started) {
                System.out.println("wait for device inquiry to complete...");

                try {
                    inquiryCompletedEvent.wait();

                } catch (InterruptedException ex) {
                }
                System.out.println(newDevices.size() + " device(s) found");
            }
        }
    }

    private DiscoveryListener initDiscoveryListener(Object inquiryCompletedEvent) {
        return new DiscoveryListener() {

            @Override
            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");

                getUserInfo(btDevice);

                getNewDevices().add(btDevice);

                try {
                    System.out.println("    name " + btDevice.getFriendlyName(false));
                } catch (IOException ex) {
                    System.out.println("Can't get Device Name");
                }
            }

            @Override
            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");

                synchronized (inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll();
                }
            }

            @Override
            public void serviceSearchCompleted(int transID, int respCode) {
            }

            @Override
            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };
    }

    /**
     * Use DiscoveryAgent static int value as argument.
     *
     * @param code
     */
    public void setDiscoverable(int code) {
        try {

            LocalDevice.getLocalDevice().setDiscoverable(code);
        } catch (BluetoothStateException ex) {
            Logger.getLogger(BluetoothDeviceFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Not yet intended for use.
     *
     * @param discoveryListener
     * @param device
     * @param inquiryCompletedEvent
     */
    private void discoverServices(DiscoveryListener discoveryListener, RemoteDevice device, Object inquiryCompletedEvent) {
        UUID[] uuidSet = new UUID[1];
        uuidSet[0] = new UUID(0x1105); //OBEX Object Push service

        int[] attrIDs = new int[]{
            0x0100 // Service name
        };

        LocalDevice localDevice = null;
        try {
            localDevice = LocalDevice.getLocalDevice();
        } catch (BluetoothStateException ex) {
            Logger.getLogger(BluetoothDeviceFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        DiscoveryAgent agent = localDevice.getDiscoveryAgent();
        try {
            agent.searchServices(null, uuidSet, device, discoveryListener);
        } catch (BluetoothStateException ex) {
            Logger.getLogger(BluetoothDeviceFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            synchronized (inquiryCompletedEvent) {
                inquiryCompletedEvent.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Not yet intended for use.
     *
     * @param remoteDevice
     * @return
     */
    private Boolean pairDevices(RemoteDevice remoteDevice) {
        //check if authenticated already
        if (remoteDevice.isAuthenticated()) {
            return true;
        } else {

            boolean paired;

            System.out.println("--> Pairing device");

            try {
                String PIN = "111111";
                paired = RemoteDeviceHelper.authenticate(remoteDevice, PIN);
                System.out.println("Pair with " + remoteDevice.getFriendlyName(true) + (paired ? " succesfull" : " failed"));

                if (paired) {
                    System.out.println("--> Pairing successful with device " + remoteDevice.getBluetoothAddress());
                } else {
                    System.out.println("--> Pairing unsuccessful with device " + remoteDevice.getBluetoothAddress());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("--> Pairing unsuccessful with device " + remoteDevice.getBluetoothAddress());
                paired = false;
            }
            System.out.println("--> Pairing device Finish");
            return paired;
        }
    }

    /**
     * Not yet intended for use.
     *
     * @param serverURL
     */
    private void sendMessageToDevice(String serverURL) {
        try {
            System.out.println("Connecting to " + serverURL);

            ClientSession clientSession = (ClientSession) Connector.open(serverURL);
            HeaderSet hsConnectReply = clientSession.connect(null);
            if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
                System.out.println("Failed to connect");
                return;
            }

            HeaderSet hsOperation = clientSession.createHeaderSet();
            hsOperation.setHeader(HeaderSet.NAME, "Hello.txt");
            hsOperation.setHeader(HeaderSet.TYPE, "text");

            //Create PUT Operation
            Operation putOperation = clientSession.put(hsOperation);

            // Sending the message
            byte data[] = "Hello World !!!".getBytes("iso-8859-1");
            OutputStream os = putOperation.openOutputStream();
            os.write(data);
            os.close();

            putOperation.close();
            clientSession.disconnect(null);
            clientSession.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the isRunning
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * @param isRunning the isRunning to set
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * @return the newDevices
     */
    private List<RemoteDevice> getNewDevices() {
        return newDevices;
    }

    /**
     * @return the discoveredDevices
     */
    public List<RemoteDevice> getDiscoveredDevices() {
        return discoveredDevices;
    }
}
