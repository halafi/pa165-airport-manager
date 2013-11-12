package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of FlightDAO.
 * 
 * @author Filip
 */
@Repository
public class FlightDAOImpl implements FlightDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void createFlight(Flight flight){
        if(flight == null) {
            throw new IllegalArgumentException("Flight is null.");
        } else if(flight.getId() != null) {
            throw new IllegalArgumentException("Flight id is already assigned.");
        } else if(flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
            throw new IllegalArgumentException("Arrival or departure time is null.");
        } else if(flight.getStewardList() == null) {
            throw new IllegalArgumentException("Steward list is null.");
        } else if(flight.getOrigin() == null || flight.getTarget() == null) {
            throw new IllegalArgumentException("Origin or target destination is null.");
        } else if(flight.getAirplane() == null) {
            throw new IllegalArgumentException("Airplane is null.");
        } else if(flight.getDepartureTime().after(flight.getArrivalTime()) || flight.getArrivalTime().before(flight.getDepartureTime())) {
            throw new IllegalArgumentException("Invalid Departure/Arrival time, wrong order.");
        } else {
            em.persist(flight);
        }
    }
    
    @Override
    public void updateFlight(Flight flight){
        if(flight == null) {
            throw new IllegalArgumentException("Flight is null.");
        } else if(flight.getId() == null) {
            throw new IllegalArgumentException("Flight id is not assigned.");
        } else if(flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
            throw new IllegalArgumentException("Arrival or departure time is null.");
        } else if(flight.getStewardList() == null) {
            throw new IllegalArgumentException("Steward list is null.");
        } else if(flight.getOrigin() == null || flight.getTarget() == null) {
            throw new IllegalArgumentException("Origin or target destination is null.");
        } else if(flight.getAirplane() == null) {
            throw new IllegalArgumentException("Airplane is null.");
        } else if(flight.getDepartureTime().after(flight.getArrivalTime()) || flight.getArrivalTime().before(flight.getDepartureTime())) {
            throw new IllegalArgumentException("Invalid Departure/Arrival time, wrong order.");
        } else {
            if(em.find(Flight.class, flight.getId()) == null) {
                throw new JPAException("Flight in database is null.");
            }
            em.merge(flight);
        }
    }
    
    @Override
    public void removeFlight(Flight flight){
        if(flight == null) {
            throw new IllegalArgumentException("Flight flight to be removed is null.");
        } else if(flight.getId() == null) {
            throw new IllegalArgumentException("Flight flight to be removed id is not assigned.");
        } else {
            Flight flightToBeDeleted = em.find(Flight.class, flight.getId());
            if (flightToBeDeleted == null) {
                throw new JPAException("Flight in database is null.");
            }
            em.remove(flightToBeDeleted);
        }
    }
    
    @Override
    public Flight getFlight(Long id){
        if(id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        Flight toReturn = em.find(Flight.class, id);
        if(toReturn == null) {
            throw new JPAException("Flight is not in database.");
        }
        return toReturn;
    }
    
    @Override
    public List<Flight> getAllFlight(){
        Query query = em.createQuery("SELECT p FROM Flight p ");
        List<Flight> allFlights = query.getResultList();
        return allFlights;
    }
}
