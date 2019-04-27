/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.eyDash.databaseManager.DatabaseManager;
import com.eyDash.entities.EyDashUser;

/**
 *
 * @author Robin Christ
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String firstName = "Hubi";
        String lastName = "Schmacht";
        int phoneNumber = (int) (Math.random()*100);
        
        DatabaseManager dm = new DatabaseManager();
        dm.createUser(firstName, lastName, phoneNumber);
        
        EyDashUser user = dm.getUserByPhoneNumber(25);
        System.out.println(user.getId() + " | " + user.getFirstName() + " | " + user.getLastName() + " | " + user.getPhoneNumber());
        
        EyDashUser user2 = dm.getUserByID(3);
        System.out.println(user2.getId() + " | " + user2.getFirstName() + " | " + user2.getLastName() + " | " + user2.getPhoneNumber());
    }
    
}
