/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.DestinationDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Chorke
 */
public class DestinationDAOTest {
    
    private static EntityManagerFactory emf;
//    private Map<String, String> prop;
    private static EntityManager manager;
    private static DestinationDAO destDAO;
    
    public DestinationDAOTest() {
//        prop = new HashMap<String, String>();
//        prop.put("hibernate.connection.url", "jdbc:derby:memory:testingDB");
    }
    
    
    @BeforeClass
    public static void init(){
        emf = Persistence.createEntityManagerFactory("AirportManager");
        destDAO = new DestinationDAOImpl();
    }
    
    @AfterClass
    public static void closing(){
        emf.close();
//        prop.clear();
    }
    
    @Before
    public void initTest(){
        manager = emf.createEntityManager();
    }
    
    @After
    public void finishedTest(){
        manager.clear();
        manager.close();
    }
    
    @Test
    public void createDestinationTest(){
        try{
            destDAO.createDestination(null);
            fail("Destination null - no exception");
        } catch(IllegalArgumentException ex){
        } catch(Exception e){
            fail("Destination null - bad exception " + e);
        }
        
        Destination des = createDetiantion(null, null, null);
        try{
            destDAO.createDestination(des);
            fail("Destinations atributes null - no exception");
        } catch(IllegalArgumentException ex){
        } catch(Exception e){
            fail("Destinations atributes null - bad exception " + e);
        }
        des = createDetiantion("", "", "");
        try{
            destDAO.createDestination(des);
            fail("Destinations atributes empty - no exception");
        } catch(IllegalArgumentException ex){
        } catch(Exception e){
            fail("Destinations atributes empty - bad exception " + e);
        }
        
        des = createDetiantion("SVK", "Slovakia", "Poprad");
        try{
            destDAO.createDestination(des);
        } catch(Exception e){
            if(!(e instanceof JPAException)){
                fail("Destinations atributes OK - exception thrown " + e);
            }
        }
        
        
    }
    
    private static Destination createDetiantion(String code, String country, String city){
        Destination des = new Destination();
        des.setCode(code);
        des.setCity(city);
        des.setCountry(country);
        return des;
    }
}