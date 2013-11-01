package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.AbstractServiceTest;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.StewardServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.StewardDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
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

    @BeforeClass
    public static void beforeClass() {
       
    }
    
    @Before
     public void init() {
        MockitoAnnotations.initMocks(this);
    }

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
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
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
        verify(stewDAO).updateSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
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
    @Test
    public void testRemoveSteward() throws JPAException {
        StewardTO stewTo = newStewardTO("Elaine","Dickinson");
        service.createSteward(stewTo);
        verify(stewDAO).createSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
        service.removeSteward(stewTo);
        verify(stewDAO).removeSteward(EntityDTOTransformer.stewardTOConvert(stewTo));
    }
    
    /**
     * Attempts to find Null Steward.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
     */
    @Test (expected=DataAccessException.class)
    public void testFindNull() throws JPAException {
        service.findSteward(null);
    }
    
    /**
     * Test for fidning steward.
     * @throws JPAException 
     */
    @Test
    public void testFindSteward() throws JPAException {
        StewardTO expected = Mockito.mock(StewardTO.class);
        try {
            
            service.createSteward(expected);
            StewardTO actual = service.findSteward(Mockito.mock(Long.class));
            verify(stewDAO).getSteward(Mockito.mock(Long.class));
            assertEquals(expected, actual);
            assertDeepEquals(expected, actual);
        } catch (DataAccessException ex) {
            fail("DataAccessException was thrown. " + ex);
        }
    }
    
    @Test
    public void testFindAllStewards() throws JPAException {
        StewardTO stew = newStewardTO("Elaine","Dickinson");
        List<StewardTO> expected = new ArrayList();
        expected.add(stew);
        service.createSteward(stew);
        List<StewardTO> actual = service.findAllStewards();
        verify(stewDAO).getAllStewards();
        assertEquals(expected, actual);
        
    }
    
    /**
     * Attempts to retrive all Null Steward flights.
     * @throws cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException
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
