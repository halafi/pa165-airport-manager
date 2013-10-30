/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.ServiceDataAccessException;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Samo
 */
public class DestinationServiceImpl implements DestinationService {
    private DestinationDAO destinationDao;
    
    public void setDestinationDao(DestinationDAO destinationDao){
        this.destinationDao = destinationDao;
    }
    
    @Override
    public void createDestination(DestinationTO destinationTo) throws DataAccessException {
        try{
            Destination destination = EntityDTOTransformer.destinationTOConvert(destinationTo);
            destinationDao.createDestination(destination);
        }catch(Exception e){
            throw new ServiceDataAccessException("Destination creating error"+ e.getMessage());
        }
        
        
    }

    @Override
    public void updateDestination(DestinationTO destinationTo) throws DataAccessException {
        try{
            Destination destination = EntityDTOTransformer.destinationTOConvert(destinationTo);
            destinationDao.updateDestination(destination);
        }catch(Exception e){
            throw new ServiceDataAccessException("Destination updating error"+ e.getMessage());
        }
    }

    @Override
    public void removeDestination(DestinationTO destinationTo) throws DataAccessException {
        try{
            Destination destination = EntityDTOTransformer.destinationTOConvert(destinationTo);
            destinationDao.removeDestination(destination);
        }catch(Exception e){
            throw new ServiceDataAccessException("Destination deleting error"+ e.getMessage());
        }
    }

    @Override
    public DestinationTO getDestination(Long id) throws DataAccessException {
        try{
            Destination destination = destinationDao.getDestination(id);
            return EntityDTOTransformer.destinationConvert(destination);
        }catch(Exception e){
            throw new ServiceDataAccessException("Destination finding error"+ e.getMessage());
        }
    }

    @Override
    public List<DestinationTO> getAllDestinations() throws DataAccessException {
        try{
            List<DestinationTO> destinationsToList = new ArrayList<DestinationTO>();
            List<Destination> destinationsList = new ArrayList<Destination>();
            destinationsList = destinationDao.getAllDestinations();
            for(Destination des : destinationsList){
                DestinationTO desTo = EntityDTOTransformer.destinationConvert(des);
                destinationsToList.add(desTo);
            }
            return destinationsToList;
        }catch(Exception e){
            throw new ServiceDataAccessException("All destinations getting error"+ e.getMessage());
        }
    }

    @Override
    public List<FlightTO> getAllIncomingFlights(DestinationTO destinationTo) throws DataAccessException {
        try{
            List<FlightTO> flightsToList = new ArrayList<FlightTO>();
            List<Flight> flightsList = new ArrayList<Flight>();
            flightsList = destinationDao.getAllIncomingFlights
                    (EntityDTOTransformer.destinationTOConvert(destinationTo));
            for(Flight flight : flightsList){
                FlightTO flightTo = EntityDTOTransformer.flightConvert(flight);
                flightsToList.add(flightTo);
            }
            return flightsToList;
        }catch(Exception e){
            throw new ServiceDataAccessException("All incoming flights retrieving error"+ e.getMessage());
        }
    }

    @Override
    public List<FlightTO> getAllOutcomingFlights(DestinationTO destinationTo) throws DataAccessException {
        try{
            List<FlightTO> flightsToList = new ArrayList<FlightTO>();
            List<Flight> flightsList = new ArrayList<Flight>();
            flightsList = destinationDao.getAllOutcomingFlights
                    (EntityDTOTransformer.destinationTOConvert(destinationTo));
            for(Flight flight : flightsList){
                FlightTO flightTo = EntityDTOTransformer.flightConvert(flight);
                flightsToList.add(flightTo);
            }
            return flightsToList;
        }catch(Exception e){
            throw new ServiceDataAccessException("All outcoming flights retrieving error"+ e.getMessage());
        }
    }    
}
