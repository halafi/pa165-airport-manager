/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.DestinationDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Samo
 */
public class AirplaneServiceImplTest extends AbstractTest{
    
    @Autowired
    private DestinationServiceImpl destService;
    
    //DestinationDAO destDao = Mockito.mock(DestinationDAOImpl.class);
    @Mock
    private DestinationDAO destDao;
    
}
