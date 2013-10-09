/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;

/**
 *
 * @author Chorke
 */
public interface FlightDAO {
    
    public void createFlight(Flight flight) throws JPAException, IllegalArgumentException;
    
    public void updateFlight(Flight flight) throws JPAException, IllegalArgumentException;
    
    public void removeFlight(Flight flight) throws JPAException, IllegalArgumentException;
    
    public Flight getFlight(Long id) throws JPAException, IllegalArgumentException;
    
    public List<Flight> getAllFlight() throws JPAException;
}
