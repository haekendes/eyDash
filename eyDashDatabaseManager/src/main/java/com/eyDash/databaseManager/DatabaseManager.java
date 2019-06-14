/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyDash.databaseManager;

import com.eyDash.entities.EyDashUser;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Robin Christ
 */
public class DatabaseManager {
    
    private EntityManagerFactory entityManagerFactory;
    private ArrayList<EyDashUser> userList;
    
    public DatabaseManager() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("com.eyDash_DatabaseManager_jar_1.0");
        userList = new ArrayList();
    }
    
//    public EyDashUser getUserByID(int idParam) {
//        EntityManager em = entityManagerFactory.createEntityManager();
//        String query = "FROM EyDashUser u WHERE u.id = :ID";
//        
//        TypedQuery<EyDashUser> tq = em.createQuery(query, EyDashUser.class);
//        tq.setParameter("ID", idParam);
//        
//        return tq.getSingleResult();
//    }
    
    public EyDashUser getUserByBluetoothAdress(String adress) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String query = "FROM EyDashUser u WHERE u.bluetoothAdress = :BluetoothAdress";
        
        TypedQuery<EyDashUser> tq = em.createQuery(query, EyDashUser.class);
        tq.setParameter("BluetoothAdress", adress);
        
        EyDashUser user = tq.getSingleResult();
        
        userList.add(user);
        
        return user;
    }
    
    public void createUser(String firstName, String lastName, byte[] token, String bluetoothName, String bluetoothAdress) {
        EyDashUser u = new EyDashUser();

        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setToken(token);
        u.setBluetoothName(bluetoothName);
        u.setBluetoothAdress(bluetoothAdress);
        
        executeTransaction(u);
    }
    
//    public void createAppointment() {
//        
//    }
    
    private void executeTransaction(Object o) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        entityManager.persist(o);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * @return the userList
     */
    public ArrayList<EyDashUser> getUserList() {
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(ArrayList<EyDashUser> userList) {
        this.userList = userList;
    }
    
    /**
     * Test function.
     */
//    public void monkeySeeMonkeyDo() {
//        EntityManagerFactory entityManagerFactory
//                = Persistence.createEntityManagerFactory("com.mycompany_HibernateTest4_jar_1.0-SNAPSHOTPU");
//
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
////        TestBitch c = new TestBitch();
////        c.setHelloWorld("Hello World!");
////        entityManager.persist(c);
//        
//        Test t = new Test();
//        t.setName("Hello World!");
//        entityManager.persist(t);
//
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();
//    }
    
//    public EyDashUser getUserByPhoneNumber(int phoneNumberParam) {
//        EntityManager em = entityManagerFactory.createEntityManager();
//        String query = "FROM EyDashUser u WHERE u.phoneNumber = :phoneNumber";
//        
//        TypedQuery<EyDashUser> tq = em.createQuery(query, EyDashUser.class);
//        tq.setParameter("phoneNumber", phoneNumberParam);
//        
//        return tq.getSingleResult();
//    }
}
