/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.eyDash.entities.EyDashUser;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
/**
 *
 * @author Marc Pilates
 */
public class Screen extends JFrame{
    
    //Uhrzeit und Datum
    private JLabel dateLabel;
    private JLabel clockLabel;
    private boolean secOn = true;
    private int clockSizeX = 80;
    private int clockSizeY = 10;
    private int day;
    private int month;
    private int year;
    private JPanel timePanel;
    
    //Begrüßungsbox
    private String greetText = "Guten Tag";
    private JLabel greetLabel;
    private JPanel greetingPanel;
    private ImageIcon icon;
    private JLabel iconLabel;
    private ImageIcon weatherIcon;
    private JLabel weatherIconLabel;
    
    //User-Listen
    private ArrayList<JLabel> nameLabels = new ArrayList<JLabel>();
//    private ArrayList<String> userListForeName = new ArrayList<String>();
//    private ArrayList<String> userListSurName = new ArrayList<String>();
    private ArrayList<EyDashUser> userList = new ArrayList<>();
    private ArrayList<ArrayList<Appointment>> appointmentsList = new ArrayList<ArrayList<Appointment>>();
    private JPanel listPanel;
    private int changeTimer = 0;
    private int changeTime = 10;
    private int currentProfile = 0;
   
    //Termine
    private Calendar cal;
    private JPanel calendarPanel;
    private GridBagConstraints cTerms;
    private JPanel otherTermsPanel;
    private JPanel firstTermPanel;
    
    //Farbe
    private Color[] colors = new Color[4];
    private Color primeColor;
   
    private JPanel mainPanel;
    
    
    public Screen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainPanel = new JPanel(new GridBagLayout());
        
        colors[0] = new Color(255,120,0);
        colors[1] = new Color(120,0,255);
        colors[2] = new Color(0,255,120);
        colors[3] = new Color(255,255,0);
        
        setColor(colors[0]);
        
        cal = new GregorianCalendar();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        
        ///////wie ein User und dessen Termine hinzugefügt werden//////
        Appointment a = new Appointment(16, 12, 13, 20, 14, 50, "Familienfeier");
        Appointment b = new Appointment(17, 12, 14, 21, 18, 52, "Party");
        ArrayList<Appointment> l = new ArrayList<Appointment>();
        l.add(a);
        l.add(b);
        addUser("Fanz", "Gustav", l);
        ////////////////////////////////////////////////////////////////
        
        
        ///////////////// Begrüßng ///////////////////////////
        greetingPanel = new JPanel();
        greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.X_AXIS));
        icon = new ImageIcon(new ImageIcon(this.getClass().getResource("/ressources/eyDash_test_icon.gif")).getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT));
        greetLabel = new JLabel(greetText + " " + userList.get(0).getFirstName());
        greetLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
        iconLabel = new JLabel(icon);
        
        greetingPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        greetingPanel.add(iconLabel);
        greetingPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        greetingPanel.add(greetLabel);
        greetingPanel.add(Box.createRigidArea(new Dimension(380, 0)));
        greetingPanel.setBackground(primeColor);
        
        //Wetterdaten, vorerst nicht genutzt
        /**weatherIcon = new ImageIcon(new ImageIcon("src/ressources/weather_icon1.png").getImage().getScaledInstance(128, 80, Image.SCALE_DEFAULT));
        weatherIconLabel = new JLabel(weatherIcon);
        greetingPanel.add(weatherIconLabel);
        weatherIconLabel.setText("13°C");
        weatherIconLabel.setHorizontalTextPosition(JLabel.LEFT);
        weatherIconLabel.setVerticalTextPosition(JLabel.BOTTOM);
        weatherIconLabel.setFont(new Font("Arial", Font.BOLD, 30));
        **/
        
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 10;
        c1.weighty = 0;
        c1.insets = new Insets(4,4,2,2);
        c1.fill = GridBagConstraints.BOTH;
        mainPanel.add(greetingPanel, c1);
        ///////////////// Begrüßng Ende///////////////////////////
        
        
        ///////////////// Uhr und Datum ///////////////////////////
        timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        GridBagConstraints cTime = new GridBagConstraints();
        dateLabel = new JLabel();
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial Black", Font.BOLD, 50));
        dateLabel.setFont(new Font("Arial Black", Font.BOLD, 25));
        clockLabel.setForeground(Color.DARK_GRAY);
        dateLabel.setForeground(Color.DARK_GRAY);
        cTime.gridy = 0;
        timePanel.add(dateLabel, cTime);
        cTime.gridy = 1;
        timePanel.add(clockLabel, cTime);
        timePanel.setBackground(new Color(180,180,180));
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = 0;
        c2.weightx = 0.5;
        c2.weighty = 0;
        c2.insets = new Insets(4,2,2,4);
        c2.fill = GridBagConstraints.BOTH;
        mainPanel.add(timePanel, c2);
        ///////////////// Uhr und Datum Ende ///////////////////////////
        
        
        ///////////////// Kalender ///////////////////////////
        calendarPanel = new JPanel(new GridBagLayout());
        cTerms = new GridBagConstraints();
        calendarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80,80,80)), "Ihre Termine auf einen Blick", 1, 3, new Font("Arial Black", Font.BOLD, 20), new Color(150,150,150)));
        calendarPanel.setBackground(new Color(30,30,30));
        createAppointments();
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 1;
        c3.weightx = 10;
        c3.weighty = 20;
        c3.insets = new Insets(2,4,4,2);
        c3.fill = GridBagConstraints.BOTH;
        mainPanel.add(calendarPanel, c3);
        ///////////////// Kalender Ende ///////////////////////////
        
        
        ///////////////// Liste der Anwesenden ///////////////////////////
        listPanel = new JPanel();
        for(int i = 0; i < userList.size(); i++){
            nameLabels.add(new JLabel(userList.get(i).getFirstName() + " " + userList.get(i).getLastName()));
            nameLabels.get(i).setForeground(new Color(200,200,200));
            listPanel.add(nameLabels.get(i));
            nameLabels.get(i).setBorder(BorderFactory.createMatteBorder(1, 10, 1, 1, new Color(0,0,0,0)));
        }
        nameLabels.get(currentProfile).setBorder(BorderFactory.createMatteBorder(1, 10, 1, 1, primeColor));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(80,80,80));
        listPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60,60,60)), "Liste aller Anwesenden", 2, 3, new Font("Arial Black", Font.PLAIN, 9), new Color(140,140,140)));
        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 1;
        c4.gridy = 1;
        c4.weightx = 0.5;
        c4.weighty = 20;
        c4.insets = new Insets(2,2,4,4);
        c4.fill = GridBagConstraints.BOTH;
        mainPanel.add(listPanel, c4);
        ///////////////// Liste der Anwesenden Ende ///////////////////////////
        
        
        mainPanel.setBackground(Color.DARK_GRAY);
        this.add(mainPanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setUndecorated(true);
        this.setTitle("eyDash");
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void clock(){
       
        Thread clock = new Thread(){
            public void run(){
                try {
                    while(true){
                        //Uhrzeit und Datum werden permanent vom System geholt
                        cal = new GregorianCalendar();
                        day = cal.get(Calendar.DAY_OF_MONTH);
                        month = cal.get(Calendar.MONTH) + 1;
                        year = cal.get(Calendar.YEAR);
                        dateLabel.setText((day < 10 ? "0" : "") + day + "." + (month < 10 ? "0" : "") + month + "." + year);


                        int sec = cal.get(Calendar.SECOND);
                        int minute = cal.get(Calendar.MINUTE);
                        int hour = cal.get(Calendar.HOUR_OF_DAY);
                        String dpoint = ":";
                        if(!secOn){
                            dpoint = " ";
                            secOn = true;
                        } else{
                            secOn = false;
                        }

                        //Je nach Tageszeit ändert sich die Begrüßung
                        if(hour >= 12 && hour <14){
                            greetText = "Schönen Mittag";
                        }else if(hour >= 14 && hour < 18){
                            greetText = "Guten Tag";
                        }else if(hour >= 18 && hour <= 23){
                            greetText = "Guten Abend";
                        }else if(hour >= 0 && hour < 12){
                            greetText = "Guten Morgen";
                        }


                        clockLabel.setText((hour < 10 ? "0" : "") + hour + dpoint + (minute < 10 ? "0" : "") + minute);

                        changeTimer++;
                        if(changeTimer == 1.5*changeTime && userList.size() > 1){
                            /**nach der Festgelegten Zeit in Sekunden wechselt der User (1,5 * x sekunden, weil auch die 
                             * Doppelpunkte der Uhr über dieses Update laufen und ein Durchlauf pro Sekunde weniger gut 
                             * aussieht als ein Durchlauf pro 2/3 Sekunde)
                             **/
                            if(currentProfile == nameLabels.size()-1){
                                currentProfile = 0;
                            }else{
                                currentProfile++;
                            }
                            firstTermPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primeColor), "Nächster", 1, 2, new Font("Arial", Font.BOLD, 15), primeColor));
                            greetingPanel.setBackground(primeColor);
                            setGreetName(userList.get(currentProfile).getFirstName());
                            firstTermPanel.removeAll();
                            otherTermsPanel.removeAll();
                            createAppointments();
                            changeTimer = 0;
                            for(int i = 0; i < userList.size(); i++){
                                //alle Namen in der rechten Liste bekommen eine unsichtbare Umrandung
                                nameLabels.get(i).setBorder(BorderFactory.createMatteBorder(1, 10, 1, 1, new Color(0,0,0,0)));
                            }
                            //dann wird dem neuen Profil die sichtbare Umrandung wieder hinzugefügt
                            nameLabels.get(currentProfile).setBorder(BorderFactory.createMatteBorder(1, 10, 1, 1, primeColor));
                        }

                        //ein Durchlauf alle 2/3 Sekunden (aus ästhetischen Gründen)
                        sleep(666);
                    }
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        
        clock.start();
    }
    
    public void appointment(int appDay, int appMonth, int beginTimeHours, int beginTimeMinutes, int endTimeHours, int endTimeMinutes, String text, int index, JPanel panel, int count, int num){
        //////Einzelner Termin/////
        
        int tempDay = appDay;
        int tempMonth = appMonth;

        JPanel termPanel = new JPanel(new GridBagLayout());
        JPanel termDatePanel = new JPanel(new GridBagLayout());
        JPanel termFromToPanel = new JPanel(new GridBagLayout());
        JPanel termContentPanel = new JPanel();
        JLabel termDateLabel = new JLabel();
        JLabel termFromToLabel = new JLabel();
        JLabel termContentLabel = new JLabel();
        if(tempDay == day && tempMonth == month){
            termDateLabel.setText("Heute");
        }else if(tempDay == (day+1) && tempMonth == month){
            termDateLabel.setText("Morgen");
        }else{
            termDateLabel.setText((tempDay < 10 ? "0" : "") + tempDay + "." + (tempMonth < 10 ? "0" : "") + tempMonth + ".");
        }
        int fontSize = 14 + (20-num)/2;
        termDateLabel.setFont(new Font("Arial", Font.BOLD, fontSize));


        termDatePanel.setBackground(primeColor);
        termDatePanel.add(termDateLabel);
        termDatePanel.setBorder(BorderFactory.createLineBorder(primeColor, 2));


        termFromToLabel.setText(beginTimeHours + ":" + beginTimeMinutes + " - " + endTimeHours + ":" + endTimeMinutes);
        termFromToLabel.setFont(new Font("Arial", Font.BOLD, 14));
        termFromToLabel.setForeground(new Color(200,200,200));


        termFromToPanel.add(termFromToLabel);
        termFromToPanel.setBackground(new Color(60,60,60));


        //int tempNoun = (int)(Math.random() * termTextNouns.length);
        //int tempDo = (int)(Math.random() * termTextDos.length);

        //termContentLabel.setText(termTextNouns[tempNoun] + " " + termTextDos[tempDo]);
        termContentLabel.setText(text);
        termContentLabel.setForeground(primeColor);
        termContentLabel.setFont(new Font("Arial", Font.BOLD, fontSize));


        termContentPanel.setOpaque(false);
        termContentPanel.add(termContentLabel);
        termContentPanel.setLayout(new BoxLayout(termContentPanel, BoxLayout.X_AXIS));
        termContentPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        GridBagConstraints tempCons = new GridBagConstraints();
        termPanel.setOpaque(false);
        termPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        tempCons.gridx = 0;
        tempCons.gridy = index;
        tempCons.weightx = 1;
        tempCons.weighty = 1;
        tempCons.fill = GridBagConstraints.BOTH;
        tempCons.insets = new Insets(5,0,0,0);
        panel.add(termDatePanel, tempCons);

        tempCons.gridx = 1;
        tempCons.gridy = index;
        tempCons.weightx = 1;
        tempCons.weighty = 1;
        tempCons.fill = GridBagConstraints.BOTH;
        tempCons.insets = new Insets(5,0,0,0);
        panel.add(termFromToPanel, tempCons);

        tempCons.gridx = 2;
        tempCons.gridy = index;
        tempCons.weightx = 40;
        tempCons.weighty = 1;
        tempCons.anchor = GridBagConstraints.FIRST_LINE_START;
        tempCons.fill = GridBagConstraints.BOTH;
        tempCons.insets = new Insets(5,1,0,0);
        panel.add(termContentPanel, tempCons);
        if(index >= count){
            //sollten weniger Termine vorliegen als minimal festgelegt werden trotzdem Termine (unsichtbar) gezeichnet,
            //um einer vertikalen Streckung vorzubeugen
            termDateLabel.setText(null);
            termContentLabel.setText(null);
            termDatePanel.setOpaque(false);
            termDatePanel.setBorder(null);
            termContentPanel.setBorder(null);
            termFromToLabel.setText(null);
            termFromToPanel.setOpaque(false);
        }
    }
    
    public void createAppointments(){
        //Methode zur Neuausrichtung der Termine, wird zu jedem Userwechsel aufgerufen, richtet sich nach dem aktuellen User
        firstTermPanel = new JPanel(new GridBagLayout());
        firstTermPanel.setOpaque(false);
        firstTermPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primeColor), "Nächster", 1, 2, new Font("Arial", Font.BOLD, 15), primeColor));
        
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        
        JPanel fillerPanel2 = new JPanel();
        fillerPanel2.setOpaque(false);
        
        otherTermsPanel = new JPanel();
        otherTermsPanel.setLayout(new GridBagLayout());
        otherTermsPanel.setOpaque(false);
        
        
        int countTerms = appointmentsList.get(currentProfile).size();
        int numTerms = 0;
        int maxTerms = 20;
        int minTerms = 8;
        if(countTerms < minTerms){
            numTerms = minTerms;
        } else if(countTerms >= minTerms && countTerms <=maxTerms){
            numTerms = countTerms;
        }else if(countTerms > maxTerms){
            numTerms = maxTerms;
        }
        for(int i = 0; i < numTerms; i++){
            if(i == 0){
                //der erste Termin kommt immer in die große Reihe
                appointment(appointmentsList.get(currentProfile).get(i).day, appointmentsList.get(currentProfile).get(i).month, 
                            appointmentsList.get(currentProfile).get(i).beginTimeHours, appointmentsList.get(currentProfile).get(i).beginTimeMinutes, 
                            appointmentsList.get(currentProfile).get(i).endTimeHours, appointmentsList.get(currentProfile).get(i).endTimeMinutes, 
                            appointmentsList.get(currentProfile).get(i).text, i, firstTermPanel, 1, 0);
            } else{
                if(i < appointmentsList.get(currentProfile).size()){
                    //für alle Termine die in der Liste wirklich vorhanden sind
                    appointment(appointmentsList.get(currentProfile).get(i).day, appointmentsList.get(currentProfile).get(i).month, 
                                 appointmentsList.get(currentProfile).get(i).beginTimeHours, appointmentsList.get(currentProfile).get(i).beginTimeMinutes, 
                                 appointmentsList.get(currentProfile).get(i).endTimeHours, appointmentsList.get(currentProfile).get(i).endTimeMinutes, 
                                 appointmentsList.get(currentProfile).get(i).text, i, otherTermsPanel, countTerms, numTerms); 
                 }else{
                    //für alle Termine, die nicht mehr in der Liste sind, aber dennoch unsichtbar gezeichnet werden
                    appointment(appointmentsList.get(currentProfile).get(0).day, appointmentsList.get(currentProfile).get(0).month, 
                             appointmentsList.get(currentProfile).get(0).beginTimeHours, appointmentsList.get(currentProfile).get(0).beginTimeMinutes, 
                             appointmentsList.get(currentProfile).get(0).endTimeHours, appointmentsList.get(currentProfile).get(0).endTimeMinutes, 
                             appointmentsList.get(currentProfile).get(0).text, i, otherTermsPanel, countTerms, numTerms); 
                }
            }
            
        }
        
        //Anordnung aller Teile im Kalender
        cTerms.gridx = 1;
        cTerms.gridy = 0;
        cTerms.gridwidth = 2;
        cTerms.weightx = 0.5;
        cTerms.weighty = 0.5;
        cTerms.insets  = new Insets(20,0,1,2);
        cTerms.fill = GridBagConstraints.BOTH;
        calendarPanel.add(firstTermPanel, cTerms);
        
        cTerms.gridx = 0;
        cTerms.gridy = 0;
        cTerms.gridwidth = 1;
        cTerms.weightx = 0.2;
        cTerms.weighty = 3;
        cTerms.gridheight = 2;
        cTerms.fill = GridBagConstraints.BOTH;
        calendarPanel.add(fillerPanel, cTerms);
        
        cTerms.gridx = 1;
        cTerms.gridy = 1;
        cTerms.gridwidth = 1;
        cTerms.weightx = 0.2;
        cTerms.weighty = 20;
        cTerms.fill = GridBagConstraints.BOTH;
        calendarPanel.add(fillerPanel2, cTerms);
        
        cTerms.gridx = 2;
        cTerms.gridy = 1;
        cTerms.gridwidth = 1;
        cTerms.weightx = 2;
        cTerms.weighty = 2.5;
        cTerms.insets  = new Insets(1,0,2,2);
        cTerms.fill = GridBagConstraints.BOTH;
        calendarPanel.add(otherTermsPanel, cTerms);
        
        
    }
    
    public void setGreetName(String s){
        //setzt den Namen im Begrüßungstext (wird zu jedem Wechsel neu aufgerufen)
        greetLabel.setText(greetText + " " + s);
    }
    
    public void setColor(Color c){
        //setzt die (Primär-)Farbe des Dashboards
        primeColor = c;
    }
    
    public void addUser(String foreName, String surName, ArrayList<Appointment> appointments){
        //setzt Vor- und Nachname sowie die Liste der Termine für jeden User
        EyDashUser user = new EyDashUser();
        user.setFirstName(foreName);
        user.setLastName(surName);
        
        userList.add(user);
        appointmentsList.add(appointments);
    }
    
    public void addUser(EyDashUser user) {
        userList.add(user);
    }
    
    public void setChangeTime(int seconds){
        //setzt das Intervall in Sekunden, in dem die einzelnen User wechseln sollen
        changeTime = seconds;
    }

}
    




