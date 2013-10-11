/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.AirplaneDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Samo
 */
public class AirplaneDAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static AirplaneDAO airDAO;

    @BeforeClass
    public static void init(){
        emf = Persistence.createEntityManagerFactory("InMemoryTestPU");
        airDAO = new AirplaneDAOImpl(emf);
    }
    
    @AfterClass
    public static void closeFactory(){
        emf.close();
    }
    
    @Before
    public void initTest(){
        em = emf.createEntityManager();
    }
    
    @After
    public void close() {
        em.close();
    }
    
    @Test
    public void createAirplane(){
        //everything right
        Airplane airplane1 = createAirplane(10, "GERT-Y", "Lockheed McDonnel");
        try{
            airDAO.createAirplane(airplane1);
        } catch(Exception ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        
        em.getTransaction().begin();
        Airplane airplaneFromDB = em.find(Airplane.class, airplane1.getId());
        assertDeepEquals(airplaneFromDB, airplane1);
        em.getTransaction().commit();
        
        //with null
        try{
            airDAO.createAirplane(null);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch (Exception ex) {
            fail("IllegalArgumentException expected" + ex.getMessage());
        }
        
        //with null params
        Airplane airplane2 = createAirplane(1, null, null);
        try{
            airDAO.createAirplane(airplane2);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch (Exception ex) {
            fail("IllegalArgumentException expected" + ex.getMessage());
        }
        
        //negative capacity
        try{
            Airplane airplane3 = createAirplane(-10, "", "");
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch (Exception ex) {
            fail("IllegalArgumentException expected" + ex.getMessage());
        }
    }
    
    @Test
    public void updateAirplane(){
        Airplane airplane1 = createAirplane(100, "RYANX", "Boeing 747");
        em.getTransaction().begin();
        em.persist(airplane1);
        em.getTransaction().commit();
        //OK
        try{
            airDAO.updateAirplane(airplane1);
        }catch(Exception ex){
            fail("Bad exception"+ ex.getMessage());
        }
        
        em.getTransaction().begin();
        Airplane airplaneFromDB = em.find(Airplane.class, airplane1.getId());
        assertDeepEquals(airplaneFromDB, airplane1);
        em.getTransaction().commit();
        
        //with null
        try{
            airDAO.updateAirplane(null);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch (Exception ex) {
            fail("IllegalArgumentException expected" + ex.getMessage());
        }
        //setNulls
        em.getTransaction().begin();
        Airplane airplane2 = em.find(Airplane.class, airplane1.getId());
        em.getTransaction().commit();
        airplane2.setName(null);
        airplane2.setType(null);
        try{
            airDAO.updateAirplane(airplane2);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch(Exception ex){
            fail("bad exception"+ ex.getMessage());
        }
        
        //not in DB
        Airplane airplane3 = createAirplane(100, "Air Force One", "Boeing 747");
        try{
            airDAO.updateAirplane(airplane3);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex){
        } catch(Exception ex){
            fail("bad exception" + ex.getMessage());
        }
        
        
    }
    
    @Test
    public void removeAirplane(){
        Airplane airplane1 = createAirplane(100, "RYANX", "Boeing 747");
        em.getTransaction().begin();
        em.persist(airplane1);
        em.getTransaction().commit();
        //ok
        try{
            airDAO.removeAirplane(airplane1);
        }catch(Exception ex){
            fail("Exception thrown" + ex.getMessage());
        }
        //with null
        try{
            airDAO.removeAirplane(null);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch (Exception ex) {
            fail("IllegalArgumentException expected" + ex.getMessage());
        }
        //notInDB
        Airplane airplane2 = createAirplane(100, "RYANY", "Boeing 737-B");
        em.getTransaction().begin();
        em.persist(airplane2);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        em.remove(airplane2);
        em.getTransaction().commit();
        
        try{
            airDAO.removeAirplane(airplane2);
            fail("No exception thrown");
        }catch(JPAException ex){
        }catch(Exception ex){
            fail("Bad Exception " + ex.getMessage());
        }
    }
    
    @Test
    public void getAirplane(){
        Airplane airplane1 = createAirplane(100, "RYANX", "Boeing 747");
        em.getTransaction().begin();
        em.persist(airplane1);
        em.getTransaction().commit();
        //ok
        try{
            airDAO.getAirplane(airplane1.getId());
        }catch(Exception ex){
            fail("Exception thrown");
        }
        
        em.getTransaction().begin();
        Airplane airplane2 = em.find(Airplane.class, airplane1.getId());
        assertDeepEquals(airplane2, airplane1);
        em.getTransaction().commit();
        //with null
        try{
            airDAO.getAirplane(null);
            fail("No exception thrown");
        } catch(IllegalArgumentException ex) {
        } catch (Exception ex) {
            fail("IllegalArgumentException expected" + ex.getMessage());
        }
        //notInDB
        Airplane airplane3 = createAirplane(100, "RYANZ", "Airbus A-100");
        em.getTransaction().begin();
        em.persist(airplane3);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        em.remove(airplane3);
        em.getTransaction().commit();
        
        try{
            airDAO.getAirplane(airplane3.getId());
            fail("No exception thrown");
        }catch(JPAException ex){
        }catch(Exception ex){
            fail("Bad exception" + ex.getMessage());
        }
        
    }
    
    @Test
    public void getAllAirplanes(){
        Airplane airplane1 = createAirplane(100, "RYANA", "Boeing 737");
        Airplane airplane2 = createAirplane(100, "RYANB", "Boeing 737");
        Airplane airplane3 = createAirplane(100, "RYANC", "Boeing 737");
        em.getTransaction().begin();
        em.persist(airplane1);
        em.persist(airplane2);
        em.persist(airplane3);
        em.getTransaction().commit();
        //ok
        List<Airplane> airplaneList1 = new ArrayList<Airplane>();
        try{
            airplaneList1 = airDAO.getAllAirplanes();
        }catch(Exception ex){
            fail("Exception thrown" + ex.getMessage());
        }
        
        List<Airplane> airplaneList2 = new ArrayList<Airplane>();
        TypedQuery<Airplane> query1 = em.createNamedQuery(
            "Airplane.findAllAirplanes", Airplane.class);
        for (Airplane a : query1.getResultList()) {
            airplaneList2.add(a);
        }
        assertDeepEqualsAirplanesLists(airplaneList1, airplaneList2);
        
        em.getTransaction().begin();
        em.remove(airplane1);
        em.remove(airplane2);
        em.remove(airplane3);
        em.getTransaction().commit();
        
        //null OK
        List<Airplane> airplaneList3 = new ArrayList<Airplane>();
        List<Airplane> airplaneList4 = new ArrayList<Airplane>();
        try{
            airplaneList3 = airDAO.getAllAirplanes();
        }catch(Exception ex){
            fail("Exception thrown" + ex.getMessage());
        }
        assertDeepEqualsAirplanesLists(airplaneList3, airplaneList4);
    }
    
    @Test
    public void getAllAirplanesFlights(){
        Airplane airplane = createAirplane(16, "GERTY", "Lockheed McDonnel");
        Destination from = createDestiantion();
        Destination to = createDestiantion();
        Steward s = createSteward();
        Flight flight1 = createFlight(airplane, from, to, s);
        Flight flight2 = createFlight(airplane, to, from, s);
        em.getTransaction().begin();
        em.persist(airplane);
        em.persist(from);
        em.persist(to);
        em.persist(s);
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.persist(flight1);
        em.persist(flight2);
        em.getTransaction().commit();
        
        List<Flight> flightList = new ArrayList<Flight>();
        //null
        try{
            airDAO.getAllAirplanesFlights(null);
            fail("No exception thrown");
        }catch(IllegalArgumentException ex){
        }catch(Exception ex){
            fail("Bad exception" + ex.getMessage());
        }
        //ok
        try{
            flightList = airDAO.getAllAirplanesFlights(airplane);
        }catch(Exception ex){
            fail("Exception thrown");
        }
        List<Flight> flightList2 = new ArrayList<Flight>();
        TypedQuery<Flight> query1 = em.createNamedQuery(
            "Flight.findByAirplane", Flight.class);
        query1.setParameter("airplane", airplane.getId());
        for (Flight f : query1.getResultList()) {
            flightList2.add(f);
        }
        assertDeepEqualsFlightsLists(flightList, flightList2);
    }
    
    
    
    
    private void assertDeepEqualsAirplanesLists(List<Airplane> l1, List<Airplane> l2){
        if (l1.size() != 0 && l2.size() != 0){
            for (int i = 0; i < l1.size(); i++) {
                Airplane a1 = l1.get(i);
                Airplane a2 = l2.get(i);
                assertDeepEquals(a1, a2);
            }
        }
    }
    
    private void assertDeepEqualsFlightsLists(List<Flight> l1, List<Flight> l2){
        if (l1.size() != 0 && l2.size() != 0){
            for (int i = 0; i < l1.size(); i++) {
                Flight f1 = l1.get(i);
                Flight f2 = l2.get(i);
                assertDeepEqualsFlights(f1, f2);
            }
        }
    }
    
    private void assertDeepEquals(Airplane a1, Airplane a2){
        assertEquals(a1.getId(), a2.getId());
        assertEquals(a1.getCapacity(), a2.getCapacity());
        assertEquals(a1.getName(), a2.getName());
        assertEquals(a1.getType(), a2.getType());
    }
    
    private void assertDeepEqualsFlights(Flight f1, Flight f2){
        assertEquals(f1.getId(), f2.getId());
        assertDeepEquals(f1.getAirplane(), f2.getAirplane());
        assertEquals(f1.getArrivalTime(), f2.getArrivalTime());
        assertEquals(f1.getDepartureTime(), f2.getDepartureTime());
    }
    
    private static Airplane createAirplane(int capacity, String name, String type) {
        Airplane airplane = new Airplane();
        airplane.setCapacity(capacity);
        airplane.setName(name);
        airplane.setType(type);
        return airplane;
    }
    
    private static Destination createDestiantion(){
        Destination des = new Destination();
        des.setCode("BLS");
        des.setCity("Bratislava");
        des.setCountry("CSlovakia");
        return des;
    }
    
    private static Steward createSteward(){
        Steward s = new Steward();
        s.setFirstName("Jan");
        s.setLastName("Jesenius");
        return s;
    }
    
    private static Flight createFlight(Airplane air, Destination from, Destination to, Steward s){
        Flight f = new Flight();
        f.setAirplane(air);
        f.setOrigin(from);
        f.setArrivalTime(new Timestamp(10000L));
        f.setDepartureTime(new Timestamp(10000L));
        f.setOrigin(from);
        f.setTarget(to);
        List<Steward> stewardList = new ArrayList<Steward>();
        stewardList.add(s);
        f.setStewardList(stewardList);
        return f;
    }
}

