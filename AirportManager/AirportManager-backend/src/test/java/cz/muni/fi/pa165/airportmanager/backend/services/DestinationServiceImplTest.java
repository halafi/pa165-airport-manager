/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Juraj Duráni
 */
public class DestinationServiceImplTest extends AbstractTest{
    
    @Autowired
    DestinationServiceImpl destService;
    @Mock
    DestinationDAO destDao;
    
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        System.out.println(destDao);
        destService.setDestinationDao(destDao);
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
    
    
    
}
