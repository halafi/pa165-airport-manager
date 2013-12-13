
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.AirportDaoException;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 *
 * @author Juraj Duráni
 */
public class DestinationServiceImplTest extends AbstractTest{
    
    private static int counter = 0;
    
    @InjectMocks
    private DestinationServiceImpl destService;
    
    @Mock
    private DestinationDAO destDao;
    
    private DestinationTO destInDB1;
    private DestinationTO destInDB2;
    private DestinationTO destWithNullID;
    private DestinationTO destWithNullAtributes;
    private DestinationTO destWithNullAtributesAndID;
    private DestinationTO destWithEmptyAtributes;
    private DestinationTO destWithEmptyAtributesAndNullID;
    private DestinationTO destRemovedFromDB;
    
    private FlightTO flight1;
    private FlightTO flight2;
    private FlightTO flight3;
    private FlightTO flight4;
    
    @Before
    public void setUp() throws AirportDaoException{
        MockitoAnnotations.initMocks(this);
        System.out.println(destService);
        
        destInDB1 = getDestination();
        destInDB1.setId(1L);
        
        destInDB2 = getDestination();
        destInDB2.setId(2L);
        
        destWithNullID = getDestination();
        
        destWithNullAtributes = new DestinationTO();
        destWithNullAtributes.setId(1L);
        
        destWithNullAtributesAndID = new DestinationTO();
        
        destWithEmptyAtributes = new DestinationTO();
        destWithEmptyAtributes.setId(1L);
        destWithEmptyAtributes.setCity("");
        destWithEmptyAtributes.setCode("");
        destWithEmptyAtributes.setCountry("");
        
        destWithEmptyAtributesAndNullID = new DestinationTO();
        destWithEmptyAtributesAndNullID.setCity("");
        destWithEmptyAtributesAndNullID.setCode("");
        destWithEmptyAtributesAndNullID.setCountry("");
        
        destRemovedFromDB = getDestination();
        destRemovedFromDB.setId(5L);
        
        flight1 = createFlight(destInDB1, destInDB2);
        flight2 = createFlight(destInDB1, destInDB1);
        flight3 = createFlight(destInDB2, destInDB2);
        flight4 = createFlight(destInDB2, destInDB1);
        
        setUpMock();
    }
    
    private void setUpMock() throws AirportDaoException{
        /* doThrow */
        /* create */
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).createDestination(null);
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributesAndID));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributesAndNullID));
        //má nastavené ID
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* update */
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).updateDestination(null);
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributes));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributes));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* remove */
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).removeDestination(null);
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* get */
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getDestination(null);
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getDestination(
                destRemovedFromDB.getId());
        /* getAllIncoming */
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getAllIncomingFlights(null);
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* getAllOutcoming */
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getAllOutcomingFlights(null);
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getAllOutcomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(InvalidDataAccessResourceUsageException.class).when(destDao).getAllOutcomingFlights(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* end doThrow */
        /* doReturn */
        /* get */
        when(destDao.getDestination(destInDB1.getId()))
                .thenReturn(EntityDTOTransformer.destinationTOConvert(destInDB1));
        when(destDao.getDestination(destInDB2.getId()))
                .thenReturn(EntityDTOTransformer.destinationTOConvert(destInDB2));
        /* getAll */
        when(destDao.getAllDestinations()).thenReturn(Arrays.asList(
                EntityDTOTransformer.destinationTOConvert(destInDB1),
                EntityDTOTransformer.destinationTOConvert(destInDB2)));
        /* getAllIncoming */
        when(destDao.getAllIncomingFlights(EntityDTOTransformer.destinationTOConvert(destInDB1)))
                .thenReturn(Arrays.asList(
                    EntityDTOTransformer.flightTOConvert(flight2),
                    EntityDTOTransformer.flightTOConvert(flight4)));
        when(destDao.getAllIncomingFlights(EntityDTOTransformer.destinationTOConvert(destInDB2)))
                .thenReturn(Arrays.asList(
                    EntityDTOTransformer.flightTOConvert(flight1),
                    EntityDTOTransformer.flightTOConvert(flight3)));
        /* getAllOutcoming */
        when(destDao.getAllOutcomingFlights(EntityDTOTransformer.destinationTOConvert(destInDB1)))
                .thenReturn(Arrays.asList(
                    EntityDTOTransformer.flightTOConvert(flight1),
                    EntityDTOTransformer.flightTOConvert(flight2)));
        when(destDao.getAllOutcomingFlights(EntityDTOTransformer.destinationTOConvert(destInDB2)))
                .thenReturn(Arrays.asList(
                    EntityDTOTransformer.flightTOConvert(flight3),
                    EntityDTOTransformer.flightTOConvert(flight4)));
        /* end doReturn */
    }
    
    /**
     * getDestination(...) too
     */
    @Test
    public void createTest(){
        try{
            destService.createDestination(null);
            fail("Create test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Create test - null argument - bad exception " + ex);
        }
        try{
            destService.createDestination(destRemovedFromDB);
            fail("Create test - argument with nonnull ID");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Create test - argument with nonnull ID - bad exception " + ex);
        }
        try{
            destService.createDestination(destWithNullAtributesAndID);
            fail("Create test - null atributes");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Create test - null atributes - bad exception " + ex);
        }
        try{
            destService.createDestination(destWithEmptyAtributesAndNullID);
            fail("Create test - emptu atributes");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Create test - empty atributes - bad exception " + ex);
        }
        try{
            doNothing().when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
            destService.createDestination(destWithNullID);
            Destination des = new Destination();
            des.setCity(destWithNullID.getCity());
            des.setCode(destWithNullID.getCode());
            des.setCountry(destWithNullID.getCountry());
            des.setId(-1L);
            when(destDao.getDestination(-1L)).thenReturn(des);
            DestinationTO desTO = destService.getDestination(-1L);
            assertDeepEquals(desTO, EntityDTOTransformer.destinationConvert(des));
            // init behavior
            when(destDao.getDestination(-1L)).thenReturn(null);
        } catch (DataAccessException ex){
            fail("Create test - OK argument - exception thrown " + ex);
        } catch (Exception ex){
            fail("Create test - OK argument - bad exception " + ex);
        }
    }
    
    /**
     * getDestination(...) too
     */
    @Test
    public void removeTest(){
        try{
            destService.removeDestination(null);
            fail("Remove test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - null argument - bad exception " + ex);
        }
        
        try{
            destService.removeDestination(destWithNullID);
            fail("Remove test - null ID");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - null ID - bad exception " + ex);
        }
        
        try{
            destService.removeDestination(destRemovedFromDB);
            fail("Remove test - argument not in DB");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - argument not in DB - bad exception " + ex);
        }
        
        try{
            destService.removeDestination(destInDB1);
            when(destDao.getDestination(destInDB1.getId()))
                    .thenThrow(InvalidDataAccessResourceUsageException.class);
        } catch (DataAccessException ex){
            fail("Remove test - OK argument - exception thrown " + ex);
        } catch (Exception ex){
            fail("Remove test - OK argument - bad exception " + ex);
        }
        
        try{
            DestinationTO d = destService.getDestination(destInDB1.getId());
            if(d != null){
                fail("Remove test - not removed");
            }
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - removed - bad exception " + ex);
        }

        try{
            when(destDao.getDestination(destInDB1.getId()))
                .thenReturn(EntityDTOTransformer.destinationTOConvert(destInDB1));
        } catch (Exception ex){}
        System.out.println("navratenie");
    }
    
    /** 
     * getDestination(...) too
     */
    @Test
    public void updateTest(){
        try{
            destService.updateDestination(null);
            fail("Update test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Update test - null argument - bad exception " + ex);
        }
        try{
            destService.updateDestination(destWithNullID);
            fail("Update test - null ID");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Update test - null ID - bad exception " + ex);
        }
        try{
            destService.updateDestination(destWithEmptyAtributes);
            fail("Update test - empty atributes");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Update test - empty atributes - bad exception " + ex);
        }
        try{
            destService.updateDestination(destWithNullAtributes);
            fail("Update test - null atributes");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Update test - null atributes - bad exception " + ex);
        }
        try{
            DestinationTO des = getDestination();
            des.setId(destInDB1.getId());
            doNothing().when(destDao)
                    .updateDestination(EntityDTOTransformer.destinationTOConvert(des));
            destService.updateDestination(des);
            when(destDao.getDestination(destInDB1.getId()))
                    .thenReturn(EntityDTOTransformer.destinationTOConvert(des));
        } catch (DataAccessException ex){
            fail("Update test - OK argument - exception thrown " + ex);
        } catch (Exception ex){
            fail("Update test - null argument - bad exception " + ex);
        }
        try{
            DestinationTO result = destService.getDestination(destInDB1.getId());
            DestinationTO demand = EntityDTOTransformer.destinationConvert(
                    destDao.getDestination(destInDB1.getId()));
            assertDeepEquals(result, demand);
            // init behavior
            when(destDao.getDestination(destInDB1.getId()))
                    .thenReturn(EntityDTOTransformer.destinationTOConvert(destInDB1));
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Update test - null argument - bad exception " + ex);
        }
    }
    
    @Test
    public void getTest(){
        try{
            destService.getDestination(null);
            fail("Get test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get test - null argument - bad exception " + ex);
        }
        try{
            destService.getDestination(destRemovedFromDB.getId());
            fail("Get test - destination not in DB");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get test - destination not in DB - bad exception " + ex);
        }
        try{
            DestinationTO result = destService.getDestination(destInDB1.getId());
            assertDeepEquals(result, destInDB1);
            result = destService.getDestination(destInDB2.getId());
            assertDeepEquals(result, destInDB2);
        } catch (DataAccessException ex){
            fail("Get test - OK argument - exception thrown " + ex);
        } catch (Exception ex){
            fail("Get test - null argument - bad exception " + ex);
        }
    }
    
    @Test
    public void getAllTest(){
        try{
            destService.getDestination(null);
            fail("Get test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get test - null argument - bad exception " + ex);
        }
        try{
            List<DestinationTO> result = destService.getAllDestinations();
            List<DestinationTO> demand = Arrays.asList(destInDB1, destInDB2);
            assertDeepEqualsListDest(demand, result);
        } catch (DataAccessException ex){
            fail("Get all test - exception thrown " + ex);
        } catch (Exception ex){
            fail("Get all test - bad exception " + ex);
        }
    }
    
    @Test
    public void getAllIncomingFlightsTest(){
        try{
            destService.getAllIncomingFlights(null);
            fail("Get all incoming test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get all incoming test - null argument - bad exception " + ex);
        }
        try{
            destService.getAllIncomingFlights(destWithNullID);
            fail("Get all incoming test - null ID");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get all incoming test - null ID - bad exception " + ex);
        }
        try{
            destService.getAllIncomingFlights(destRemovedFromDB);
            fail("Get all incoming test - destination not in DB");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get all incoming test - destination not in DB - bad exception " + ex);
        }
        try{
            List<FlightTO> result = destService.getAllIncomingFlights(destInDB1);
            List<FlightTO> demand = Arrays.asList(flight2, flight4);
            assertDeepEqualsListFlight(demand, result);
            result = destService.getAllIncomingFlights(destInDB2);
            demand = Arrays.asList(flight1, flight3);
            assertDeepEqualsListFlight(demand, result);
        } catch (DataAccessException ex){
            fail("Get all incoming flights test - exception thrown " + ex);
        } catch (Exception ex){
            fail("Get all incoming flights test - bad exception " + ex);
        }
    }
    
    @Test
    public void getAllOutcomingFlightsTest(){
        try{
            destService.getAllOutcomingFlights(null);
            fail("Get all outcoming test - null argument");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get all outcoming test - null argument - bad exception " + ex);
        }
        try{
            destService.getAllOutcomingFlights(destWithNullID);
            fail("Get all outcoming test - null ID");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get all outcoming test - null ID - bad exception " + ex);
        }
        try{
            destService.getAllOutcomingFlights(destRemovedFromDB);
            fail("Get all outcoming test - destination not in DB");
        } catch (DataAccessException ex){
        } catch (Exception ex){
            fail("Get all outcoming test - destination not in DB - bad exception " + ex);
        }
        try{
            List<FlightTO> result = destService.getAllOutcomingFlights(destInDB1);
            List<FlightTO> demand = Arrays.asList(flight1, flight2);
            assertDeepEqualsListFlight(demand, result);
            result = destService.getAllOutcomingFlights(destInDB2);
            demand = Arrays.asList(flight3, flight4);
            assertDeepEqualsListFlight(demand, result);
        } catch (DataAccessException ex){
            fail("Get all outcoming flights test - exception thrown " + ex);
        } catch (Exception ex){
            fail("Get all outcoming flights test - bad exception " + ex);
        }
    }
    
    private static DestinationTO getDestination(){
        DestinationTO dto = new DestinationTO();
        counter++;
        dto.setCity("city" + counter);
        dto.setCode("code" + counter);
        dto.setCountry("country" + counter);
        return dto;
    }
    
    
    private static StewardTO createSteward(){
        StewardTO s = new StewardTO();
        s.setFirstName("first");
        s.setLastName("last");
        return s;
    }
    
    private static FlightTO createFlight(DestinationTO from, DestinationTO to){
        FlightTO f = new FlightTO();
        AirplaneTO ap = new AirplaneTO();
        ap.setCapacity(100);
        ap.setName("mary");
        ap.setName("boeing");
        f.setAirplaneTO(ap);
        f.setArrivalTime(new Timestamp(10000L));
        f.setDepartureTime(new Timestamp(10000L));
        f.setOrigin(from);
        f.setTarget(to);
        StewardTO s = createSteward();
        List<StewardTO> ls = new ArrayList<>();
        ls.add(s);
        f.setStewList(ls);
        return f;
    }
    
    private void assertDeepEquals(FlightTO flight1, FlightTO flight2){
        assertEquals(flight1.getId(), flight2.getId());
        assertEquals(flight1.getArrivalTime(), flight2.getArrivalTime());
        assertEquals(flight1.getDepartureTime(), flight2.getDepartureTime());
        assertDeepEquals(flight1.getOrigin(), flight2.getOrigin());
        assertDeepEquals(flight1.getTarget(), flight2.getTarget());
        assertDeepEqualsListStew(flight1.getStewList(), flight2.getStewList());
        assertDeepEquals(flight1.getAirplaneTO(), flight2.getAirplaneTO());
    }
    
    private void assertDeepEquals(StewardTO stew1, StewardTO stew2){
        assertEquals(stew1.getId(), stew2.getId());
        assertEquals(stew1.getFirstName(), stew2.getFirstName());
        assertEquals(stew1.getLastName(), stew2.getLastName());
    }
    
    private void assertDeepEquals(AirplaneTO des1, AirplaneTO des2){
        assertEquals(des1.getId(), des2.getId());
        assertEquals(des1.getCapacity(), des2.getCapacity());
        assertEquals(des1.getName(), des2.getName());
        assertEquals(des1.getType(), des2.getType());
    }
    
    private void assertDeepEquals(DestinationTO des1, DestinationTO des2){
        assertEquals(des1.getId(), des2.getId());
        assertEquals(des1.getCity(), des2.getCity());
        assertEquals(des1.getCode(), des2.getCode());
        assertEquals(des1.getCountry(), des2.getCountry());
    }
    
    private void assertDeepEqualsListFlight(List<FlightTO> flight1, List<FlightTO> flight2){
        if(!checkListsOnNull(flight1, flight2)){fail(); }
        Iterator i1 = flight1.iterator();
        Iterator i2 = flight2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            FlightTO f1 = (FlightTO)i1.next();
            FlightTO f2 = (FlightTO)i2.next();
            assertDeepEquals(f1, f2);
        }
    }
    
    private void assertDeepEqualsListStew(List<StewardTO> stew1, List<StewardTO> stew2){
        if(!checkListsOnNull(stew1, stew2)){fail(); }
        Iterator i1 = stew1.iterator();
        Iterator i2 = stew2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            StewardTO s1 = (StewardTO)i1.next();
            StewardTO s2 = (StewardTO)i2.next();
            assertDeepEquals(s1, s2);
        }
    }
    
    private void assertDeepEqualsListDest(List<DestinationTO> des1, List<DestinationTO> des2){
        if(!checkListsOnNull(des1, des2)){fail(); }
        Iterator i1 = des1.iterator();
        Iterator i2 = des2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            DestinationTO d1 = (DestinationTO)i1.next();
            DestinationTO d2 = (DestinationTO)i2.next();
            assertDeepEquals(d1, d2);
        }
    }
}
