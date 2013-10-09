package cz.muni.fi.pa165.airportmanager.backend;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.StewardDAOImpl;
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
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for StewardDAOImpl.
 *
 * @author Filip
 */
public class StewardDAOImplTest extends TestCase {
    private StewardDAO stewardDAO;
    
    @Before
    public void setUp() {
        stewardDAO = new StewardDAOImpl();
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
     * Test for get steward.
     */
    @Test
    public void getSteward(){
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewardDAO.createSteward(steward1);
        stewardDAO.createSteward(steward2);
        
        assertDeepEquals(stewardDAO.getSteward(steward1.getId()),steward1);
        assertDeepEquals(stewardDAO.getSteward(steward2.getId()),steward2);
    }
    
    /**
     * Test for getting steward with null id.
     */
    @Test (expected = IllegalArgumentException.class)
    public void getStewardWithNullId(){
        stewardDAO.getSteward(null);
    }
    
    /**
     * Test for getting all stewards.
     */
    @Test
    public void getAllStewards() {
        assertTrue(stewardDAO.getAllStewards().isEmpty());
        
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewardDAO.createSteward(steward1);
        stewardDAO.createSteward(steward2);

        List<Steward> expected = Arrays.asList(steward1,steward2);
        List<Steward> actual = stewardDAO.getAllStewards();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }
    
    /**
     * Test for getting all stewards flights.
     */
    @Test
    public void getAllStewardsFlights() {
        Airplane plane1 = newAirplane(700,"Jet3000","Passenger transport");
        Destination dest1 = newDestination("CZB","Czech Republic","Brno");
        Destination dest2 = newDestination("USN","United States","New York");
        Steward steward1 = newSteward("Elaine","Dickinson");
        stewardDAO.createSteward(steward1);
        
        assertTrue(stewardDAO.getAllStewardsFlights(steward1).isEmpty());
        
        List<Flight> expected = Collections.EMPTY_LIST;
        List<Flight> actual = stewardDAO.getAllStewardsFlights(steward1);
        assertEquals(expected, actual);
        
        List<Steward> stewList = new ArrayList<Steward>();
        stewList.add(steward1);
        
        Flight flight1 = newFlight(new Timestamp(100000),new Timestamp(500000),dest1,dest2,plane1,stewList);
        List<Flight> flightList = new ArrayList<Flight>();
        flightList.add(flight1);
        
        assertEquals(stewardDAO.getAllStewardsFlights(steward1),flightList);
    }
    
    /**
     * Test for steward creation.
     */
    @Test
    public void createSteward() {
        Steward steward = newSteward("Elaine","Dickinson");
        stewardDAO.createSteward(steward);
        
        assertNotNull(steward.getId());
        assertNotNull(steward.getFirstName());
        assertNotNull(steward.getLastName());
        
        Steward sameSteward = stewardDAO.getSteward(steward.getId());
        
        assertEquals(steward, sameSteward);
        assertNotSame(steward, sameSteward);
        assertDeepEquals(steward, sameSteward);
    }
    
    /**
     * Attempt of creating null steward.
     */
    @Test (expected = IllegalArgumentException.class)
    public void createNullSteward() {
        Steward steward = null;
        stewardDAO.createSteward(steward);
    }
    
    /**
     * Attempt of creating steward without last name.
     */
    @Test (expected = IllegalArgumentException.class)
    public void createStewardWithoutLastName() {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        stewardDAO.createSteward(steward);
    }
    
    /**
     * Attempt of creating steward without first name.
     */
    @Test (expected = IllegalArgumentException.class)
    public void createStewardWithoutFirstName() {
        Steward steward = new Steward();
        steward.setLastName("Dickinson");
        stewardDAO.createSteward(steward);
    }
    
    @Test
    /**
     * Test for steward updating.
     */
    public void updateSteward() {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        steward.setLastName("Dickinson");
        stewardDAO.createSteward(steward);
        
        Steward steward2 = stewardDAO.getSteward(steward.getId());
        steward2.setFirstName("Jane");
        stewardDAO.updateSteward(steward2);
        Steward steward3 = stewardDAO.getSteward(steward.getId());
        
        assertEquals(steward2, steward3);
        assertNotSame(steward2, steward3);
    }
    
    @Test
    /**
     * Test for removing steward.
     */
    public void removeSteward(){
        Steward steward1 = newSteward("Elaine","Dickinson");
        Steward steward2 = newSteward("Joshua","Bloch");
        stewardDAO.createSteward(steward1);
        stewardDAO.createSteward(steward2);

        assertNotNull(stewardDAO.getSteward(steward1.getId()));
        assertNotNull(stewardDAO.getSteward(steward2.getId()));

        stewardDAO.removeSteward(steward1);
        
        assertNull(stewardDAO.getSteward(steward1.getId()));
        assertNotNull(stewardDAO.getSteward(steward2.getId()));
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
     * Comparator by id.
     */
    private static Comparator<Steward> idComparator = new Comparator<Steward>() {
        @Override
        public int compare(Steward s1, Steward s2) {
            return s1.getId().compareTo(s2.getId());
        }
    };
}
