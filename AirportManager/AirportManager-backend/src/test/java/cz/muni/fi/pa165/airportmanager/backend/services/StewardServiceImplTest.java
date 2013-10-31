package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractServiceTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl.StewardServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.Comparator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
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

    /**
     * Attempts to create Null Steward. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testCreateNull() throws JPAException {
        service.createSteward(null);
    }
    
    @Test
    public void testCreateSteward() throws JPAException {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
    }
    
    /**
     * Attempts to update Null Steward. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testUpdateNull() throws JPAException {
        service.updateSteward(null);
    }
    
    @Test
    public void testUpdateSteward() throws JPAException {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        stewTo.setFirstName("Marie");
        service.updateSteward(stewTo);
        verify(stewDAO).updateSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
    }
    
    /**
     * Attempts to remove Null Steward. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testRemoveNull() throws JPAException {
        service.removeSteward(null);
    }
    
    @Test
    public void testRemoveSteward() throws JPAException {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        service.removeSteward(stewTo);
        verify(stewDAO).removeSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
    }
    
    /**
     * Attempts to find Null Steward. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testFindNull() throws JPAException {
        service.findSteward(null);
    }
    
    //should work
    @Test
    public void testFindSteward() throws JPAException {
        StewardTO expected = newStewardTO("Elaine","Dickinson");
        try {
            expected.setId(new Long(1));
            service.createSteward(expected);
            StewardTO actual = service.findSteward(expected.getId());
            verify(stewDAO).getSteward(expected.getId());
            assertEquals(expected, actual);
            assertDeepEquals(expected, actual);
        } catch (DataAccessException ex) {
            fail("DataAccessException was thrown.");
        }
    }
    
    @Test
    public void testFindAllStewards() throws JPAException {
        service.findAllStewards();
        verify(stewDAO).getAllStewards();
    }
    
    /**
     * Attempts to retrive all Null Steward flights. Good luck with that!
     */
    @Test (expected=DataAccessException.class)
    public void testGetAllNullFlights() throws JPAException {
        service.getAllStewardsFlights(null);
    }
    
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
