package cz.muni.fi.pa165.airportmanager.backend.services;

import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Matus Makovy
 */
public interface AirplaneService {

    /**
     * Creates airplane
     * 
     * @param airplane airplane to be created
     * @throws DataAccessException in case of exception in lower layer
     */
    public void createAirplane(AirplaneTO airplane) throws DataAccessException;

    /**
     * Updates airplane 
     * 
     * @param airplane airplane to be updated
     * @throws DataAccessException in case of exception in lower layer
     */
    public void updateAirplane(AirplaneTO airplane) throws DataAccessException;
    
    /**
     * Removes airplane
     * 
     * @param airplane airplane to be removed
     * @throws DataAccessException in case of exception in lower layer
     */
    public void removeAirplane(AirplaneTO airplane) throws DataAccessException;

    /**
     * Returns airplane according to ID
     * 
     * @param id id of desired airplane
     * @return airplane from DB
     * @throws DataAccessException in case of exception in lower layer
     */
    public AirplaneTO getAirplane(Long id) throws DataAccessException;

    /**
     * Returns all airplanes from DB
     * 
     * @return all airplanes from DB
     * @throws DataAccessException in case of exception in lower layer
     */
    public List<AirplaneTO> getAllAirplanes() throws DataAccessException;

    /**
     * Returns all flights for one airplane
     * 
     * @param airplane airplane, whose flights we are looking for
     * @return List of flights
     * @throws DataAccessException in case of exception in lower layer
     */
    public List<FlightTO> getAllAirplanesFlights(AirplaneTO airplane) throws DataAccessException;
}
