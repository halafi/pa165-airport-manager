/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.ServiceDataAccessException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Juraj Duráni
 */
public class DestinationServiceImplTest extends AbstractTest{
    
    private static int counter = 0;
    
    @Autowired
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
    public void setUp() throws JPAException{
//        System.out.println(destService);
        MockitoAnnotations.initMocks(this);
        destService.setDestinationDao(destDao);
        
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
    
    private void setUpMock() throws JPAException{
        /* doThrow */
        /* create */
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(null);
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributes));
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributes));
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* update */
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(null);
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributes));
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributes));
        doThrow(JPAException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* remove */
        doThrow(IllegalArgumentException.class).when(destDao).removeDestination(null);
        doThrow(IllegalArgumentException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(JPAException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* get */
        doThrow(IllegalArgumentException.class).when(destDao).getDestination(null);
        doThrow(JPAException.class).when(destDao).getDestination(
                destRemovedFromDB.getId());
        /* getAllIncoming */
        doThrow(IllegalArgumentException.class).when(destDao).getAllIncomingFlights(null);
        doThrow(IllegalArgumentException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(JPAException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* getAllOutcoming */
        doThrow(IllegalArgumentException.class).when(destDao).getAllOutcomingFlights(null);
        doThrow(IllegalArgumentException.class).when(destDao).getAllOutcomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(JPAException.class).when(destDao).getAllOutcomingFlights(
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
     * testuje sa aj getDestination(...) čiastočne
     */
    @Test
    public void createTest(){
//        destService.createDestination(destWithNullID);
        try{
            destService.createDestination(null);
            fail("Create test - null argument");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Create test - null argument - bad exception");
        }
        try{
            destService.createDestination(destRemovedFromDB);
            fail("Create test - argument with nonnull ID");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Create test - argument with nonnull ID - bad exception");
        }
        try{
            destService.createDestination(destWithNullAtributesAndID);
            fail("Create test - null atributes");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Create test - null atributes - bad exception");
        }
        try{
            destService.createDestination(destWithEmptyAtributesAndNullID);
            fail("Create test - emptu atributes");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Create test - empty atributes - bad exception");
        }
        try{
            destService.createDestination(destWithNullID);
            Destination des = new Destination();
            des.setCity(destWithNullID.getCity());
            des.setCode(destWithNullID.getCode());
            des.setCountry(destWithNullID.getCountry());
            des.setId(-1L);
            when(destDao.getDestination(-1L)).thenReturn(des);
            DestinationTO desTO = destService.getDestination(-1L);
            assertDeepEquals(desTO, EntityDTOTransformer.destinationConvert(des));
            when(destDao.getDestination(-1L)).thenReturn(null);
        } catch (ServiceDataAccessException ex){
            fail("Create test - OK argument - exception thrown");
        } catch (Exception ex){
            fail("Create test - OK argument - bad exception");
        }
    }
    
    /**
     * čiastočne aj getDestination(...)
     */
    @Test
    public void removeTest(){
        try{
            destService.removeDestination(null);
            fail("Remove test - null argument");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - null argument - bad exception");
        }
        try{
            destService.removeDestination(destWithNullID);
            fail("Remove test - null ID");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - null ID - bad exception");
        }
        try{
            destService.removeDestination(destRemovedFromDB);
            fail("Remove test - argument not in DB");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - argument not in DB - bad exception");
        }
        try{
            destService.removeDestination(destInDB1);
            when(destDao.getDestination(destInDB1.getId())).thenThrow(JPAException.class);
        } catch (ServiceDataAccessException ex){
            fail("Remove test - OK argument - exception thrown");
        } catch (Exception ex){
            fail("Remove test - OK argument - bad exception");
        }
        
        try{
            DestinationTO d = destService.getDestination(destInDB1.getId());
            if(d != null){
                fail("Remove test - not removed");
            }
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Remove test - removed - bad exception");
        }
        // navrátenie do pôvodného stavu
        try{
            when(destDao.getDestination(destInDB1.getId()))
                .thenReturn(EntityDTOTransformer.destinationTOConvert(destInDB1));
        } catch (JPAException ex){}
    }
    
    /** 
     * čiastočne aj getDestination(...)
     */
    @Test
    public void updateTest(){
        try{
            destService.updateDestination(null);
            fail("Update test - null argument");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Update test - null argument - bad exception");
        }
        try{
            destService.updateDestination(destWithNullID);
            fail("Update test - null ID");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Update test - null ID - bad exception");
        }
        try{
            destService.updateDestination(destWithEmptyAtributes);
            fail("Update test - empty atributes");
        } catch (ServiceDataAccessException ex){
        } catch (Exception ex){
            fail("Update test - empty atributes - bad exception");
        }
    }
    
    @Test
    public void getTest(){
//        System.out.println(destService.getDestination(2L));
    }
    
    @Test
    public void getAllTest(){
        //TODO
    }
    
    @Test
    public void getAllIncomingFlightsTest(){
//        System.out.println("//////////////////////////////////////////");
//        System.out.println(destService.getAllIncomingFlights(destInDB1));
//        System.out.println("//////////////////////////////////////////");
//        System.out.println(destService.getAllIncomingFlights(destInDB2));
//        System.out.println("//////////////////////////////////////////");
        //TODO
    }
    
    @Test
    public void getAllOutcomingFlightsTest(){
        //TODO
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
    
    private void assertDeepEquals(DestinationTO des1, DestinationTO des2){
        assertEquals(des1.getId(), des2.getId());
        assertEquals(des1.getCity(), des2.getCity());
        assertEquals(des1.getCode(), des2.getCode());
        assertEquals(des1.getCountry(), des2.getCountry());
    }
}
