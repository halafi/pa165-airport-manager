package cz.muni.fi.pa165.airportmanager.services;

import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.List;

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
    public void createAirplane(AirplaneTO airplane);

    /**
     * Updates airplane 
     * 
     * @param airplane airplane to be updated
     * @throws DataAccessException in case of exception in lower layer
     */
    public void updateAirplane(AirplaneTO airplane);
    
    /**
     * Removes airplane
     * 
     * @param airplane airplane to be removed
     * @throws DataAccessException in case of exception in lower layer
     */
    public void removeAirplane(AirplaneTO airplane);

    /**
     * Returns airplane according to ID
     * 
     * @param id id of desired airplane
     * @return airplane from DB
     * @throws DataAccessException in case of exception in lower layer
     */
    public AirplaneTO getAirplane(Long id);

    /**
     * Returns all airplanes from DB
     * 
     * @return all airplanes from DB
     * @throws DataAccessException in case of exception in lower layer
     */
    public List<AirplaneTO> getAllAirplanes();

    /**
     * Returns all flights for one airplane
     * 
     * @param airplane airplane, whose flights we are looking for
     * @return List of flights
     * @throws DataAccessException in case of exception in lower layer
     */
    public List<FlightTO> getAllAirplanesFlights(AirplaneTO airplane);
}
