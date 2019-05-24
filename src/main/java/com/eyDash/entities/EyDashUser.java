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
@Table(name = "users", schema = "public")
public class EyDashUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "userid")
    private Integer id;
    
    @Column(name = "firstname")
    private String firstName;
    
    @Column(name = "lastname")
    private String lastName;
    
    @Column(name = "token")
    private byte[] token;
    
    @Column(name = "bluetoothname")
    private String bluetoothName;
    
    @Column(name = "bluetoothadress")
    private String bluetoothAdress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof EyDashUser)) {
            return false;
        }
        EyDashUser other = (EyDashUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hibernatetest4.User[ id=" + id + " ]";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the bluetoothName
     */
    public String getBluetoothName() {
        return bluetoothName;
    }

    /**
     * @param bluetoothName the bluetoothName to set
     */
    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    /**
     * @return the bluetoothAdress
     */
    public String getBluetoothAdress() {
        return bluetoothAdress;
    }

    /**
     * @param bluetoothAdress the bluetoothAdress to set
     */
    public void setBluetoothAdress(String bluetoothAdress) {
        this.bluetoothAdress = bluetoothAdress;
    }

    /**
     * @return the token
     */
    public byte[] getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(byte[] token) {
        this.token = token;
    }
}
