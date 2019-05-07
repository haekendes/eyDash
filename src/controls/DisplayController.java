/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Robin Christ
 */
public class DisplayController implements Runnable {
    
    /**
     * Last recorded System time the sensor state was high, in milliseconds.
     */
    private long highTime;
    
    /**
     * Time until display is set to standby, in milliseconds.
     */
    private long STANDBY_TIME = 120000; //2 minutes
    
    /**
     * Whether exceedance of standby time should be checked.
     */
    private boolean bool;

    public DisplayController() {
        bool = true;
        highTime = System.currentTimeMillis();
        
        System.out.println("DisplayController initialized, Version 1.0");
    }

    @Override
    public void run() {
        final GpioController gpioPIRMotionSensor = GpioFactory.getInstance();
        final GpioPinDigitalInput pirMotionsensor = gpioPIRMotionSensor.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);
               
        pirMotionsensor.addListener(new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState().isHigh()) {
                    System.out.println("Movement detected! " + new Date().toString());
                    
                    turnOn();
                    
                    bool = false;
                }
                
                if (event.getState().isLow()) {
                    System.out.println("Ready...");
                    
                    bool = true;
                    
                    highTime = System.currentTimeMillis(); //time until standby is reset after movement detection
                }
            }
        });

        try {
            while (true) {
                
                if(bool) {
                    if(System.currentTimeMillis() - highTime > STANDBY_TIME) {
                        standby();
                        bool = false;
                    }
                }
                
                Thread.sleep(500);  
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Tells a Linux system to turn on the display.
     */
    public void turnOn() {
        try {
            Runtime.getRuntime().exec("xset dpms force on");
        } catch (IOException ex) {
        }
    }
    
    /**
     * Tells a Linux system to turn off the display.
     */
    public void standby() {
        try {
            Runtime.getRuntime().exec("xset dpms force standby");
        } catch (IOException ex) {
        }
    }
}
