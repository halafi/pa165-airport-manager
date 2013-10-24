/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Juraj Duráni
 */
public interface FlightService {
    
    void createFlight(Flight flight) throws DataAccessException;
    
    void updateFlight(Flight flight) throws DataAccessException;
    
    void removeFlight(FlightTO flight) throws DataAccessException;
    
    FlightTO getFlight(Long id) throws DataAccessException;
    
    List<FlightTO> getAllFlights() throws DataAccessException;
    
}
