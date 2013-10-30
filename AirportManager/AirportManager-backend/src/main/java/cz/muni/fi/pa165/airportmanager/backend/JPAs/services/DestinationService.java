/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Samo
 */
public interface DestinationService {
    /*
     * Create new destination
     * @param destination specifies new destination to be created
     */
    public void createDestination(DestinationTO destination) throws DataAccessException;
    /*
     * Update destination
     * @param destination specifies new destination to be updated
     */
    public void updateDestination(DestinationTO destination) throws DataAccessException;
    /*
     * Remove destination
     * @param destination specifies new destination to deleted
     */
    public void removeDestination(DestinationTO destination) throws DataAccessException;
    /*
     * Get destination with specified id
     * @param id specifies id of destination
     * @return DestinationTO witch given id
     */
    public DestinationTO getDestination(Long id) throws DataAccessException;
    /*
     * Returns list of all destinations
     * @return List of all destinations
     */
    public List<DestinationTO> getAllDestinations() throws DataAccessException;
    
    public List<FlightTO> getAllIncomingFlights(DestinationTO destination) throws DataAccessException;
    
    public List<FlightTO> getAllOutcomingFlights(DestinationTO destination) throws DataAccessException;
}
