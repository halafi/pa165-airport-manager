package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractServiceTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.FlightServiceImpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Matus Makovy
 */
public class FlightServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private FlightServiceImpl flightService;
    
    @Mock
    private FlightDAO flightDao;
    @Mock
    private StewardDAO stewDao;
    @Mock
    private AirplaneDAO airplaneDao;
    @Mock
    private DestinationDAO destDao;
    
    private static Airplane plane1;
    private static Destination dest1;
    private static Destination dest2;
    private static Steward john;
    private static Steward paul;
    private static List<Steward> stewards;
    private static Timestamp timeEarlier;
    private static Timestamp timeLater;
    private Flight flightWithoutID;
    private FlightTO flightTOWithoutID;
    private Flight flightWithID;
    private FlightTO flightTOWithID;
    private Flight flightNotInDB;
    private FlightTO flightTONotInDB;
    private FlightTO obtained;
    private Long idNotInDB;
    private Long id;
    private List<FlightTO> flightTOs;
    
    @BeforeClass
    public static void setUp(){
        plane1 = createAirplane(100, "plane1", "type1");         

        dest1 = createDetiantion("code", "country", "city");
        dest2 = createDetiantion("code2", "country2", "city2");

        john = createSteward("john", "brown");
        paul = createSteward("paul", "smith");

        stewards = new ArrayList<>();
        stewards.add(john);
        stewards.add(paul);
        
        timeEarlier = new Timestamp(new GregorianCalendar(2013, 1, 1, 12, 00, 00).getTimeInMillis());
        timeLater = new Timestamp(new GregorianCalendar(2013, 1, 1, 15, 30, 00).getTimeInMillis());      
    }

    @Before
    public void init() throws JPAException {
        MockitoAnnotations.initMocks(this);  
        
        idNotInDB = new Long(3);
        id = new Long(12);

        flightWithoutID = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);
        flightTOWithoutID = EntityDTOTransformer.flightConvert(flightWithoutID);
        
        flightWithID = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);
        flightWithID.setId(id);
        flightTOWithID = EntityDTOTransformer.flightConvert(flightWithID);
        
        flightNotInDB = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);
        flightNotInDB.setId(new Long(2));
        flightTONotInDB = EntityDTOTransformer.flightConvert(flightNotInDB);

        doThrow(IllegalArgumentException.class).when(flightDao).createFlight(null);
        doThrow(IllegalArgumentException.class).when(flightDao).updateFlight(null);
        doThrow(IllegalArgumentException.class).when(flightDao).removeFlight(null);
        doThrow(IllegalArgumentException.class).when(flightDao).getFlight(null);
        
        doThrow(IllegalArgumentException.class).when(flightDao).updateFlight(flightWithoutID);
        doThrow(IllegalArgumentException.class).when(flightDao).removeFlight(flightWithoutID);
        
        doThrow(JPAException.class).when(flightDao).updateFlight(flightNotInDB);
        doThrow(JPAException.class).when(flightDao).removeFlight(flightNotInDB);
        doThrow(JPAException.class).when(flightDao).getFlight(idNotInDB);
        
        doReturn(flightWithID).when(flightDao).getFlight(id);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flightWithID);
        flights.add(flightNotInDB);
        
        flightTOs = new ArrayList<>();
        flightTOs.add(flightTOWithID);
        flightTOs.add(flightTONotInDB);
        
        doReturn(flights).when(flightDao).getAllFlight();      
        
    }

    @Test
    public void createFlightWithNull() {
        try {
            flightService.createFlight(null);
            fail("No exception thrown");
        } catch (DataAccessException ex) {
            //OK
        } catch (Exception ex) {
            fail("Wrong exception thrown");
        }
    }

    @Test
    public void createFlightTest() {
        try {
            flightService.createFlight(flightTOWithoutID);
        } catch (Exception ex) {
            fail("Exception thrown - no reason");
        }
        
        verify(flightDao).createFlight(flightWithoutID);
    }

    @Test
    public void updateFlightWithNull() {
        try {
            flightService.updateFlight(null);
            fail("No exception thrown");
        } catch (DataAccessException ex) {
            //OK
        } catch (Exception ex) {
            fail("Wrong exception thrown");
        }
    }
    
    @Test
    public void updateFlightTest() throws JPAException{
        try{
            flightService.updateFlight(flightTOWithID);
        } catch(Exception ex){
            fail("Exception thrown - no reason");
        }
        
        verify(flightDao).updateFlight(flightWithID);
    }
    
    @Test
    public void updateFlightWithNullID() throws JPAException{
        try{
            flightService.updateFlight(flightTOWithoutID);
            fail("No exception thrown");
        } catch (DataAccessException ex){
            //OK
        } catch (Exception ex) {
            fail("Wrong exception thrown");
        }
        
        verify(flightDao).updateFlight(flightWithoutID);
    }
    
    @Test
    public void updateFlightNotinDB() throws JPAException{
        try{
            flightService.updateFlight(flightTONotInDB);
            fail("No exception thrown");
        } catch(DataAccessException ex){
            //OK
        } catch(Exception ex){
            fail("Wrong exception thrown");
        }
        
        verify(flightDao).updateFlight(flightNotInDB);
    }

    @Test
    public void removeFlightWithNull() {
        try {
            flightService.removeFlight(null);
            fail("No exception thrown");
        } catch (DataAccessException ex) {
            //OK
        } catch (Exception ex) {
            fail("Wrong exception thrown");
        }
    }
    
    @Test
    public void removeFlightTest() throws JPAException{
        try{
            flightService.removeFlight(flightTOWithID);
        } catch(Exception ex){
            fail("Exception thrown - no reason");
        }
        
        verify(flightDao).removeFlight(flightWithID);
    }
    
    @Test
    public void removeFlightWithNullID() throws JPAException {
        try{
            flightService.removeFlight(flightTOWithoutID);
            fail("No exception thrown");
        } catch(DataAccessException ex){
            //OK
        } catch(Exception ex){
            fail("Wrong exception thrown");
        }
        
        verify(flightDao).removeFlight(flightWithoutID);
    }
    
    @Test
    public void removeFlightNotInDB() throws JPAException{
        try{
            flightService.removeFlight(flightTONotInDB);
        } catch(DataAccessException ex){
            //OK
        } catch(Exception ex){
            fail("Wrong exception thrown");
        }
        
        verify(flightDao).removeFlight(flightNotInDB);
    }

    @Test
    public void getFlightWithNull() {
        try {
            flightService.getFlight(null);
            fail("No exception thrown");
        } catch (DataAccessException ex) {
            //OK
        } catch (Exception ex) {
            fail("Wrong exception thrown");
        }
    }
    
    @Test
    public void getFlightIdNotInDB() throws JPAException{
        try{
            flightService.getFlight(idNotInDB);
            fail("No exception thrown");
        } catch(DataAccessException ex){
            //OK
        } catch(Exception ex){
            fail("No exception thrown");
        }
        
        verify(flightDao).getFlight(idNotInDB);
    }
    
    @Test
    public void getFlightTest() throws JPAException{
        
        try{
            obtained = flightService.getFlight(flightTOWithID.getId());
        } catch(Exception ex){
            fail("Exception thrown - no reason");
        }
        
        assertEquals(flightTOWithID,obtained);
        
        verify(flightDao).getFlight(id);
        
    }
    
    @Test
    public void getAllFlightsTest() throws JPAException{
        
        List<FlightTO> obtainedList = null;
        
        try{ 
            obtainedList = flightService.getAllFlights();
            if(obtainedList == null){
                fail("No flights returned");
            }
        } catch(Exception ex){
            fail("Exception thrown - no reason");
        }
        
        assertEquals(flightTOs,obtainedList);
        
        verify(flightDao).getAllFlight();
    }

    /**
     * Constructor
     */
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