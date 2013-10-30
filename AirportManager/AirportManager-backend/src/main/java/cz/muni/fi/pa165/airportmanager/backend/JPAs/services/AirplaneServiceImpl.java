package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Matus Makovy
 */
public class AirplaneServiceImpl implements AirplaneService{
    
    private AirplaneDAO airplaneDao;

    public void setAirplaneDao(AirplaneDAO airplaneDao) {
        this.airplaneDao = airplaneDao;
    }

    @Override
    @Transactional
    public void createAirplane(AirplaneTO airplane) throws DataAccessException {
        
        Airplane airplaneEntity = EntityDTOTransformer.AirplaneTOConvert(airplane);

        airplaneDao.createAirplane(airplaneEntity);
    }

    @Override
    public void updateAirplane(AirplaneTO airplane) throws DataAccessException {
        Airplane airplaneEntity = EntityDTOTransformer.AirplaneTOConvert(airplane);

        airplaneDao.updateAirplane(airplaneEntity);
    }

    @Override
    public void removeAirplane(AirplaneTO airplane) throws DataAccessException {
        
        Airplane airplaneEntity = EntityDTOTransformer.AirplaneTOConvert(airplane);
        
        try {
            airplaneDao.removeAirplane(airplaneEntity);
        } catch (JPAException ex) {
            throw new DataRetrievalFailureException(ex.getMessage());
        }
    }

    @Override
    public AirplaneTO getAirplane(Long id) throws DataAccessException {
        AirplaneTO airplaneTO;
        
        try {
            airplaneTO =  EntityDTOTransformer.AirplaneConvert(airplaneDao.getAirplane(id));
        } catch (JPAException ex) {
            throw new DataRetrievalFailureException(ex.getMessage());
        }
        
        return airplaneTO;
    }

    @Override
    public List<AirplaneTO> getAllAirplanes() throws DataAccessException {
        
        List<AirplaneTO> airplanesTO = new ArrayList<>();
        List<Airplane> airplanes = airplaneDao.getAllAirplanes();
        
        for(Airplane a : airplanes) {
            AirplaneTO airplaneTO = EntityDTOTransformer.AirplaneConvert(a);
            airplanesTO.add(airplaneTO);
        }
        
        return airplanesTO;
    }

    @Override
    public List<FlightTO> getAllAirplanesFlights(AirplaneTO airplane) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
