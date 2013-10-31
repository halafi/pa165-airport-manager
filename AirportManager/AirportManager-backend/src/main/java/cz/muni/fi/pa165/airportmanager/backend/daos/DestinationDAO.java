package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;

/**
 *
 * @author Matus Makovy
 */
public interface DestinationDAO {
    
    /**
     * Creates new destination.
     * 
     * @param destination specifies the destination to be created
     */
    public void createDestination(Destination destination);
    
    /**
     * Updates destination.
     * 
     * @param destination specifies new destination
     */
    public void updateDestination(Destination destination) throws JPAException;
    
    /**
     * Removes destination.
     * 
     * @param destination specifies destination to be removed
     */
    public void removeDestination(Destination destination) throws JPAException;
    
    /**
     * Returns all destinations.
     * 
     * @return List of destinations
     */
    public List<Destination> getAllDestinations() throws JPAException;
    
    /**
     * Returns one destination according to id.
     * 
     * @param id specifies id of destination
     * @return desired destination
     */
    public Destination getDestination(Long id) throws JPAException;
    
    /**
     * Returns all flights that have this destination set as target.
     * 
     * @param destination specifies target destination
     * @return List of flights
     */
    public List<Flight> getAllIncomingFlights(Destination destination) throws JPAException;
    
    /**
     * Returns all flights that have this destination set as origin.
     * 
     * @param destination specifies origin destination
     * @return List of flights
     */
    public List<Flight> getAllOutcomingFlights(Destination destination) throws JPAException;       
}
