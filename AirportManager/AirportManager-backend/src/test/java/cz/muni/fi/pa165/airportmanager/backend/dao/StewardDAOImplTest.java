package cz.muni.fi.pa165.airportmanager.backend.dao;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tests for StewardDAOImpl.
 *
 * @author Filip
 */
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class StewardDAOImplTest extends AbstractTest {
    
    @Autowired
    private StewardDAO stewDAO;
    
    @Autowired
    private EntityManagerFactory emf;
    
    private EntityManager em;
    
    /**
     * Setup for each test.
     */
    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testingApplicationContext.xml");
        stewDAO = context.getBean(StewardDAO.class);
        emf = context.getBean(EntityManagerFactory.class);
        em = emf.createEntityManager();
    }
    /**
     * Test for get steward.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test
    public void testGetSteward() throws JPAException{
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewDAO.createSteward(steward1);
        stewDAO.createSteward(steward2);
        
        assertDeepEquals(stewDAO.getSteward(steward1.getId()),steward1);
        assertDeepEquals(stewDAO.getSteward(steward2.getId()),steward2);
    }
    
    /**
     * Test for getting steward with null id.
     */
    @Test
    public void testGetStewardWithNullId() {
        try {
            stewDAO.getSteward(null);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    /**
     * Test for getting all stewards.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test
    public void testGetAllStewards() throws JPAException {
        assertTrue(stewDAO.getAllStewards().isEmpty());
        
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewDAO.createSteward(steward1);
        stewDAO.createSteward(steward2);

        List<Steward> expected = Arrays.asList(steward1,steward2);
        List<Steward> actual = stewDAO.getAllStewards();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }
    
    /**
     * Test for getting all stewards flights.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test
    public void testGetAllStewardsFlights() throws JPAException {
        Airplane plane1 = newAirplane(700,"Jet3000","Passenger transport");
        Destination dest1 = newDestination("CZB","Czech Republic","Brno");
        Destination dest2 = newDestination("USN","United States","New York");
        em.getTransaction().begin();
        em.persist(plane1);
        em.persist(dest1);
        em.persist(dest2);
        em.getTransaction().commit();
        Steward steward1 = newSteward("Elaine","Dickinson");
        stewDAO.createSteward(steward1);

        assertTrue(stewDAO.getAllStewardsFlights(steward1).isEmpty());
        
        List<Flight> expected = Collections.EMPTY_LIST;
        List<Flight> actual = stewDAO.getAllStewardsFlights(steward1);
        assertEquals(expected, actual);
        
        List<Steward> stewList = new ArrayList<>();
        stewList.add(steward1);
        
        Flight flight1 = newFlight(new Timestamp(100000),new Timestamp(500000),dest1,dest2,plane1,stewList);
        em.getTransaction().begin();
        em.persist(flight1);
        em.getTransaction().commit();
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight1);
        
        assertEquals(stewDAO.getAllStewardsFlights(steward1).size(),flightList.size());
        assertFlightDeepEquals(stewDAO.getAllStewardsFlights(steward1),flightList);
    }
    
    /**
     * Test for steward creation.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test
    public void testCreateSteward() throws JPAException {
        Steward steward = newSteward("Elaine","Dickinson");
        stewDAO.createSteward(steward);
        
        assertNotNull(steward.getId());
        assertNotNull(steward.getFirstName());
        assertNotNull(steward.getLastName());
        
        Steward sameSteward = stewDAO.getSteward(steward.getId());
        
        assertEquals(steward, sameSteward);
        assertNotSame(steward, sameSteward);
        assertDeepEquals(steward, sameSteward);
    }
    
    /**
     * Attempt of creating null steward.
     */
    @Test
    public void testCreateNullSteward() {
        Steward steward = null;
        try {
            stewDAO.createSteward(steward);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    /**
     * Attempt of creating steward without last name.
     */
    @Test
    public void testCreateStewardWithoutLastName() {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        try {
            stewDAO.createSteward(steward);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    /**
     * Attempt of creating steward without first name.
     */
    @Test
    public void testCreateStewardWithoutFirstName() {
        Steward steward = new Steward();
        steward.setLastName("Dickinson");
        try {
            stewDAO.createSteward(steward);
        } catch (JPAException ex) {
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
            
        }
    }
    
    
    /**
     * Test for steward updating.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test
    public void testUpdateSteward() throws JPAException {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        steward.setLastName("Dickinson");
        stewDAO.createSteward(steward);
        
        Steward steward2 = stewDAO.getSteward(steward.getId());
        steward2.setFirstName("Jane");
        stewDAO.updateSteward(steward2);
        Steward steward3 = stewDAO.getSteward(steward.getId());
        
        assertEquals(steward2, steward3);
        assertNotSame(steward2, steward3);
    }
    
    
    /**
     * Test for removing steward.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test
    public void testRemoveSteward() throws JPAException{
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewDAO.createSteward(steward1);
        stewDAO.createSteward(steward2);

        assertNotNull(stewDAO.getSteward(steward1.getId()));
        assertNotNull(stewDAO.getSteward(steward2.getId()));

        stewDAO.removeSteward(steward1);
        assertNotNull(stewDAO.getSteward(steward2.getId()));
        
        try {
            Steward removedSteward = stewDAO.getSteward(steward1.getId());
        } catch (JPAException ex) {
            return;
        }
        fail();
    }
    
    /**
     * Constructor for Steward.
     */
    private static Steward newSteward(String firstName, String lastName) {
        Steward steward = new Steward();
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        return steward;
    }
    
    /**
     * Constructor for Flight.
     */
    private static Flight newFlight(Timestamp departureTime, 
            Timestamp arrivalTime, Destination origin, Destination target,
            Airplane airplane, List<Steward> stewardList) {
        Flight flight = new Flight();
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setOrigin(origin);
        flight.setTarget(target);
        flight.setAirplane(airplane);
        flight.setStewardList(stewardList);
        return flight;
    }
    
    /**
     * Constructor for Destination.
     */
    private static Destination newDestination(String code, String country, 
            String city) {
        Destination destination = new Destination();
        destination.setCode(code);
        destination.setCountry(country);
        destination.setCity(city);
        return destination;
    }
    
    /**
     * Constructor for Airplane.
     */
    private static Airplane newAirplane(int capacity, String name, String type) {
        Airplane plane = new Airplane();
        plane.setCapacity(capacity);
        plane.setName(name);
        plane.setType(type);
        return plane;
    }
    
    /**
     * Check for equality of all steward parameters.
     * @param stew1 steward number one to be compared
     * @param stew2 steward number two to be compared
     */
    private void assertDeepEquals(Steward stew1, Steward stew2) {
        assertEquals(stew1.getId(), stew2.getId());
        assertEquals(stew1.getFirstName(), stew2.getFirstName());
        assertEquals(stew1.getLastName(), stew2.getLastName());
    }
    
    /**
     * Check for equality of all flight parameters.
     * @param fli1 flight number one to be compared
     * @param fli2 flight number two to be compared
     */
    private void assertFlightDeepEquals(Flight fli1, Flight fli2) {
        assertEquals(fli1.getId(), fli2.getId());
        assertEquals(fli1.getAirplane(), fli2.getAirplane());
        assertEquals(fli1.getArrivalTime(), fli2.getArrivalTime());
        assertEquals(fli1.getDepartureTime(), fli2.getDepartureTime());
        assertEquals(fli1.getTarget(), fli2.getTarget());
        assertEquals(fli1.getOrigin(), fli2.getOrigin());
        assertDeepEquals(fli1.getStewardList(), fli2.getStewardList());
    }
    /**
     * Check for equality of two steward lists and all their parameters.
     * @param stewList1 steward list number one to be compared
     * @param stewList2 steward list number two to be compared
     */
    private void assertDeepEquals(List<Steward> stewList1, List<Steward> stewList2) {
        for (int i = 0; i < stewList1.size(); i++) {
            Steward expected = stewList1.get(i);
            Steward actual = stewList2.get(i);
            assertDeepEquals(expected, actual);
        }
    }
    
    /**
     * Check for equality of two flight lists and all their parameters.
     * @param flightList1 flight list number one to be compared
     * @param flightList2 flight list number two to be compared
     */
    private void assertFlightDeepEquals(List<Flight> flightList1, List<Flight> flightList2) {
        for (int i = 0; i < flightList1.size(); i++) {
            Flight expected = flightList1.get(i);
            Flight actual = flightList2.get(i);
            assertFlightDeepEquals(expected, actual);
        }
    }
    /**
     * Comparator by id.
     */
    private static Comparator<Steward> idComparator = new Comparator<Steward>() {
        @Override
        public int compare(Steward s1, Steward s2) {
            return s1.getId().compareTo(s2.getId());
        }
    };
}
