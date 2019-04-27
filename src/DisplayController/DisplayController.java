/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisplayController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 *
 * @author Robin Christ
 */
public class DisplayController {

    public DisplayController() {

    }

    public void test() {

//        GpioController gpio = GpioFactory.getInstance();
//        
//        GpioPinDigitalInput input = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
//        input.setMode(PinMode.DIGITAL_INPUT);
//Create gpio controller for PIR Motion Sensor listening on the pin GPIO_04        
        final GpioController gpioPIRMotionSensor = GpioFactory.getInstance();
        final GpioPinDigitalInput pirMotionsensor = gpioPIRMotionSensor.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);

        //Create and register gpio pin listener on PIRMotion Sensor GPIO Input instance            
        pirMotionsensor.addListener(new GpioPinListenerDigital() {

            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                //if the event state is High then print "Intruder Detected" and turn the LED ON by invoking the high() method
                if (event.getState().isHigh()) {
                    System.out.println("Intruder Detected!");
                }
                //if the event state is Low then print "All is quiet.." and make the LED OFF by invoking the low() method
                if (event.getState().isLow()) {
                    System.out.println("All is quiet...");
                }
            }
        });

        try {
            // keep program running until user aborts 
            while (true) {
                Thread.sleep(500);  
            }
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
