/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controls.ConnectionManager;

/**
 *
 * @author Robin Christ
 */
public class ConnectionTestMain {

//        public static final Vector/*<RemoteDevice>*/ devicesDiscovered = new Vector();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new Thread(new ConnectionManager()).start();
    }
}
