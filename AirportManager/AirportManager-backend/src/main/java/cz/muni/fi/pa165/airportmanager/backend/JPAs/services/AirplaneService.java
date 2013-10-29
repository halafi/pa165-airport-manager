package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Matus Makovy
 */
public interface AirplaneService {

    public void createAirplane(AirplaneTO airplane) throws DataAccessException;

    public void updateAirplane(AirplaneTO airplane) throws DataAccessException;

    public void removeAirplane(AirplaneTO airplane) throws DataAccessException;

    public AirplaneTO getAirplane(Long id) throws DataAccessException;

    public List<AirplaneTO> getAllAirplanes() throws DataAccessException;

    public List<FlightTO> getAllAirplanesFlights(AirplaneTO airplane) throws DataAccessException;
}
