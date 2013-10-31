/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
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
        destWithNullAtributes.setId(3L);
        
        destWithNullAtributesAndID = new DestinationTO();
        
        destWithEmptyAtributes = new DestinationTO();
        destWithEmptyAtributes.setId(4L);
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
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributesAndID));
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributes));
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributesAndNullID));
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* update */
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(null);
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributes));
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributesAndID));
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributes));
        doThrow(IllegalArgumentException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributesAndNullID));
        doThrow(JPAException.class).when(destDao).updateDestination(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* remove */
        doThrow(IllegalArgumentException.class).when(destDao).removeDestination(null);
        doThrow(IllegalArgumentException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(IllegalArgumentException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributesAndID));
        doThrow(IllegalArgumentException.class).when(destDao).removeDestination(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributesAndNullID));
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
        doThrow(IllegalArgumentException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributesAndID));
        doThrow(IllegalArgumentException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributesAndNullID));
        doThrow(JPAException.class).when(destDao).getAllIncomingFlights(
                EntityDTOTransformer.destinationTOConvert(destRemovedFromDB));
        /* getAllOutcoming */
        doThrow(IllegalArgumentException.class).when(destDao).getAllOutcomingFlights(null);
        doThrow(IllegalArgumentException.class).when(destDao).getAllOutcomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullID));
        doThrow(IllegalArgumentException.class).when(destDao).getAllOutcomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithNullAtributesAndID));
        doThrow(IllegalArgumentException.class).when(destDao).getAllOutcomingFlights(
                EntityDTOTransformer.destinationTOConvert(destWithEmptyAtributesAndNullID));
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
    
    @Test
    public void createTest(){
//        destService.createDestination(destWithNullID);
    }
    
    @Test
    public void removeTest(){
        //TODO
    }
    
    @Test
    public void updateTest(){
        //TODO
    }
    
    @Test
    public void getTest(){
        //TODO
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
}
