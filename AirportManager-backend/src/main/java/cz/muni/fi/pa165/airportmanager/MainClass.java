/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager;

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
        
    }
}
