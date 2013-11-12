/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.util.List;

/**
 *
 * @author Samo
 */
public interface StewardDAO {
    
     /**
     * Creates new steward.
     * 
     * @param steward specifies the steward to be created
     */
    public void createSteward(Steward steward);
    
     /**
     * Updates steward.
     * 
     * @param steward specifies the steward to be created
     */
    public void updateSteward(Steward steward);
    
    /**
     * Deletes steward.
     * 
     * @param steward specifies the steward to be deleted
     */
    public void removeSteward(Steward steward);
    
    /**
     * Returns steward specified by id.
     * 
     * @param id specifies id of steward
     * @return steward specified by id 
     */
    public Steward getSteward(Long id);
    
    /**
     * Returns list of all stewards.
     * 
     * @return List of stewards
     */
    public List<Steward> getAllStewards();
    
    /**
     * Returns List of all flights associated to steward.
     * 
     * @param steward specifies the steward
     * @return List of flights
     */
    public List<Flight> getAllStewardsFlights(Steward steward);
}