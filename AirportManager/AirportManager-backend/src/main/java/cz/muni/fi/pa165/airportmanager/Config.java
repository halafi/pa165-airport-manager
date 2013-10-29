/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Chorke
 */
@Configuration
public class Config {
    
   @Bean
   public EntityManagerFactory emf(){
       return Persistence.createEntityManagerFactory("AirportManager");
   }
}
