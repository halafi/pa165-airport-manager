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
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Matus Makovy
 */
public class FlightDAOImplTest {

    private FlightDAO flightDao;
    private EntityManagerFactory emf;
    private Airplane plane1;
    private Airplane plane2;
    private Destination dest1;
    private Destination dest2;
    private Steward john;
    private Steward paul;
    private List<Steward> stewards;
    Timestamp time1;
    Timestamp time2;

    @BeforeClass
    public void setUp() {
        flightDao = new FlightDAOImpl();
        this.emf = Persistence.createEntityManagerFactory("AirportManager");

        plane1 = createAirplane(100, "plane1", "type1");
        plane2 = createAirplane(200, "plane2", "type2");

        dest1 = createDetiantion("code", "country", "city");
        dest2 = createDetiantion("code2", "country2", "city2");

        john = createSteward("john", "brown");
        paul = createSteward("paul", "smith");

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

        stewards = new ArrayList<Steward>();
        stewards.add(john);
        stewards.add(paul);

        time1 = new Timestamp(new GregorianCalendar(2013, 1, 1, 12, 00, 00).getTimeInMillis());
        time2 = new Timestamp(new GregorianCalendar(2013, 1, 1, 15, 30, 00).getTimeInMillis());

    }

    //createFlight method
    @Test(expected = IllegalArgumentException.class)
    public void createFlightWithNull() {
        flightDao.createFlight(null);
    }

    @Test
    public void createFlightTest() {

        Flight flight1 = createFlight(plane1, dest1, dest2, stewards, time1, time2);

        flightDao.createFlight(flight1);

        assertNotNull(flight1.getId());

        EntityManager em = emf.createEntityManager();

        Flight foundFlight = em.find(Flight.class, flight1.getId());

        assertEquals(flight1, foundFlight);

        em.close();
    }

    @Test
    public void createFlightWithNullParameters() {

        Flight flight = createFlight(null, dest1, dest2, stewards, time1, time2);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with null airplane");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, null, dest2, stewards, time1, time2);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with origin destination null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, null, stewards, time1, time2);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with target destination null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, dest2, null, time1, time2);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with steward list null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, dest2, stewards, null, time2);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with deperature time null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
        }

        flight = createFlight(plane1, dest1, dest2, stewards, time1, null);

        try {
            flightDao.createFlight(flight);
            fail("No exception thrown! - Flight with arrival time null");
        } catch (IllegalArgumentException ex) {
            //OK
        } catch (Exception ex) {
            fail("Expected IllegalArgumentException instead of " + ex.toString());
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
        flightDao.removeFlight(flightNullId);
    }

    @Test
    public void removeFlightTest() {
        EntityManager em = emf.createEntityManager();

        Flight flight = createFlight(plane2, dest2, dest1, stewards, time1, time2);

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
        flightDao.updateFlight(flightNullId);
    }

    @Test
    public void updateFlightTest() {

        EntityManager em = emf.createEntityManager();

        Flight flight = createFlight(plane1, dest2, dest1, stewards, time1, time2);

        em.getTransaction().begin();
        em.persist(flight);
        em.getTransaction().commit();
        em.detach(flight);

        Airplane plane3 = createAirplane(10, "new", "new");
        flight.setAirplane(plane3);
        
        try {
            flightDao.updateFlight(flight);
        } catch (JPAException ex) {
            fail("Update flight - JPA Exception" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            fail("Update flight - IllegalArgumentException" + ex.getMessage());
        }
        
        Flight flightFromDB = em.find(Flight.class,flight.getId());
        
        assertEquals("updateFlight() did not update flight (airplane)",flightFromDB.getAirplane(),plane3);

    }

    //getFlight method
    @Test(expected = IllegalArgumentException.class)
    public void getFlightWithNull() throws JPAException {
        flightDao.getFlight(null);
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
