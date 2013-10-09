/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
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
    public void createSteward(Steward steward) throws JPAException, IllegalArgumentException;
    
     /**
     * Updates steward.
     * 
     * @param steward specifies the steward to be created
     */
    public void updateSteward(Steward steward) throws JPAException, IllegalArgumentException;
    
    /**
     * Deletes steward.
     * 
     * @param steward specifies the steward to be deleted
     */
    public void removeSteward(Steward steward) throws JPAException, IllegalArgumentException;
    
    /**
     * Returns steward specified by id.
     * 
     * @param id specifies id of steward
     * @return steward specified by id 
     */
    public Steward getSteward(Long id) throws JPAException, IllegalArgumentException;
    
    /**
     * Returns list of all stewards.
     * 
     * @return List of stewards
     */
    public List<Steward> getAllStewards() throws JPAException;
    
    /**
     * Returns List of all flights associated to steward.
     * 
     * @param steward specifies the steward
     * @return List of flights
     */
    public List<Flight> getAllStewardsFlights(Steward steward) throws JPAException, IllegalArgumentException;
}