
package cz.muni.fi.pa165.airportmanager.backend;

import java.util.List;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Juraj Duráni
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/testingApplicationContext.xml"})
public abstract class AbstractTest {
    protected boolean checkListsOnNull(List l1, List l2){
        if(l1 == null && l2 == null){ return true; }
        if(l1 == null && l2 != null){ return false; }
        if(l1 != null && l2 == null){ return false; }
        if(l1.size() != l2.size()){ return false; }
        return true;
    }
}
