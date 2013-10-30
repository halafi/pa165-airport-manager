/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
public interface StewardService {
    
    void createSteward(StewardTO steward) throws DataAccessException;
    
    void updateSteward(StewardTO steward) throws DataAccessException;
    
    void removeSteward(StewardTO steward) throws DataAccessException;
    
    StewardTO findSteward(Long id) throws DataAccessException;
    
    List<StewardTO> findAllStewards() throws DataAccessException;
    
    List<FlightTO> getAllStewardsFlights() throws DataAccessException;
}
