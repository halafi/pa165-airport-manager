/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.ServiceDataAccessException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.StewardService;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
public class StewardServiceImpl implements StewardService{

    StewardDAO stewardDao;

    public void setStewardDao(StewardDAO stewardDao) {
        this.stewardDao = stewardDao;
    }
    
    @Override
    public void createSteward(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = StewardTO.getStewardEntity(steward);
            stewardDao.createSteward(stew);
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by creating steward", ex);
        }
    }

    @Override
    public void updateSteward(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = StewardTO.getStewardEntity(steward);
            stewardDao.updateSteward(stew);
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by updating steward", ex);
        }
    }

    @Override
    public void removeSteward(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = StewardTO.getStewardEntity(steward);
            stewardDao.removeSteward(stew);
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by removing steward", ex);
        }
    }

    @Override
    public StewardTO findSteward(Long id) throws DataAccessException {
        try{
            Steward s = stewardDao.getSteward(id);
            return StewardTO.getStewardTransferObject(s);
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by finding steward", ex);
        }
    }

    @Override
    public List<StewardTO> findAllStewards() throws DataAccessException {
        try{
            List<Steward> list = stewardDao.getAllStewards();
            List<StewardTO> out = new ArrayList<>(list.size());
            for(Steward s : list){
                out.add(StewardTO.getStewardTransferObject(s));
            }
            return out;
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by finding steward", ex);
        }
    }

    @Override
    public List<FlightTO> getAllStewardsFlights() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
