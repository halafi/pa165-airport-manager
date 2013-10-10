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
 * @author Juraj Duráni
 */
public interface FlightDAO {
    
    /**
     * Creates {@link Flight} {@code flight} in DB.
     * 
     * @param flight {@link Flight} to be created
     * @throws IllegalArgumentException when some atributes have illegal states
     * @throws JPAException when an error occurs by creating
     */
    public void createFlight(Flight flight) throws IllegalArgumentException, JPAException;
    
    /**
     * Updates {@link Flight} {@code flight} in DB.
     * 
     * @param flight {@link Flight} to be updated
     * @throws JPAException when {@code flight} does not exist or error occurs by updating
     * @throws IllegalArgumentException when {@code flight} has some illegal atributes
     */
    public void updateFlight(Flight flight) throws JPAException, IllegalArgumentException;
    
    /**
     * Removes {@link Flight} {@code flight} from DB
     * 
     * @param flight {@code flight} to be removed
     * @throws JPAException when {@code flight} does not exist or some error occurs by removing
     * @throws IllegalArgumentException when {@code flight.getId() == null}
     */
    public void removeFlight(Flight flight) throws JPAException, IllegalArgumentException;
    
    /**
     * Finds and return {@link Flight} {@code flight} in DB acording to {@code id}
     * 
     * @param id {@code id} of {@code flight}
     * @return finded {@link Flight} 
     * @throws JPAException when no such {@link Flight} exist or some error occurs
     *      by findig
     * @throws IllegalArgumentException when {@code id == null}
     */
    public Flight getFlight(Long id) throws JPAException, IllegalArgumentException;
    
    /**
     * Finds all flights in DB
     * 
     * @return {@link List} of {@link Flight}{@code s} in DB
     * @throws JPAException when some error occurs by finding
     */
    public List<Flight> getAllFlight() throws JPAException;
}
