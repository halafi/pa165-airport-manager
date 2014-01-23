package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractTest;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.StewardServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

/**
 *
 * @author Filip
 */
@RunWith(MockitoJUnitRunner.class)
public class StewardServiceImplTest extends AbstractTest {
    
    @Mock
    private StewardDAO stewDAO;
    @InjectMocks
    private StewardServiceImpl service;

    @Before
    public void setUpMock() {
        doThrow(DataRetrievalFailureException .class).when(stewDAO).createSteward(null);
        doThrow(DataRetrievalFailureException .class).when(stewDAO).updateSteward(null);
        doThrow(DataRetrievalFailureException .class).when(stewDAO).removeSteward(null);
        doThrow(DataRetrievalFailureException .class).when(stewDAO).getSteward(null);
        doThrow(DataRetrievalFailureException .class).when(stewDAO).getAllStewardsFlights(null);
    }
    
    @After
    public void reset() {
        Mockito.reset(stewDAO);
    }
    
    
    /**
     * Attempts to create Null Steward. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testCreateNull() {
        service.createSteward(null);
    }
    
    /**
     * Test for steward creation.
     */
    @Test
    public void testCreateSteward() {
        StewardTO expected = newStewardTO("Elaine","Dickinson");
        service.createSteward(expected);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(expected));
        when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(expected));
        StewardTO actual = service.getSteward(-1L);
        assertDeepEquals(actual, expected);
    }
    
    /**
     * Attempts to update Null Steward. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testUpdateNull() {
        service.updateSteward(null);
    }
    
    /**
     * Test for steward updating.
     */
    @Test
    public void testUpdateSteward() {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        stewTo.setFirstName("Marie");
        service.updateSteward(stewTo);
        when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(stewTo));
        StewardTO actual = service.getSteward(-1L);
        verify(stewDAO).updateSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        assertDeepEquals(stewTo, actual);
    }
    
    /**
     * Attempts to remove Null Steward.
     */
    @Test (expected=DataAccessException.class)
    public void testRemoveNull() {
        service.removeSteward(null);
    }
    
    /**
     * Test for removing steward.
     */
    @Test (expected=DataAccessException.class)
    public void testRemoveSteward() {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(stewTo));
        service.removeSteward(stewTo);
        verify(stewDAO).removeSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        when(stewDAO.getSteward(-1L)).thenThrow(DataRetrievalFailureException .class);
        service.getSteward(-1L);
    }
    
    /**
     * Attempts to find Null Steward.
     */
    @Test (expected=DataAccessException.class)
    public void testFindNull(){
        service.getSteward(null);
    }
    
    /**!!!
     * Test for finding steward.
     * @throws AirplaneDaoException 
     */
    @Test
    public void testFindSteward() {
        StewardTO expected = newStewardTO("Elaine","Dickinson");
        try {
            service.createSteward(expected);
            verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(expected));
            when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(expected));
            StewardTO actual = service.getSteward(-1L);
            verify(stewDAO).getSteward(-1L);
            assertDeepEquals(expected, actual);
        } catch (DataAccessException ex) {
            fail("DataAccessException was thrown. " + ex);
        }
    }
    
    /**!!!
     * Test for fining all stewards.
     */
    @Test
    public void testFindAllStewards() {
        StewardTO stew = newStewardTO("Elaine","Dickinson");
        StewardTO stew2 = newStewardTO("Samo","Teammate");
        List<Steward> list = new ArrayList();
        when(stewDAO.getAllStewards()).thenReturn(list);
        assertEquals(0, service.getAllStewards().size());
        verify(stewDAO).getAllStewards();
        service.createSteward(stew);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stew));
        list.add(EntityDTOTransformer.stewardTOConvert(stew));
        when(stewDAO.getAllStewards()).thenReturn(list);
        assertEquals(1, service.getAllStewards().size());
        service.createSteward(stew2);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stew2));
        list.add(EntityDTOTransformer.stewardTOConvert(stew2));
        when(stewDAO.getAllStewards()).thenReturn(list);
        assertEquals(2, service.getAllStewards().size());
        assertDeepEquals(EntityDTOTransformer.stewardListConvert(list), service.getAllStewards());
    }
    
    /**
     * Attempts to retrieve all Null Steward flights.
     */
    @Test (expected=DataAccessException.class)
    public void testGetAllNullFlights() {
        service.getAllStewardsFlights(null);
    }
    
    /**
     * Test for getting all stewards flights.
     */
    @Test
    public void testGetAllStewardsFlights() {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.getAllStewardsFlights(stewTo);
        verify(stewDAO).getAllStewardsFlights(EntityDTOTransformer.stewardTOConvert(stewTo));
    }
    
    /**
     * Constructor for StewardTO.
     */
    private static StewardTO newStewardTO(String firstName, String lastName) {
        StewardTO steward = new StewardTO();
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        return steward;
    }
    
    /**
     * Deep Equalajzr for two StewardsTOs.
     * @param stew1 first DTO
     * @param stew2 second DTO
     */
    private void assertDeepEquals(StewardTO stew1, StewardTO stew2) {
        assertEquals(stew1.getId(), stew2.getId());
        assertEquals(stew1.getFirstName(), stew2.getFirstName());
        assertEquals(stew1.getLastName(), stew2.getLastName());
    }
    
    private void assertDeepEquals(List<StewardTO> stew1, List<StewardTO> stew2) {
        for (int i = 0; i < stew1.size(); i++) {
            StewardTO expected = stew1.get(i);
            StewardTO actual = stew2.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}