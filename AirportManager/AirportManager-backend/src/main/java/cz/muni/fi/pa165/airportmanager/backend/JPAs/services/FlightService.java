
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Filip
 */
public interface FlightService {
    
    void createFlight(Flight flight) throws DataAccessException;
    
    void updateFlight(Flight flight) throws DataAccessException;
    
    void removeFlight(FlightTO flight) throws DataAccessException;
    
    FlightTO getFlight(Long id) throws DataAccessException;
    
    List<FlightTO> getAllFlights() throws DataAccessException;
    
}
