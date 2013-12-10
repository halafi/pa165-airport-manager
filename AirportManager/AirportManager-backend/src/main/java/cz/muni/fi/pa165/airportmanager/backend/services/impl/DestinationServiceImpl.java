package cz.muni.fi.pa165.airportmanager.backend.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Samo
 */
@Transactional
@Service
public class DestinationServiceImpl implements DestinationService {
    
    @Autowired
    private DestinationDAO destinationDao;
    
    public void setDestinationDao(DestinationDAO destinationDao){
        this.destinationDao = destinationDao;
    }

    @Override
    @Transactional
    public void createDestination(DestinationTO destinationTo) throws DataAccessException {
        Destination destination = EntityDTOTransformer.destinationTOConvert(destinationTo);
        destinationDao.createDestination(destination);
        destinationTo.setId(destination.getId());
    }

    @Override
    @Transactional
    public void updateDestination(DestinationTO destinationTo) throws DataAccessException {
        Destination destination = EntityDTOTransformer.destinationTOConvert(destinationTo);
        destinationDao.updateDestination(destination);
    }

    @Override
    @Transactional
    public void removeDestination(DestinationTO destinationTo) throws DataAccessException {
        Destination destination = EntityDTOTransformer.destinationTOConvert(destinationTo);
        destinationDao.removeDestination(destination);
    }
    
    @Override
    @Transactional//
    public DestinationTO getDestination(Long id) throws DataAccessException {
        Destination destination = destinationDao.getDestination(id);
        return EntityDTOTransformer.destinationConvert(destination);
    }

    @Override
    @Transactional
    public List<DestinationTO> getAllDestinations() throws DataAccessException {
        List<DestinationTO> destinationsToList = new ArrayList<>();
        List<Destination> destinationsList;
        destinationsList = destinationDao.getAllDestinations();
        for(Destination des : destinationsList){
            DestinationTO desTo = EntityDTOTransformer.destinationConvert(des);
            destinationsToList.add(desTo);
        }
        return destinationsToList;
    }

    @Override
    @Transactional
    public List<FlightTO> getAllIncomingFlights(DestinationTO destinationTo) throws DataAccessException {
        List<FlightTO> flightsToList = new ArrayList<>();
        List<Flight> flightsList;
        flightsList = destinationDao.getAllIncomingFlights
                (EntityDTOTransformer.destinationTOConvert(destinationTo));
        for(Flight flight : flightsList){
            FlightTO flightTo = EntityDTOTransformer.flightConvert(flight);
            flightsToList.add(flightTo);
        }
        return flightsToList;
    }

    @Override
    @Transactional
    public List<FlightTO> getAllOutcomingFlights(DestinationTO destinationTo) throws DataAccessException {
        List<FlightTO> flightsToList = new ArrayList<>();
        List<Flight> flightsList;
        flightsList = destinationDao.getAllOutcomingFlights
                (EntityDTOTransformer.destinationTOConvert(destinationTo));
        for(Flight flight : flightsList){
            FlightTO flightTo = EntityDTOTransformer.flightConvert(flight);
            flightsToList.add(flightTo);
        }
        return flightsToList;
    }    
}
