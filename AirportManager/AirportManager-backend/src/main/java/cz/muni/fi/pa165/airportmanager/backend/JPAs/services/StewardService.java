<<<<<<< HEAD
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
=======
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Filip
 */
public interface StewardService {
    /**
     * Creates new steward.
     * @param stewardTO specifies the steward to be created
     */
    public void createSteward(StewardTO stewardTO) throws DataAccessException;
    
     /**
     * Updates steward.
     * 
     * @param stewardTO specifies the steward to be created
     */
    public void updateSteward(StewardTO stewardTO) throws DataAccessException;
    
    /**
     * Deletes steward.
     * 
     * @param stewardTO specifies the steward to be deleted
     */
    public void removeSteward(StewardTO stewardTO) throws DataAccessException;
    
    /**
     * Returns steward specified by id.
     * 
     * @param id specifies id of steward
     * @return stewardTO specified by id 
     */
    public StewardTO getSteward(Long id) throws DataAccessException;
    
    /**
     * Returns list of all stewards.
     * 
     * @return List of stewardsTOs
     */
    public List<StewardTO> getAllStewards() throws DataAccessException;
    
    /**
     * Returns List of all flights associated to steward.
     * 
     * @param steward specifies the steward
     * @return List of flightsTos
     */
    public List<FlightTO> getAllStewardsFlights(StewardTO steward) throws DataAccessException;
}
>>>>>>> 311e5fa5269c5ded229f4dc7cf33cf4500c6c52d
