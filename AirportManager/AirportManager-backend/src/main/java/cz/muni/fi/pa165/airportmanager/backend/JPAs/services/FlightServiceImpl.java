package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Filip
 */
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDAO flightDAO;

    public void setFlightDAO(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public void createFlight(FlightTO flightTO) throws ServiceDataAccessException {
        if(flightTO == null) {
            throw new ServiceDataAccessException("flightTO is null.");
        }
        //Flight flight = EntityDTOTransformer.
        
    }

    @Override
    public void updateFlight(FlightTO flightTO) throws ServiceDataAccessException {
        if(flightTO == null) {
            throw new ServiceDataAccessException("flightTO is null.");
        }
    }

    @Override
    public void removeFlight(FlightTO flightTO) throws ServiceDataAccessException {
        if(flightTO == null) {
            throw new ServiceDataAccessException("flightTO is null.");
        }
    }

    @Override
    public FlightTO getFlight(Long id) throws ServiceDataAccessException {
        if(id == null) {
            throw new ServiceDataAccessException("id is null.");
        }
        return null;
    }

    @Override
    public List<FlightTO> getAllFlights() throws ServiceDataAccessException {
        return null;

    }
    
    
    
}
