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
    private Airplane plane1;
    private Airplane plane2;
    private Destination dest1;
    private Destination dest2;
    private Steward john;
    private Steward paul;
    private List<Steward> stewards;
    private Timestamp timeEarlier;
    private Timestamp timeLater;
    private Flight flight1;
    private FlightTO flight1TO;

    @Before
    public void setUp() throws JPAException {
        MockitoAnnotations.initMocks(this);

        plane1 = createAirplane(100, "plane1", "type1");
        plane2 = createAirplane(200, "plane2", "type2");

        dest1 = createDetiantion("code", "country", "city");
        dest2 = createDetiantion("code2", "country2", "city2");

        john = createSteward("john", "brown");
        paul = createSteward("paul", "smith");

        stewards = new ArrayList<>();
        stewards.add(john);
        stewards.add(paul);

        timeEarlier = new Timestamp(new GregorianCalendar(2013, 1, 1, 12, 00, 00).getTimeInMillis());
        timeLater = new Timestamp(new GregorianCalendar(2013, 1, 1, 15, 30, 00).getTimeInMillis());

        flight1 = createFlight(plane1, dest1, dest2, stewards, timeEarlier, timeLater);
        flight1TO = EntityDTOTransformer.flightConvert(flight1);

        doThrow(IllegalArgumentException.class).when(flightDao).createFlight(null);
        doThrow(IllegalArgumentException.class).when(flightDao).updateFlight(null);
        doThrow(IllegalArgumentException.class).when(flightDao).removeFlight(null);
        doThrow(IllegalArgumentException.class).when(flightDao).getFlight(null);

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
            flightService.createFlight(flight1TO);
        } catch (Exception ex) {
            fail("Exception thrown - no reason");
        }
        
        verify(flightDao).createFlight(flight1);
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

    /**
     * Constructor
     */
    private Destination createDetiantion(String code, String country, String city) {
        Destination des = new Destination();
        des.setCode(code);
        des.setCity(city);
        des.setCountry(country);
        return des;
    }

    private Airplane createAirplane(int capacity, String name, String type) {
        Airplane airplane = new Airplane();
        airplane.setCapacity(capacity);
        airplane.setName(name);
        airplane.setType(type);
        return airplane;
    }

    private Steward createSteward(String first, String last) {
        Steward steward = new Steward();
        steward.setFirstName(first);
        steward.setLastName(last);
        return steward;
    }

    private Flight createFlight(Airplane airplane, Destination origin, Destination target,
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