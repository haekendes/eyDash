/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyDash.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Robin Christ
 */
@Entity
@Table(name = "appointments", schema = "public")
public class EyDashAppointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private Long id;
    
    @Column(name = "appointmentid")
    private long appointmentID;
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "appointmentdate")
    private java.sql.Date appointmentDate;
    
//        Calendar calendar = Calendar.getInstance();
//    calendar.set(Calendar.YEAR, 1990);
//    calendar.set(Calendar.DAY_OF_MONTH, 1);
//    calendar.set(Calendar.MONTH, 4); // Assuming you wanted May 1st
//
//    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EyDashAppointment)) {
            return false;
        }
        EyDashAppointment other = (EyDashAppointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hibernatetest4.Appointment[ id=" + id + " ]";
    }
    
}
