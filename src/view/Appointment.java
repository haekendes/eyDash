/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Solom
 */
public class Appointment {
    public int day;
    public int month;
    public int beginTimeHours;
    public int beginTimeMinutes;
    public int endTimeHours;
    public int endTimeMinutes;
    public String text;
    
    public Appointment(int day, int month, int beginTimeHours, int beginTimeMinutes, int endTimeHours, int endTimeMinutes, String text){
        this.day = day;
        this.month = month;
        this.beginTimeHours = beginTimeHours;
        this.beginTimeMinutes = beginTimeMinutes;
        this.endTimeHours = endTimeHours;
        this.endTimeMinutes = endTimeMinutes;
        this.text = text;
    }
}
