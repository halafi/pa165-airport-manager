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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
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
    private Flight flight1;
    private Flight flight2;
    
    @Before
    public void setUp() {
        flightDao = new FlightDAOImpl();
        this.emf = Persistence.createEntityManagerFactory("AirportManager");
        
        plane1 = new Airplane();
        plane1.setCapacity(100);
        plane1.setName("Airplane1");
        plane1.setType("best");
        
        plane2 = new Airplane();
        plane2.setCapacity(200);
        plane2.setName("Airplane2");
        plane2.setType("worst");
        
        dest1 = new Destination();
        dest1.setCity("City");
        dest1.setCode("CT");
        dest1.setCountry("Country");
        
        dest2 = new Destination();
        dest2.setCity("City2");
        dest2.setCode("CT2");
        dest2.setCountry("Country2");
        
        john = new Steward();
        john.setFirstName("John");
        john.setLastName("Brown");
  
        paul = new Steward();
        paul.setFirstName("Paul");
        paul.setLastName("Smith");
        
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
        
        flight1 = new Flight();
        flight1.setAirplane(plane1);
        flight1.setOrigin(dest1);
        flight1.setTarget(dest2);
        flight1.setStewardList(stewards);
        flight1.setDepartureTime(new Timestamp(new GregorianCalendar(2013,1,1,12,00).getTimeInMillis()));
        flight1.setArrivalTime(new Timestamp(new GregorianCalendar(2013,1,1,15,30).getTimeInMillis()));
        
        flight2 = new Flight();
        flight2.setAirplane(plane2);
        flight2.setOrigin(dest2);
        flight2.setTarget(dest1);
        flight2.setStewardList(stewards);
        flight2.setDepartureTime(new Timestamp(new GregorianCalendar(2012,1,5,11,00).getTimeInMillis()));
        flight2.setArrivalTime(new Timestamp(new GregorianCalendar(2012,1,5,14,20).getTimeInMillis()));
        
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createFlightWithNull() throws JPAException{
        flightDao.createFlight(null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void removeFlightWithNull() throws JPAException{
            flightDao.removeFlight(null);
    }  
    
    @Test (expected = IllegalArgumentException.class)
    public void updateFlightWithNull() throws JPAException {
        flightDao.updateFlight(null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getFlightWithNull() throws JPAException{
        flightDao.getFlight(null);
    }
    
    
    @Test
    public void createFlightTest() {
        
        flightDao.createFlight(flight1);
        
        assertNotNull(flight1.getId());
        
        EntityManager em = emf.createEntityManager();
        
        Flight foundFlight = em.find(Flight.class, flight1.getId());
        
        assertEquals(flight1,foundFlight);
        
        em.close();
    }
}
