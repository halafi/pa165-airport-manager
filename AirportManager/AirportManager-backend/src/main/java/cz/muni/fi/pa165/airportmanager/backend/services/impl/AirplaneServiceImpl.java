package cz.muni.fi.pa165.airportmanager.backend.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Matus Makovy
 */
@Service
@Transactional
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    private AirplaneDAO airplaneDao;

    public void setAirplaneDao(AirplaneDAO airplaneDao) {
        this.airplaneDao = airplaneDao;
    }

    @Override
    @Transactional
    public void createAirplane(AirplaneTO airplane) throws DataAccessException {
        Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
        airplaneDao.createAirplane(airplaneEntity);
    }

    @Override
    @Transactional
    public void updateAirplane(AirplaneTO airplane) throws DataAccessException {
        Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
        airplaneDao.updateAirplane(airplaneEntity);
    }

    @Override
    @Transactional
    public void removeAirplane(AirplaneTO airplane) throws DataAccessException {
        Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
        airplaneDao.removeAirplane(airplaneEntity);
    }

    @Override
    @Transactional
    public AirplaneTO getAirplane(Long id) throws DataAccessException {
        return EntityDTOTransformer.airplaneConvert(airplaneDao.getAirplane(id));
    }

    @Override
    @Transactional
    public List<AirplaneTO> getAllAirplanes() throws DataAccessException {
        List<AirplaneTO> airplanesTO = new ArrayList<>();
        List<Airplane> airplanes = airplaneDao.getAllAirplanes();
        for (Airplane a : airplanes) {
            AirplaneTO airplaneTO = EntityDTOTransformer.airplaneConvert(a);
            airplanesTO.add(airplaneTO);
        }
        return airplanesTO;
    }

    @Override
    @Transactional
    public List<FlightTO> getAllAirplanesFlights(AirplaneTO airplane) throws DataAccessException {
        List<Flight> flights;
        List<FlightTO> flightsTO = new ArrayList<>();

        Airplane airplaneEntity = EntityDTOTransformer.airplaneTOConvert(airplane);
        flights = airplaneDao.getAllAirplanesFlights(airplaneEntity);

        if (flights.isEmpty()) {
            return flightsTO;
        } else {
            for (Flight f : flights) {
//                FlightTO flightTO = new FlightTO();
//                flightTO.setAirplaneTO(EntityDTOTransformer.airplaneConvert(f.getAirplane()));
//                flightTO.setArrivalTime(f.getArrivalTime());
//                flightTO.setDepartureTime(f.getDepartureTime());
//                flightTO.setId(f.getId());
//                flightTO.setOrigin(EntityDTOTransformer.destinationConvert(f.getOrigin()));
//                flightTO.setTarget(EntityDTOTransformer.destinationConvert(f.getTarget()));
//                List<StewardTO> stewardTOList = new ArrayList<>();
//                for (Steward s : f.getStewardList()) {
//                    StewardTO stewardTO = EntityDTOTransformer.stewardConvert(s);
//                    stewardTOList.add(stewardTO);
//                }
//
//                flightTO.setStewList(stewardTOList);
//                flightsTO.add(flightTO);
                flightsTO.add(EntityDTOTransformer.flightConvert(f));
            }
            return flightsTO;
        }
    }
}
