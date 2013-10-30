/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
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
    
    
    @Before
    public void setUp(){
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
        
        setUpMock();
    }
    
    private void setUpMock(){
        doThrow(IllegalArgumentException.class).when(destDao).createDestination(null);
        //TODO
    }
    
    @Test
    public void createTest(){
        //TODO
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
    
}
