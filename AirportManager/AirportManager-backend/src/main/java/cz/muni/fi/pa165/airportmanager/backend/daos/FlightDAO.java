package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.daos.impl.AirplaneDaoException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;

/**
 * DAO layer for manipulatig with {@link Flight} entity and database.
 * 
 * @author Juraj Duráni
 */
public interface FlightDAO {
    
    /**
     * Creates {@link Flight} {@code flight} in DB.
     * 
     * @param flight {@link Flight} to be created
     * @throws IllegalArgumentException when some atributes have illegal states
     * @throws AirplaneDaoException when an error occurs by creating
     */
    public void createFlight(Flight flight) throws AirplaneDaoException, IllegalArgumentException;
    
    /**
     * Updates {@link Flight} {@code flight} in DB.
     * 
     * @param flight {@link Flight} to be updated
     * @throws AirplaneDaoException when {@code flight} does not exist or error occurs by updating
     * @throws IllegalArgumentException when {@code flight} has some illegal atributes
     */
    public void updateFlight(Flight flight) throws AirplaneDaoException, IllegalArgumentException;
    
    /**
     * Removes {@link Flight} {@code flight} from DB
     * 
     * @param flight {@code flight} to be removed
     * @throws AirplaneDaoException when {@code flight} does not exist or some error occurs by removing
     * @throws IllegalArgumentException when {@code flight.getId() == null}
     */
    public void removeFlight(Flight flight) throws AirplaneDaoException, IllegalArgumentException;
    
    /**
     * Finds and return {@link Flight} {@code flight} in DB acording to {@code id}
     * 
     * @param id {@code id} of {@code flight}
     * @return finded {@link Flight} 
     * @throws AirplaneDaoException when no such {@link Flight} exist or some error occurs
     *      by findig
     * @throws IllegalArgumentException when {@code id == null}
     */
    public Flight getFlight(Long id) throws AirplaneDaoException, IllegalArgumentException;
    
    /**
     * Finds all flights in DB
     * 
     * @return {@link List} of {@link Flight}{@code s} in DB
     * @throws AirplaneDaoException when some error occurs by finding
     */
    public List<Flight> getAllFlight() throws AirplaneDaoException;
}
