/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.ServiceDataAccessException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.services.StewardService;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chorke
 */
@Service
//@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional(readOnly = true)
public class StewardServiceImpl implements StewardService{

    @Autowired
    private StewardDAO stewardDao;

    public void setStewardDao(StewardDAO stewardDao) {
        this.stewardDao = stewardDao;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void createSteward(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
            stewardDao.createSteward(stew);
            steward.setId(stew.getId());
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by creating steward", ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSteward(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
            stewardDao.updateSteward(stew);
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by updating steward", ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void removeSteward(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
            stewardDao.removeSteward(stew);
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by removing steward", ex);
        }
    }

    @Override
    public StewardTO findSteward(Long id) throws DataAccessException {
        try{
            Steward s = stewardDao.getSteward(id);
            return EntityDTOTransformer.stewardConvert(s);
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
                out.add(EntityDTOTransformer.stewardConvert(s));
            }
            return out;
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by finding all stewards", ex);
        }
    }

    @Override
    public List<FlightTO> getAllStewardsFlights(StewardTO steward) throws DataAccessException {
        try{
            Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
            List<Flight> flights = stewardDao.getAllStewardsFlights(stew);
            List<FlightTO> out = new ArrayList<>(flights.size());
            for(Flight f : flights){
                out.add(EntityDTOTransformer.flightConvert(f));
            }
            return out;
        } catch (IllegalArgumentException | JPAException ex){
            throw new ServiceDataAccessException("Error by finding all stewards flights", ex);
        }
    }
}
