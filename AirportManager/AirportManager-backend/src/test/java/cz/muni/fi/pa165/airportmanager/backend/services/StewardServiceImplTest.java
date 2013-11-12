package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractServiceTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.StewardServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.dao.DataAccessException;
/**
 *
 * @author Filip
 */
public class StewardServiceImplTest extends AbstractServiceTest {
    
    @Mock
    private StewardDAO stewDAO;
    @InjectMocks
    private StewardServiceImpl service;

    @After
    public void reset() {
        Mockito.reset(stewDAO);
    }
    
    /**
     * Attempts to create Null Steward. Good luck with that!
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testCreateNull() throws JPAException {
        service.createSteward(null);
    }
    
    /**
     * Test for steward creation.
     * @throws JPAException 
     */
    @Test
    public void testCreateSteward() throws JPAException {
        StewardTO expected = newStewardTO("Elaine","Dickinson");
        service.createSteward(expected);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(expected));
        when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(expected));
        StewardTO actual = service.findSteward(-1L);
        assertDeepEquals(actual, expected);
    }
    
    /**
     * Attempts to update Null Steward. Good luck with that!
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testUpdateNull() throws JPAException {
        service.updateSteward(null);
    }
    
    /**
     * Test for steward updating.
     * @throws JPAException 
     */
    @Test
    public void testUpdateSteward() throws JPAException {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        stewTo.setFirstName("Marie");
        service.updateSteward(stewTo);
        when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(stewTo));
        StewardTO actual = service.findSteward(-1L);
        verify(stewDAO).updateSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        assertDeepEquals(stewTo, actual);
    }
    
    /**
     * Attempts to remove Null Steward.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testRemoveNull() throws JPAException {
        service.removeSteward(null);
    }
    
    /**
     * Test for removing steward.
     * @throws JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testRemoveSteward() throws JPAException {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(stewTo));
        service.removeSteward(stewTo);
        verify(stewDAO).removeSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        Steward stew = EntityDTOTransformer.stewardTOConvert(stewTo);
        when(stewDAO.getSteward(-1L)).thenReturn(null);
        StewardTO actual = service.findSteward(-1L);

    }
    
    /**
     * Attempts to find Null Steward.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testFindNull() throws JPAException {
        service.findSteward(null);
    }
    
    /**!!!
     * Test for finding steward.
     * @throws JPAException 
     */
    @Test
    public void testFindSteward() throws JPAException {
        StewardTO expected = newStewardTO("Elaine","Dickinson");
        try {
            service.createSteward(expected);
            verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(expected));
            when(stewDAO.getSteward(-1L)).thenReturn(EntityDTOTransformer.stewardTOConvert(expected));
            StewardTO actual = service.findSteward(-1L);
            verify(stewDAO).getSteward(-1L);
            assertDeepEquals(expected, actual);
        } catch (DataAccessException ex) {
            fail("DataAccessException was thrown. " + ex);
        }
    }
    
    /**!!!
     * Test for fining all stewards.
     * @throws JPAException 
     */
    @Test
    public void testFindAllStewards() throws JPAException {
        StewardTO stew = newStewardTO("Elaine","Dickinson");
        StewardTO stew2 = newStewardTO("Samo","Teammate");
        List<Steward> list = new ArrayList();
        when(stewDAO.getAllStewards()).thenReturn(list);
        assertEquals(0, service.findAllStewards().size());
        verify(stewDAO).getAllStewards();
        service.createSteward(stew);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stew));
        list.add(EntityDTOTransformer.stewardTOConvert(stew));
        when(stewDAO.getAllStewards()).thenReturn(list);
        assertEquals(1, service.findAllStewards().size());
        service.createSteward(stew2);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stew2));
        list.add(EntityDTOTransformer.stewardTOConvert(stew2));
        when(stewDAO.getAllStewards()).thenReturn(list);
        assertEquals(2, service.findAllStewards().size());
        assertDeepEquals(EntityDTOTransformer.stewardListConvert(list), service.findAllStewards());
    }
    
    /**
     * Attempts to retrieve all Null Steward flights.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testGetAllNullFlights() throws JPAException {
        service.getAllStewardsFlights(null);
    }
    
    /**
     * Test for getting all stewards flights.
     * @throws JPAException 
     */
    @Test
    public void testGetAllStewardsFlights() throws JPAException {
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
    
    /**
     * Comparator by id.
     */
    private static Comparator<StewardTO> idComparator = new Comparator<StewardTO>() {
        @Override
        public int compare(StewardTO s1, StewardTO s2) {
            return s1.getId().compareTo(s2.getId());
        }
    };
}
