package cz.muni.fi.pa165.airportmanager.services;

import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.List;

/**
 * service layer that uses DAO layer
 * 
 * @author Filip
 */
public interface FlightService {
    
    /**
     * Creates Flight in DB.
     * 
     * @param flightTO flightTO to be created
     * @throws ServiceDataAccessException
     */
    void createFlight(FlightTO flightTO);
    
    /**
     * Updates Flight in DB.
     * 
     * @param flightTO flightTO to be removed
     * @throws ServiceDataAccessException
     */
    void updateFlight(FlightTO flightTO);
    
    /**
     * Removes Flight from DB
     * 
     * @param flightTO flightTO to be removed
     * @throws ServiceDataAccessException
     */
    void removeFlight(FlightTO flightTO);
    
    /**
     * Finds and return flightTO in DB according to id
     * 
     * @param id id of flightTO
     * @return found flight
     * @throws ServiceDataAccessException
     */
    FlightTO getFlight(Long id);
    
    /**
     * Finds all flights in DB
     * 
     * @return List of FlightTOs in DB
     * @throws ServiceDataAccessException
     */
    List<FlightTO> getAllFlights();
    
}
