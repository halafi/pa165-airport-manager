/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chorke
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/testingApplicationContext.xml"})
//@Transactional
//@EnableTransactionManagement
public abstract class AbstractTest {
    
}
