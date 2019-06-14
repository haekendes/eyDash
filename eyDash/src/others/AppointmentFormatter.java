/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

/**
 *
 * @author Robin Christ
 */
public class AppointmentFormatter {
    
    public static String format(String string) {
        String head = string.substring(0, string.lastIndexOf(" "));
        String date = string.substring(string.lastIndexOf(" "));
        
        date = date.replaceAll("-", ".");
        
        if(date.contains("T")) {
            date = date.substring(0, date.indexOf("T")+6) + " Uhr";
            date = date.replaceFirst("T", " um ");
        }
        
        return " " + head + " -" + date;
    }
}
