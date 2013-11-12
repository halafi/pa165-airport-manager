/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.services;

import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.List;

/**
 *
 * @author Juraj Duráni
 */
public interface StewardService {
    
    void createSteward(StewardTO steward);
    
    void updateSteward(StewardTO steward);
    
    void removeSteward(StewardTO steward);
    
    StewardTO findSteward(Long id);
    
    List<StewardTO> findAllStewards();
    
    List<FlightTO> getAllStewardsFlights(StewardTO steward);
}