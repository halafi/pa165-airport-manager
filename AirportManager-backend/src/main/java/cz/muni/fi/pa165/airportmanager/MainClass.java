/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager;

import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Chorke
 */
public class MainClass {
    
    public static final EntityManagerFactory EM_FACTORY = 
            Persistence.createEntityManagerFactory("AirportManager");
    
    public static void main(String... atgs){
//        chorkeTest();
    }
    
    private static void chorkeTest(){
        EntityManager man = EM_FACTORY.createEntityManager();
        Airplane ap = new Airplane();
        ap.setCapacity(10);
        ap.setName("air");
        ap.setType("boeing");
        Airplane ap1 = new Airplane();
        ap1.setCapacity(100);
        ap1.setName("airplane excelent");
        ap1.setType("boeing 747");
        
        
        man.getTransaction().begin();
        man.persist(ap);
        man.persist(ap1);
        man.getTransaction().commit();
        
        ap.setName("airplane");
        man.clear();
        man.close();
        man = EM_FACTORY.createEntityManager();
        man.getTransaction().begin();
        man.merge(ap);
        man.getTransaction().commit();
        
        man.getTransaction().begin();
        man.remove(ap1);
        man.getTransaction().commit();
        
        System.out.println(ap1);
    }
}
