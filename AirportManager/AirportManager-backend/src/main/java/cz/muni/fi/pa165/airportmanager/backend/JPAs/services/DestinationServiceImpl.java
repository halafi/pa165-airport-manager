/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services;

import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
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
    public void createDestination(DestinationTO destination) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateDestination(DestinationTO destination) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeDestination(DestinationTO destination) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DestinationTO getDestination(Long id) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DestinationTO> getAllDestinations() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
