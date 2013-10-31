/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.dao;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Juraj Duráni
 */
public class DestinationDAOImplTest extends AbstractTest{
    
//    private static final ResourceBundle bundle = ResourceBundle.getBundle("testing");
    
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager manager;
    
    @Autowired
    private DestinationDAO destDAO;
    
    @Before
    public void init(){
//        emf = Persistence.createEntityManagerFactory(bundle.getString("testingUNIT"));
        manager = emf.createEntityManager();
    }
    
    @After
    public void closing(){
//        manager.getTransaction().begin();
//        manager.createQuery("DELETE FROM Steward s").executeUpdate();
//        manager.createQuery("DELETE FROM Airplane a").executeUpdate();
//        manager.createQuery("DELETE FROM Flight f").executeUpdate();
//        System.out.println(manager.createQuery("DELETE FROM Destination").executeUpdate());
//        manager.getTransaction().commit();
        manager.clear();
        manager.close();
//        emf.close();
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
        
        Destination des = createDestiantion(null, null, null);
        try{
            destDAO.createDestination(des);
            fail("Destinations atributes null - no exception");
        } catch(IllegalArgumentException ex){
        } catch(Exception e){
            fail("Destinations atributes null - bad exception " + e);
        }
        des = createDestiantion("", "", "");
        try{
            destDAO.createDestination(des);
            fail("Destinations atributes empty - no exception");
        } catch(IllegalArgumentException ex){
        } catch(Exception e){
            fail("Destinations atributes empty - bad exception " + e);
        }
        
        des = createDestiantion("SVK", "Slovakia", "Poprad");
        try{
            destDAO.createDestination(des);
            System.out.println("0000000000000000000000000000000000000000000000   "
                    + des.getId());
        } catch(Exception e){
            if(!(e instanceof JPAException)){
                fail("Destinations atributes OK - exception thrown " + e);
            }
        }
        manager.getTransaction().begin();
        Destination result = manager.find(Destination.class, des.getId());
        assertDeepEquals(result, des);
        manager.getTransaction().commit();
        //clearing
        removeFromDB(des);
    }
    
    @Test
    public void updateDestinationTest(){
        Destination des1 = createDestiantion("SVK", "Slovakia", "Poprad");
        Destination des2 = createDestiantion("SVK", "Slovakia", "Bratislava");
        
        manager.getTransaction().begin();
        manager.persist(des1);
        manager.persist(des2);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        manager.remove(des2);
        manager.getTransaction().commit();
        
        des1.setCity("Kosice");
        try{
            destDAO.updateDestination(des1);
        } catch (Exception ex){
            fail("samo wrote this"+ex);//SAMO
        }
        
        manager.getTransaction().begin();
        Destination result = manager.find(Destination.class, des1.getId());
        assertDeepEquals(des1, result);
        
        try{
            des2.setCity("Presov");
            destDAO.updateDestination(des2);
            fail("Updated absent destination");
        } catch (Exception ex){
            if(!(ex instanceof JPAException)){
                fail("Update destination absent destination - bad exception " + ex);
            }
        }
        
        try{
            Destination des3 = createDestiantion("SVK", "Slovakia", "Nitra");
            destDAO.updateDestination(des3);
            fail("Updated absent destination");
        } catch (IllegalArgumentException ex){
        } catch (Exception ex){
            fail("Update destination absent destination - bad exception " + ex);
        }
        try{
            des1.setCity(null);
            des1.setCode(null);
            des1.setCountry(null);
            destDAO.updateDestination(des1);
            fail("Update destination atributes null - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Update destination atributes null - bad exception " + ex);
        }
        try{
            des1.setCity("");
            des1.setCode("");
            des1.setCountry("");
            destDAO.updateDestination(des1);
            fail("Update destination atributes empty - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Update destination atributes empty - bad exception " + ex);
        }
        try{
            destDAO.updateDestination(null);
            fail("Update destination null argument - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Update destination null argument - bad exception " + ex);
        }
        //clearing
        removeFromDB(des1);
    }
    
    @Test
    public void removeDestinationTest(){
        Destination des1 = createDestiantion("SVK", "Slovakia", "Poprad");
        Destination des2 = createDestiantion("SVK", "Slovakia", "Bratislava");
        
        manager.getTransaction().begin();
        manager.persist(des1);
        manager.getTransaction().commit();
        
        try{
            destDAO.removeDestination(null);
            fail("Remove destination null argument - no exception");
        } catch(IllegalArgumentException e){
        } catch(Exception ex){
            fail("Remove destination null argument - bad exception " + ex);
        }
        try{
            destDAO.removeDestination(des2);
            fail("Remove destination absent destination - no exception");
        } catch (IllegalArgumentException ex){
        } catch (Exception ex){
            fail("Remove destination absent destination - bad exception " + ex);
        }
        try{
            destDAO.removeDestination(des1);
        } catch(Exception ex){
            fail("Remove destination OK argumen - Exception thrown " + ex);
        }
        manager.clear();
        manager.getTransaction().begin();
        Destination result = manager.find(Destination.class, des1.getId());
        manager.getTransaction().commit();
        if(result != null){
            fail("Remove destination OK argument - destination has not been removed");
        }
        //clearing
        removeFromDB(des1);
    }
    
    @Test
    public void getDestinationTest(){
        Destination des1 = createDestiantion("SVK", "Slovakia", "Poprad");
        Destination des2 = createDestiantion("SVK", "Slovakia", "Poprad");
        manager.getTransaction().begin();
        manager.persist(des1);
        manager.persist(des2);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        manager.remove(des2);
        manager.getTransaction().commit();
        
        try{
            destDAO.getDestination(null);
            fail("Get destination null argument - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Get destination null argument - bad exception " + ex);
        }
        
        try{
            Destination result = destDAO.getDestination(des2.getId());
            if(result != null){
                fail("Get destination absent destination found");
            }
        } catch (Exception ex){
            if(!(ex instanceof JPAException)){
                fail("Get destination absent destination - bad exception " + ex);
            }
        }
        
        try{
            Destination result = destDAO.getDestination(des1.getId());
            assertDeepEquals(des1, result);
        } catch (Exception ex){
            fail("Get destination OK argument - exception thrown " + ex);
        }
        //clearing
        removeFromDB(des1);
    }
    
    @Test
    public void getAllDestinationsTest(){
        Destination des1 = createDestiantion("SVK", "Slovakia", "Poprad");
        Destination des2 = createDestiantion("SVK", "Slovakia", "Bratislava");
        Destination des3 = createDestiantion("SVK", "Slovakia", "Kosice");
        Destination des4 = createDestiantion("SVK", "Slovakia", "Presov");
        
        try{
            List<Destination> resultDAO = destDAO.getAllDestinations();
            if(!resultDAO.isEmpty()){
                fail("Get all destinations empty DB - some destinations found");
            }
        } catch (JPAException e){
            fail("Get all destinations empty DB - exception thrown");
        } catch (Exception ex){
            fail("Get all destinations empty DB - bad exception thrown " + ex);
        }
        
        manager.getTransaction().begin();
        manager.persist(des1);
        manager.persist(des2);
        manager.persist(des3);
        manager.getTransaction().commit();
        
        getAllDestHelpTestingMethod("Get all destinations non empty DB");
        
        manager.getTransaction().begin();
        manager.persist(des4);
        manager.getTransaction().commit();
        
        getAllDestHelpTestingMethod("Get all destinations added destination");
        
        manager.getTransaction().begin();
        manager.remove(des2);
        manager.getTransaction().commit();
        
        getAllDestHelpTestingMethod("Get all destinations removed destination");
        //clearing
        removeFromDB(des1, des3, des4);
    }
    
    private void getAllDestHelpTestingMethod(String message){
        EntityManager man = emf.createEntityManager();
        List<Destination> resultDAO;
        List<Destination> result;
        man.getTransaction().begin();
        result = man.createQuery("SELECT d FROM Destination d",
                Destination.class).getResultList();
        man.getTransaction().commit();
        try{
            resultDAO = destDAO.getAllDestinations();
            assertDeepEqualsDest(result, resultDAO);
        } catch (JPAException e){
            fail(message + " - exception thrown");
        } catch (Exception ex){
            fail(message + " - bad exception thrown " + ex);
        }
    }
    
    @Test
    public void getAllIncomingFlightsTest(){
        Destination des1 = createDestiantion("SVK", "Slovakia", "Poprad");
        Destination des2 = createDestiantion("SVK", "Slovakia", "Bratislava");
        Destination des3 = createDestiantion("SVK", "Slovakia", "Kosice");
        
        manager.getTransaction().begin();
        manager.persist(des1);
        manager.persist(des2);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        manager.remove(des2);
        manager.getTransaction().commit();
        
        try{
            destDAO.getAllIncomingFlights(null);
            fail("Get all incoming flights null argument - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Get all incoming flights null argument - bad ecxeption");
        }
        
        try{
            destDAO.getAllIncomingFlights(des2);
            fail("Get all incoming flights absent (removed) argument - no exception");
        } catch (JPAException e){
        } catch (Exception ex){
            fail("Get all incoming flights absent (removed) argument - bad ecxeption");
        }
        
        try{
            destDAO.getAllIncomingFlights(des3);
            fail("Get all incoming flights absent argument - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Get all incoming flights absent argument - bad ecxeption");
        }
        
        try{
            List<Flight> result = destDAO.getAllIncomingFlights(des1);
            if(!result.isEmpty()){
                fail("Get all incoming flights no incomming flights");
            }
        } catch (JPAException ex){
            fail("Get all incoming flights no incoming flight - exception thrown");
        } catch (Exception ex){
            fail("Get all incoming flights no incoming flight - bad exception");
        }
        
        Destination start = createDestiantion("SVK", "Slovakia", "Presov");
        Destination end = createDestiantion("SVK", "Slovakia", "Nitra");
        
        Flight f1 = createFlight(start, des1);
        Flight f2 = createFlight(des1, end);
        Flight f3 = createFlight(des1, start);
        Flight f4 = createFlight(end, des1);
        
        manager.getTransaction().begin();
        manager.persist(start);
        manager.persist(end);
        manager.persist(des1);
        manager.getTransaction().commit();
        
        manager.getTransaction().begin();
        manager.persist(f1);
        manager.persist(f2);
        manager.getTransaction().commit();
        
        getAllInOutcommingFlightsHelpTesingMethod("Get all incoming flights", des1, true);
        
        manager.getTransaction().begin();
        manager.persist(f3);
        manager.persist(f4);
        manager.getTransaction().commit();
        
        getAllInOutcommingFlightsHelpTesingMethod("Get all incoming flights added flight", des1, true);
        
        manager.getTransaction().begin();
        manager.remove(f1);
        manager.remove(f2);
        manager.getTransaction().commit();
        
        getAllInOutcommingFlightsHelpTesingMethod("Get all incoming flights removed flight", des1, true);
        //clearing
        removeFromDB(
                f3, f4, 
                des1, start, end);
    }
    
    private void getAllInOutcommingFlightsHelpTesingMethod(String message, Destination des, boolean incoming){
        EntityManager man = emf.createEntityManager();
        man.getTransaction().begin();
        TypedQuery<Flight> query;
        if(incoming){
            query = man.createQuery(
               "SELECT f "
                + "FROM Flight f "
                + "WHERE f.target.id = :desID",
               Flight.class);
        } else {
            query = man.createQuery(
               "SELECT f "
                + "FROM Flight f "
                + "WHERE f.origin.id = :desID",
               Flight.class);
        }
        query.setParameter("desID", des.getId());
        List<Flight> result = query.getResultList();
//        System.out.println(result);
        man.getTransaction().commit();
        
        try{
            List<Flight> resultDAO;
            if(incoming){
                resultDAO = destDAO.getAllIncomingFlights(des);
            } else {
                resultDAO = destDAO.getAllOutcomingFlights(des);
            }
            assertDeepEqualsFlig(result, resultDAO);
        } catch (JPAException e){
            fail(message + " - exception thrown");
        } catch (Exception ex){
            fail(message + " - bad exception");
        }
    }
    
    @Test
    public void getAllOutcomingFlightsTest(){
        Destination des1 = createDestiantion("SVK", "Slovakia", "Poprad");
        Destination des2 = createDestiantion("SVK", "Slovakia", "Bratislava");
        Destination des3 = createDestiantion("SVK", "Slovakia", "Kosice");
        
        manager.getTransaction().begin();
        manager.persist(des1);
        manager.persist(des2);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        manager.remove(des2);
        manager.getTransaction().commit();
        
        try{
            destDAO.getAllOutcomingFlights(null);
            fail("Get all outcoming flights null argument - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Get all outcoming flights null argument - bad ecxeption");
        }
        
        try{
            destDAO.getAllOutcomingFlights(des2);
            fail("Get all outcoming flights absent (removed) argument - no exception");
        } catch (JPAException e){
        } catch (Exception ex){
            fail("Get all outcoming flights absent (removed) argument - bad ecxeption");
        }
        
        try{
            destDAO.getAllOutcomingFlights(des3);
            fail("Get all outcoming flights absent argument - no exception");
        } catch (IllegalArgumentException e){
        } catch (Exception ex){
            fail("Get all outcoming flights absent argument - bad ecxeption");
        }
        
        try{
            List<Flight> result = destDAO.getAllOutcomingFlights(des1);
            if(!result.isEmpty()){
                fail("Get all outcoming flights no incomming flights");
            }
        } catch (JPAException ex){
            fail("Get all outcoming flights no incoming flight - exception thrown");
        } catch (Exception ex){
            fail("Get all outcoming flights no incoming flight - bad exception");
        }
        
        Destination start = createDestiantion("SVK", "Slovakia", "Presov");
        Destination end = createDestiantion("SVK", "Slovakia", "Nitra");
        
        
        
        Flight f1 = createFlight(start, des1);
        Flight f2 = createFlight(des1, end);
        Flight f3 = createFlight(des1, start);
        Flight f4 = createFlight(end, des1);
        
        manager.getTransaction().begin();
        manager.persist(start);
        manager.persist(end);
        manager.persist(des1);
        manager.getTransaction().commit();
        
        manager.getTransaction().begin();
        manager.persist(f1);
        manager.persist(f2);
        manager.getTransaction().commit();
        
        getAllInOutcommingFlightsHelpTesingMethod("Get all outcoming flights", des1, false);
        
        manager.getTransaction().begin();
        manager.persist(f3);
        manager.persist(f4);
        manager.getTransaction().commit();
        
        getAllInOutcommingFlightsHelpTesingMethod("Get all outcoming flights added flight", des1, false);
        
        manager.getTransaction().begin();
        manager.remove(f1);
        manager.remove(f2);
        manager.getTransaction().commit();
        
        getAllInOutcommingFlightsHelpTesingMethod("Get all outcoming flights removed flight", des1, false);
        //clearing
        removeFromDB(
                f3, f4, 
                des1, start, end);
    }
    
    private void assertDeepEqualsDest(List<Destination> des1, List<Destination> des2){
        if(des1 == null && des2 == null){ return; }
        if(des1 == null && des2 != null){ fail(); }
        if(des1 != null && des2 == null){ fail(); }
        if(des1.size() != des2.size()){ fail(); }
        Iterator i1 = des1.iterator();
        Iterator i2 = des2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            Destination d1 = (Destination)i1.next();
            Destination d2 = (Destination)i2.next();
            assertDeepEquals(d1, d2);
        }
    }
    
    private void assertDeepEqualsStew(List<Steward> stew1, List<Steward> stew2){
        if(stew1 == null && stew2 == null){ return; }
        if(stew1 == null && stew2 != null){ fail(); }
        if(stew1 != null && stew2 == null){ fail(); }
        if(stew1.size() != stew2.size()){ fail(); }
        Iterator i1 = stew1.iterator();
        Iterator i2 = stew2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            Steward d1 = (Steward)i1.next();
            Steward d2 = (Steward)i2.next();
            assertDeepEquals(d1, d2);
        }
    }
    
    private void assertDeepEqualsFlig(List<Flight> flig1, List<Flight> flig2){
        if(flig1 == null && flig2 == null){ return; }
        if(flig1 == null && flig2 != null){ fail(); }
        if(flig1 != null && flig2 == null){ fail(); }
        if(flig1.size() != flig2.size()){ fail(); }
        Iterator i1 = flig1.iterator();
        Iterator i2 = flig2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            Flight f1 = (Flight)i1.next();
            Flight f2 = (Flight)i2.next();
            assertDeepEquals(f1, f2);
        }
    }
    
    private void assertDeepEquals(Destination des1, Destination des2){
        assertEquals(des1.getId(), des2.getId());
        assertEquals(des1.getCode(), des2.getCode());
        assertEquals(des1.getCountry(), des2.getCountry());
        assertEquals(des1.getCity(), des2.getCity());
    }
    
    private void assertDeepEquals(Steward stew1, Steward stwe2){
        assertEquals(stew1.getId(), stwe2.getId());
        assertEquals(stew1.getFirstName(), stwe2.getFirstName());
        assertEquals(stew1.getLastName(), stwe2.getLastName());
    }
    
    private void assertDeepEquals(Flight f1, Flight f2){
        assertEquals(f1.getId(), f2.getId());
        assertEquals(f1.getAirplane(), f2.getAirplane());
        assertEquals(f1.getArrivalTime(), f2.getArrivalTime());
        assertEquals(f1.getDepartureTime(), f2.getDepartureTime());
        assertEquals(f1.getOrigin(), f2.getOrigin());
        assertDeepEqualsStew(f1.getStewardList(), f2.getStewardList());
        assertEquals(f1.getTarget(), f2.getTarget());
    }
    
    private static Destination createDestiantion(String code, String country, String city){
        Destination des = new Destination();
        des.setCode(code);
        des.setCity(city);
        des.setCountry(country);
        return des;
    }
    
    private static Steward createSteward(){
        Steward s = new Steward();
        s.setFirstName("first");
        s.setLastName("last");
        return s;
    }
    
    private Flight createFlight(Destination from, Destination to){
        Flight f = new Flight();
        Airplane ap = new Airplane();
        ap.setCapacity(100);
        ap.setName("mary");
        ap.setName("boeing");
        f.setAirplane(ap);
        f.setArrivalTime(new Timestamp(10000L));
        f.setDepartureTime(new Timestamp(10000L));
        f.setOrigin(from);
        f.setTarget(to);
        Steward s = createSteward();
        EntityManager man = emf.createEntityManager();
        man.getTransaction().begin();
        man.persist(s);
        man.persist(ap);
        man.getTransaction().commit();
        List<Steward> ls = new ArrayList<>();
        ls.add(s);
        f.setStewardList(ls);
        return f;
    }
    
    private void removeFromDB(Object... objs){
//        EntityManager man = emf.createEntityManager();
//        man.getTransaction().begin();
//        for(Object o : objs){
//            man.remove(o);
//        }
//        man.getTransaction().commit();
    }
}