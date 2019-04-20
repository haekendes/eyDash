/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hibernatetest4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Robin Christ
 */
public class DatabaseManager {
    
    public void monkeySeeMonkeyDo() {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("com.mycompany_HibernateTest4_jar_1.0-SNAPSHOTPU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

//        TestBitch c = new TestBitch();
//        c.setHelloWorld("Hello World!");
//        entityManager.persist(c);

        Test t = new Test();
        t.setName("Hello World!");
        entityManager.persist(t);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
