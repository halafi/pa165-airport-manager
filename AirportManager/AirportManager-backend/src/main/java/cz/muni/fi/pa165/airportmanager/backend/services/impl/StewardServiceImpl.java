/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services.impl;

import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.services.FlightService;
import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Juraj Duráni
 */
@Service
@Transactional(readOnly = true)
public class StewardServiceImpl implements StewardService{

    @Autowired
    private StewardDAO stewardDao;
    
    @Autowired
    private FlightDAO flightDao;

    public void setStewardDao(StewardDAO stewardDao) {
        this.stewardDao = stewardDao;
    }
    
    public void setFlightDao(FlightDAO flightDao) {
        this.flightDao = flightDao;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void createSteward(StewardTO steward) throws DataAccessException {
        Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
        stewardDao.createSteward(stew);
        steward.setId(stew.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSteward(StewardTO steward) throws DataAccessException {
        Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
        stewardDao.updateSteward(stew);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeSteward(StewardTO steward) throws DataAccessException {
        Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
        List<Flight> flights = stewardDao.getAllStewardsFlights(stew);
        for(Flight f : flights){
            f.getStewardList().remove(stew);
            flightDao.updateFlight(f);
        }
        stewardDao.removeSteward(stew);
    }

    @Override
    public StewardTO findSteward(Long id) throws DataAccessException {
        Steward s = stewardDao.getSteward(id);
        return EntityDTOTransformer.stewardConvert(s);
    }

    @Override
    public List<StewardTO> findAllStewards() throws DataAccessException {
        List<Steward> list = stewardDao.getAllStewards();
        List<StewardTO> out = new ArrayList<>(list.size());
        for(Steward s : list){
            out.add(EntityDTOTransformer.stewardConvert(s));
        }
        return out;
    }

    @Override
    public List<FlightTO> getAllStewardsFlights(StewardTO steward) throws DataAccessException {
        Steward stew = EntityDTOTransformer.stewardTOConvert(steward);
        List<Flight> flights = stewardDao.getAllStewardsFlights(stew);
        List<FlightTO> out = new ArrayList<>(flights.size());
        for(Flight f : flights){
            out.add(EntityDTOTransformer.flightConvert(f));
        }
        return out;
    }
}
