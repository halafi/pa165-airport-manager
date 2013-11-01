/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.DestinationDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.services.ServiceDataAccessException;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.AirplaneServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.DestinationDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Samo
 */
public class AirplaneServiceImplTest extends AbstractTest{
    
    @InjectMocks
    private AirplaneServiceImpl airplaneService;
    
    //@Autowired
    //private AirplaneServiceImpl airplaneService;
    
    @Mock
    private AirplaneDAO airplaneDAO;
    
    private AirplaneTO airplane1 = new AirplaneTO();
    private AirplaneTO airplane2 = new AirplaneTO();
    
    private AirplaneTO airplaneNull = new AirplaneTO();
    private AirplaneTO airplaneNullId = new AirplaneTO();
    private AirplaneTO airplaneNullArgs = new AirplaneTO();
    private AirplaneTO airplaneEmptyArgs = new AirplaneTO();
    private AirplaneTO airplaneNotInDb = new AirplaneTO();
    
    private FlightTO flight1 = new FlightTO();
    private FlightTO flight2 = new FlightTO();
//    private DestinationTO destinationTO1 = new DestinationTO();
//    private DestinationTO destinationTO2 = new DestinationTO();
    
    @Before
    public void setup() throws JPAException{
        MockitoAnnotations.initMocks(this);
        airplaneService.setAirplaneDao(airplaneDAO);
        
        airplane1 = createAirplane(10, "GERT-Y", "Boeing 747-800");
        airplane1.setId(new Long(1));
        airplane2 = createAirplane(10, "RYAN-A", "Airbus a360");
        airplane2.setId(new Long(2));
        airplaneNull = null;
        airplaneNullId = createAirplane(10, "GERT-Y", "Boeing 747-800");
        airplaneNullId.setId(null);
        airplaneNullArgs = createAirplane(10, null, null);
        airplaneNullArgs.setId(new Long(3));
        airplaneEmptyArgs = createAirplane(10, "", "");
        airplaneEmptyArgs.setId(new Long(4));
        airplaneNotInDb = createAirplane(10, "GERT-Y", "Boeing 747-800");
        airplaneNotInDb.setId(new Long(5));
        flight1 = createFlight(airplane1);
        flight2 = createFlight(airplane2);
        //        destinationTO1 = createDestination();
//        destinationTO1.setId(new Long(1));
//        destinationTO2 = createDestination();
//        destinationTO2.setId(new Long(2));
        mockReactions();
    }
    
    private void mockReactions() throws JPAException{
        //create
        doThrow(IllegalArgumentException.class).when(airplaneDAO).createAirplane(null);
//        doThrow(IllegalArgumentException.class).when(airplaneDAO).createAirplane(
//                EntityDTOTransformer.airplaneTOConvert(airplaneNullId));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).createAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullArgs));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).createAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneEmptyArgs));
        //update
        doThrow(IllegalArgumentException.class).when(airplaneDAO).updateAirplane(null);
        doThrow(IllegalArgumentException.class).when(airplaneDAO).updateAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullId));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).updateAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullArgs));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).updateAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneEmptyArgs));
        doThrow(JPAException.class).when(airplaneDAO).updateAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNotInDb));
        //remove
        doThrow(IllegalArgumentException.class).when(airplaneDAO).removeAirplane(null);
        doThrow(IllegalArgumentException.class).when(airplaneDAO).removeAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullId));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).removeAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullArgs));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).removeAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneEmptyArgs));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).removeAirplane(
                EntityDTOTransformer.airplaneTOConvert(airplaneNotInDb));
        //get
        doThrow(IllegalArgumentException.class).when(airplaneDAO).getAirplane(null);
        doThrow(JPAException.class).when(airplaneDAO).getAirplane(airplaneNotInDb.getId());
        //getAll
        when(airplaneDAO.getAllAirplanes()).thenReturn(Arrays.asList(
                EntityDTOTransformer.airplaneTOConvert(
                airplane1
                )
                ,
                EntityDTOTransformer.airplaneTOConvert(
                airplane2
                )
                ));
        //getAllFlights
        doThrow(IllegalArgumentException.class).when(airplaneDAO).getAllAirplanesFlights(null);
        doThrow(IllegalArgumentException.class).when(airplaneDAO).getAllAirplanesFlights(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullArgs));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).getAllAirplanesFlights(
                EntityDTOTransformer.airplaneTOConvert(airplaneNullId));
        doThrow(IllegalArgumentException.class).when(airplaneDAO).getAllAirplanesFlights(
                EntityDTOTransformer.airplaneTOConvert(airplaneEmptyArgs));
        doThrow(JPAException.class).when(airplaneDAO).getAllAirplanesFlights(
                EntityDTOTransformer.airplaneTOConvert(airplaneNotInDb));
        when(airplaneDAO.getAllAirplanesFlights(EntityDTOTransformer.airplaneTOConvert(airplane1))).
                thenReturn(Arrays.asList(
                    EntityDTOTransformer.flightTOConvert(flight1)));
        when(airplaneDAO.getAllAirplanesFlights(EntityDTOTransformer.airplaneTOConvert(airplane2))).
                thenReturn(Arrays.asList(
                    EntityDTOTransformer.flightTOConvert(flight2)));
    }
    
    @Test
    public void createTest(){
        try{
            airplaneService.createAirplane(null);
            fail("Creating airplane with null - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Creating airplane with null - bad exception" + ex);
        }
        try{
            airplaneService.createAirplane(airplaneEmptyArgs);
            fail("Creating airplane with emptyArgs - no exception");
        }catch(DataAccessException ex){
        }catch(Exception ex){
            fail("Creating airplane with emptyArgs - bad exception" + ex.getMessage());
        }
        try{
            airplaneService.createAirplane(airplaneNullArgs);
            fail("Creating airplane with nullArgs - no exception");
        }catch(DataAccessException ex){
        }catch(Exception ex){
            fail("Creating airplane with nullArgs - bad exception" + ex.getMessage());
        }
//        try{
//            airplaneService.createAirplane(airplaneNullId);
//            fail("Creating airplane with null ID - no exception");
//        }catch(ServiceDataAccessException ex){
//        }catch(Exception ex){
//            fail("Creating airplane with null - bad exception" + ex.getMessage());
//        }
    }
    
    @Test
    public void updateTest(){
        try{
            airplaneService.updateAirplane(airplaneEmptyArgs);
            fail("Updating airplane with emptyArgs - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Updating airplane with emptyArgs - bad exception" + ex);
        }
        try{
            airplaneService.updateAirplane(null);
            fail("Updating airplane with null - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Updating airplane with null - bad exception" + ex);
        }
        try{
            airplaneService.updateAirplane(airplaneNotInDb);
            fail("Updating airplane with with id not in db - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Updating airplane with id not in db - bad exception" + ex);
        }
        try{
            airplaneService.updateAirplane(airplaneNullArgs);
            fail("Updating airplane with nullArgs - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Updating airplane with nullArgs - bad exception" + ex);
        }
        try{
            airplaneService.updateAirplane(airplaneNullId);
            fail("Updating airplane with null id - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Updating airplane with null id - bad exception" + ex);
        }
    }
    
    @Test
    public void removeTest(){
        try{
            airplaneService.removeAirplane(null);
            fail("Removing airplane with null - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Removing airplane with null - bad exception" + ex);
        }
        try{
            airplaneService.removeAirplane(airplaneNullId);
            fail("Removing airplane with null id - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Removing airplane with null id - bad exception" + ex);
        }
        try{
            airplaneService.removeAirplane(airplaneNotInDb);
            fail("Removing airplane with id not in db - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Removing airplane with id not in db - bad exception" + ex);
        }
    }
    
    @Test
    public void getTest(){
        try{
            airplaneService.getAirplane(null);
            fail("Getting airplane with null - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting airplane with null - bad exception" + ex);
        }
        try{
            airplaneService.getAirplane(airplaneNullId.getId());
            fail("Getting airplane with null id - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting airplane with null id - bad exception" + ex);
        }
        try{
            airplaneService.getAirplane(airplaneNotInDb.getId());
            fail("Getting airplane with id not in db - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting airplane with id not in db - bad exception" + ex);
        }
    }
    
    @Test
    public void getAllTest(){
        try{
            List<AirplaneTO> airList = new ArrayList<AirplaneTO>();
            airList = airplaneService.getAllAirplanes();
            assertDeepEquals(airList.get(0), airplane1);
            assertDeepEquals(airList.get(1), airplane2);
            //fail("Getting airplane with null - no exception");
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting all airplanes - exception" + ex);
        }
    }
    
    @Test
    public void getAllFlightsTest(){
        try{
            airplaneService.getAllAirplanesFlights(null);
            fail("Getting flights for airplane null - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting flights for airplane null - bad exception" + ex);
        }
        try{
            airplaneService.getAllAirplanesFlights(airplaneNullId);
            fail("Getting flights for airplane null id - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting flights for airplane null id - bad exception" + ex);
        }
        try{
            airplaneService.getAllAirplanesFlights(airplaneNotInDb);
            fail("Getting flights for airplane not in db - no exception");
        }catch(ServiceDataAccessException ex){
        }catch(Exception ex){
            //fail("Creating airplane with null - bad exception" + ex.getMessage());
            fail("Getting flights for airplane not in db - bad exception" + ex);
        }
        try{
            List<FlightTO> flightList = new ArrayList<FlightTO>();
            flightList = airplaneService.getAllAirplanesFlights(airplane1);
            assertDeepEqualsFlights(flight1, flightList.get(0));
        }catch(Exception ex){
            fail("Getting all airplane flights - exception" + ex);
        }
    }
    
    public FlightTO createFlight(AirplaneTO airplane){
        FlightTO flight = new FlightTO();
        flight.setAirplaneTO(airplane);
        flight.setArrivalTime(new Timestamp(new Long(1000)));
        flight.setDepartureTime(new Timestamp(new Long(1000)));
        flight.setOrigin(createDestination());
        flight.setTarget(createDestination());
        List<StewardTO> stewardList = new ArrayList<StewardTO>();
        stewardList.add(createSteward());
        flight.setStewList(stewardList);
        return flight;
    }
    
    public StewardTO createSteward(){
        StewardTO steward = new StewardTO();
        steward.setFirstName("Zafod");
        steward.setLastName("Biblbrox");
        return steward;
    }
    
    public DestinationTO createDestination(){
        DestinationTO destination = new DestinationTO();
        destination.setCity("London-Stansted");
        destination.setCode("STD");
        destination.setCountry("Great Britain");
        return destination;
    }
    
    public AirplaneTO createAirplane(int capacity, String name, String type){
        AirplaneTO airplane = new AirplaneTO();
        airplane.setCapacity(capacity);
        airplane.setName(name);
        airplane.setType(type);
        return airplane;
    }
    
    private void assertDeepEquals(AirplaneTO air1, AirplaneTO air2){
        assertEquals(air1.getId(), air2.getId());
        assertEquals(air1.getCapacity(), air2.getCapacity());
        assertEquals(air1.getName(), air2.getName());
        assertEquals(air1.getType(), air2.getType());
    }
    
    private void assertDeepEqualsFlights(FlightTO flight1, FlightTO flight2){
        assertEquals(flight1.getId(), flight2.getId());
        assertEquals(flight1.getArrivalTime(), flight2.getArrivalTime());
        assertEquals(flight1.getDepartureTime(), flight2.getDepartureTime());
        assertDeepEquals(flight1.getAirplaneTO(), flight2.getAirplaneTO());
        asserDeepEqualsDestinations(flight1.getOrigin(), flight2.getOrigin());
        asserDeepEqualsDestinations(flight1.getTarget(), flight2.getTarget());
    }
    
    private void asserDeepEqualsDestinations(DestinationTO des1, DestinationTO des2){
        assertEquals(des1.getId(), des2.getId());
        assertEquals(des1.getCity(), des2.getCity());
        assertEquals(des1.getCode(), des2.getCode());
        assertEquals(des1.getCountry(), des2.getCountry());
    }

}
