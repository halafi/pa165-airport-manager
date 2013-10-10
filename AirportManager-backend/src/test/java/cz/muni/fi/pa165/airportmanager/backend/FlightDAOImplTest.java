package cz.muni.fi.pa165.airportmanager.backend;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.FlightDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Matus Makovy
 */
public class FlightDAOImplTest {

    private static FlightDAO flightDao;
    private static EntityManagerFactory emf;
    private static Airplane plane1;
    private static Airplane plane2;
    private static Destination dest1;
    private static Destination dest2;
    private static Steward john;
    private static Steward paul;
    private static List<Steward> stewards;
    private static Timestamp timeEarlier;
    private static Timestamp timeLater;

    @BeforeClass
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("AirportManager");
        flightDao = new FlightDAOImpl(emf);
        plane1 = createAirplane(100, "plane1", "type1");
        plane2 = createAirplane(200, "plane2", "type2");

        dest1 = createDetiantion("code", "country", "city");
        dest2 = createDetiantion("code2", "country2", "city2");

        john = createSteward("john", "brown");
        paul = createSteward("paul", "smith");

        stewards = new ArrayList<Steward>();
        stewards.add(john);
        stewards.add(paul);

        timeEarlier = new Timestamp(new GregorianCalendar(2013, 1, 1, 12, 00, 00).getTimeInMillis());
        timeLater = new Timestamp(new GregorianCalendar(2013, 1, 1, 15, 30, 00).getTimeInMillis());
        
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(plane1);
        em.persist(dest1);
        em.persist(john);
        em.getTransaction().commit();

        em.clear();

        em.getTransaction().begin();
        em.persist(plane2);
        em.persist(dest2);
        em.persist(paul);
        em.getTransaction().commit();

        em.close();

    }

   /* @Before
    public void fillTables() {
        
        
    }*/

    //createFlight method

    @Test(expected = IllegalArgumentException.class)
    public void createFlightWithNull() throws JPAException {
        flightDao.createFlight(null);
    }

    @Test
    public void createFlightTest() {

        Flight flight1 = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);

        flightDao.createFlight(flight1);

        assertNotNull(flight1.getId());

        EntityManager em = emf.createEntityManager();

        Flight foundFlight = em.find(Flight.class, flight1.getId());

        assertEquals(flight1, foundFlight);

        em.close();
    }

    @Test
    public void createFlightSwapedTimes() {
        Flight flight = createFlight(plane1, dest1, dest1, stewards, timeLater, timeEarlier);

        try {
            flightDao.createFlight(flight);
            fail("Successfully created flight with earlier time of arrival then time of deperature");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("IllegalArgumentException expected " + ex.getMessage());
        }
    }

    @Test
    public void createFlightWithNullParameters() {

        Flight flight = createFlight(null, dest1, dest2, stewards, timeEarlier, timeLater);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with null airplane");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, null, dest2, stewards, timeEarlier, timeLater);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with origin destination null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, null, stewards, timeEarlier, timeLater);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with target destination null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, dest2, null, timeEarlier, timeLater);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with steward list null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, dest2, stewards, null, timeLater);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with deperature time null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, dest2, stewards, timeEarlier, null);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with arrival time null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

    }

    @Test
    public void createFlightWithEmptyStewardList() {
        List<Steward> stewardsEmpty = new ArrayList<Steward>();
        Flight flight = createFlight(plane1, dest1, dest1, stewardsEmpty, timeEarlier, timeEarlier);

        try {
            flightDao.createFlight(flight);
            fail("Succesfully created flight with empty steward List.");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Excepected IllegalArgumentException" + ex.getMessage());
        }
    }

    //removeFlight method
    @Test(expected = IllegalArgumentException.class)
    public void removeFlightWithNull() throws JPAException {
        flightDao.removeFlight(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeFlightWithNullId() throws JPAException {
        Flight flightNullId = createFlight(null, null, null, null, null, null);
        flightNullId.setId(null);
        flightDao.removeFlight(flightNullId);
    }

    @Test
    public void removeFlightTest() {
        EntityManager em = emf.createEntityManager();

        Flight flight = createFlight(plane2, dest2, dest1, stewards, timeEarlier, timeLater);

        em.getTransaction().begin();
        em.persist(flight);
        em.getTransaction().commit();

        try {
            flightDao.removeFlight(flight);
        } catch (JPAException ex) {
            fail("Remove flight - JPA Exception" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            fail("Remove flight - IllegalArgumentException" + ex.getMessage());
        }

        em.clear();
        Flight flightFromDB = em.find(Flight.class, flight.getId());

        assertNull("removeFlight() did not remove the flight", flightFromDB);
    }

    //updateFlight method
    @Test(expected = IllegalArgumentException.class)
    public void updateFlightWithNull() throws JPAException {
        flightDao.updateFlight(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateFlightWithNullId() throws JPAException {
        Flight flightNullId = createFlight(null, null, null, null, null, null);
        flightNullId.setId(null);
        flightDao.updateFlight(flightNullId);
    }

    public void updateFlightWithNullParameters() {

        EntityManager em = emf.createEntityManager();
        Flight flight = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);

        em.getTransaction().begin();
        em.persist(flight);
        em.getTransaction().commit();
        em.close();

        flight.setAirplane(null);

        try {
            flightDao.updateFlight(flight);
            fail("No exception thrown! - Flight with null airplane");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight.setAirplane(plane1);
        flight.setOrigin(null);

        try {
            flightDao.updateFlight(flight);
            fail("No exception thrown! - Flight with origin destination null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight.setOrigin(dest1);
        flight.setTarget(null);

        try {
            flightDao.updateFlight(flight);
            fail("No exception thrown! - Flight with target destination null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight.setTarget(dest2);
        flight.setStewardList(null);

        try {
            flightDao.updateFlight(flight);
            fail("No exception thrown! - Flight with steward list null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight.setStewardList(stewards);
        flight.setDepartureTime(null);

        try {
            flightDao.updateFlight(flight);
            fail("No exception thrown! - Flight with deperature time null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight.setDepartureTime(timeEarlier);
        flight.setArrivalTime(null);

        try {
            flightDao.updateFlight(flight);
            fail("No exception thrown! - Flight with arrival time null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

    }

    @Test
    public void updateFlightWithEmptyStewardList() {
        List<Steward> stewardsEmpty = new ArrayList<Steward>();
        EntityManager em = emf.createEntityManager();
        Flight flight = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);

        em.getTransaction().begin();
        em.persist(flight);
        em.getTransaction().commit();
        em.close();

        flight.setStewardList(stewardsEmpty);

        try {
            flightDao.updateFlight(flight);
            fail("Succesfully updated flight with empty steward List.");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Excepected IllegalArgumentException" + ex.getMessage());
        }
    }

    @Test
    public void updateFlightSwapedTimes() {
        Flight flight = createFlight(plane1, dest1, dest1, stewards, timeEarlier, timeLater);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(flight);
        em.getTransaction().commit();
        em.close();

        flight.setArrivalTime(timeEarlier);
        flight.setDepartureTime(timeLater);

        try {
            flightDao.updateFlight(flight);
            fail("Successfully update flight with earlier time of arrival then time of deperature");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("IllegalArgumentException expected " + ex.getMessage());
        }
    }

    @Test
    public void updateFlightTest() {

        EntityManager em = emf.createEntityManager();

        Flight flight = createFlight(plane1, dest2, dest1, stewards, timeEarlier, timeLater);
        Airplane plane3 = createAirplane(10, "new", "new");

        em.getTransaction().begin();
        em.persist(flight);
        em.persist(plane3);
        em.getTransaction().commit();
        em.detach(flight);
        em.detach(plane3);

        flight.setAirplane(plane3);

        try {
            flightDao.updateFlight(flight);
        } catch (JPAException ex) {
            fail("Update flight - JPA Exception" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            fail("Update flight - IllegalArgumentException" + ex.getMessage());
        }

        Flight flightFromDB = em.find(Flight.class, flight.getId());

        assertEquals("updateFlight() did not update flight (airplane)", plane3, flightFromDB.getAirplane());
        em.close();
    }

    //getFlight method
    @Test(expected = IllegalArgumentException.class)
    public void getFlightWithNull() throws JPAException {
        flightDao.getFlight(null);
    }

    @Test
    public void getFlightTest() throws JPAException {

        Steward steward = createSteward("ste", "ward");
        stewards.add(steward);
        Flight flight = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(steward);
        em.persist(flight);
        em.getTransaction().commit();

        Flight flightFromDB = flightDao.getFlight(flight.getId());

        assertEquals("getFlight() returned flight diffrenet from inserted flight", flight, flightFromDB);

        em.close();

    }

    //getAllFlights method
    /**
     * COMMING SOON *
     */
    //Constructors
    private static Destination createDetiantion(String code, String country, String city) {
        Destination des = new Destination();
        des.setCode(code);
        des.setCity(city);
        des.setCountry(country);
        return des;
    }

    private static Airplane createAirplane(int capacity, String name, String type) {
        Airplane airplane = new Airplane();
        airplane.setCapacity(capacity);
        airplane.setName(name);
        airplane.setType(type);
        return airplane;
    }

    private static Steward createSteward(String first, String last) {
        Steward steward = new Steward();
        steward.setFirstName(first);
        steward.setLastName(last);
        return steward;
    }

    private static Flight createFlight(Airplane airplane, Destination origin, Destination target,
            List<Steward> stewardList, Timestamp deperature, Timestamp arrival) {
        Flight flight = new Flight();
        flight.setAirplane(airplane);
        flight.setOrigin(origin);
        flight.setTarget(target);
        flight.setStewardList(stewardList);
        flight.setDepartureTime(deperature);
        flight.setArrivalTime(arrival);
        return flight;
    }
}
