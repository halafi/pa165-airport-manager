package cz.muni.fi.pa165.airportmanager.backend;

import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.util.Comparator;
import java.util.List;
import static junit.framework.Assert.assertNotNull;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for StewardDAOImpl.
 *
 * @author Filip
 */
public class StewardDAOImplTest extends TestCase {
    private StewardDAO stewardDAO;
    
    @Before
    public void setUp() {
        stewardDAO = new StewardDAOImpl();
    }
    
    @Test
    /**
     * Test for steward creation.
     */
    public void createSteward() {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        steward.setLastName("Dickinson");
        stewardDAO.createSteward(steward);
        
        assertNotNull(steward.getId());
        assertNotNull(steward.getFirstName());
        assertNotNull(steward.getLastName());
        
        Steward sameSteward = stewardDAO.getSteward(steward.getId());
        
        assertEquals(steward, sameSteward);
        assertNotSame(steward, sameSteward);
        assertDeepEquals(steward, sameSteward);
    }
    /**
     * Attempt of creating null steward.
     */
    @Test (expected = IllegalArgumentException.class)
    public void createNullSteward() {
        Steward steward = null;
        stewardDAO.createSteward(steward);
    }
    /**
     * Attempt of creating steward without last name.
     */
    @Test (expected = IllegalArgumentException.class)
    public void createStewardWithoutLastName() {
        Steward steward = new Steward();
        steward.setFirstName("Elaine");
        stewardDAO.createSteward(steward);
    }
    /**
     * Attempt of creating steward without first name.
     */
    @Test (expected = IllegalArgumentException.class)
    public void createStewardWithoutFirstName() {
        Steward steward = new Steward();
        steward.setLastName("Dickinson");
        stewardDAO.createSteward(steward);
    }

    
    
    
    /**
     * Check for equality of all steward parameters.
     * @param stew1 steward number one to be compared
     * @param stew2 steward number two to be compared
     */
    private void assertDeepEquals(Steward stew1, Steward stew2) {
        assertEquals(stew1.getId(), stew2.getId());
        assertEquals(stew1.getFirstName(), stew2.getFirstName());
        assertEquals(stew1.getLastName(), stew2.getLastName());
    }
    /**
     * Check for equality of two steward lists and all their parameters.
     * @param stewList1 steward list number one to be compared
     * @param stewList2 steward list number two to be compared
     */
    private void assertDeepEquals(List<Steward> stewList1, List<Steward> stewList2) {
        for (int i = 0; i < stewList1.size(); i++) {
            Steward expected = stewList1.get(i);
            Steward actual = stewList2.get(i);
            assertDeepEquals(expected, actual);
        }
    }
    /**
     * Comparatord by id.
     */
    private static Comparator<Steward> idComparator = new Comparator<Steward>() {
        @Override
        public int compare(Steward s1, Steward s2) {
            return s1.getId().compareTo(s2.getId());
        }
    };
}
