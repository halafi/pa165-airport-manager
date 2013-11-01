package cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.ServiceDataAccessException;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Matus Makovy
 */

public class AirplaneServiceImpl implements AirplaneService {

    private AirplaneDAO airplaneDao;

    public void setAirplaneDao(AirplaneDAO airplaneDao) {
        this.airplaneDao = airplaneDao;
    }

    @Override
    //@Transactional
    public void createAirplane(AirplaneTO airplane) throws DataAccessException {
        
        //SAMO DID THIS
        //Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
        
        try {
            //SAMO DID THIS
            Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
            airplaneDao.createAirplane(airplaneEntity);
        } catch (Exception ex) {
            throw new ServiceDataAccessException(ex.getMessage());
        }
    }

    @Override
    //@Transactional
    public void updateAirplane(AirplaneTO airplane) throws DataAccessException {

        //SAMO DID THIS
        //Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);

        try {
            //SAMO DID THIS
            Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
            airplaneDao.updateAirplane(airplaneEntity);
        } catch (Exception ex) {
            throw new ServiceDataAccessException(ex.getMessage());
        }

    }

    @Override
    //@Transactional
    public void removeAirplane(AirplaneTO airplane) throws DataAccessException {

        //SAMO DID THIS
        //Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);

        try {
            //SAMO DID THIS
            Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
            airplaneDao.removeAirplane(airplaneEntity);
        } catch (Exception ex) {
            throw new ServiceDataAccessException(ex.getMessage());
        }
    }

    @Override
    //@Transactional
    public AirplaneTO getAirplane(Long id) throws DataAccessException {
        AirplaneTO airplaneTO;

        try {
            airplaneTO = EntityDTOTransformer.airplaneConvert(airplaneDao.getAirplane(id));
        } catch (Exception ex) {
            throw new ServiceDataAccessException(ex.getMessage());
        }

        return airplaneTO;
    }

    @Override
    //@Transactional
    public List<AirplaneTO> getAllAirplanes() throws DataAccessException {

        List<AirplaneTO> airplanesTO = new ArrayList<>();
        List<Airplane> airplanes = new ArrayList<>();

        try {
            airplanes = airplaneDao.getAllAirplanes();
        } catch (Exception ex) {
            throw new ServiceDataAccessException(ex.getMessage());
        }

        for (Airplane a : airplanes) {
            AirplaneTO airplaneTO = EntityDTOTransformer.airplaneConvert(a);
            airplanesTO.add(airplaneTO);
        }

        return airplanesTO;
    }

    @Override
    //@Transactional
    public List<FlightTO> getAllAirplanesFlights(AirplaneTO airplane) throws DataAccessException {

        List<Flight> flights = new ArrayList<>();
        List<FlightTO> flightsTO = new ArrayList<>();

        //SAMO DID THIS
        //Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);

        try {
            //SAMO DID THIS
            Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
            flights = airplaneDao.getAllAirplanesFlights(airplaneEntity);
        } catch (Exception ex) {
            throw new ServiceDataAccessException(ex.getMessage());
        }

        if (flights.isEmpty()) {
            return flightsTO;
        } else {
            for (Flight f : flights) {
                FlightTO flightTO = new FlightTO();
                flightTO.setAirplaneTO(EntityDTOTransformer.airplaneConvert(f.getAirplane()));
                flightTO.setArrivalTime(f.getArrivalTime());
                flightTO.setDepartureTime(f.getDepartureTime());
                flightTO.setId(f.getId());
                flightTO.setOrigin(EntityDTOTransformer.destinationConvert(f.getOrigin()));
                flightTO.setTarget(EntityDTOTransformer.destinationConvert(f.getTarget()));
                List<StewardTO> stewardTOList = new ArrayList<>();
                for (Steward s : f.getStewardList()) {
                    StewardTO stewardTO = EntityDTOTransformer.stewardConvert(s);
                    stewardTOList.add(stewardTO);
                }

                flightTO.setStewList(stewardTOList);
                flightsTO.add(flightTO);

            }
            return flightsTO;
        }
    }
}
