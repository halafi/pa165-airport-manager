package cz.muni.fi.pa165.airportmanager.backend.daos;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.exception.DataException;

/**
 * JPA implementation of FlightDAO.
 * 
 * @author Filip
 */
public class FlightDAOImpl {
    
    @PersistenceContext
    private EntityManager em;
    
    public void createFlight(Flight flight) throws JPAException, IllegalArgumentException {
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
        } else {
            em.persist(flight);
        }
    }
    
    public void updateFlight(Flight flight) throws JPAException, IllegalArgumentException {
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
        } else if(em.find(Flight.class, flight.getId()) == null){
            throw new JPAException("Flight in database is null.");
        } else {
            em.merge(flight);
        }
    }
    
    public void removeFlight(Flight flight) throws JPAException, IllegalArgumentException {
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
    
    public Flight getFlight(Long id) throws JPAException, IllegalArgumentException {
        if(id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        if(em.find(Flight.class, id) == null) {
            throw new JPAException("Flight is not in database.");
        }
        return em.find(Flight.class, id);
    }
    
    public List<Flight> getAllFlight() throws JPAException {
        Query query = em.createQuery("SELECT p FROM Flight p ");
        List<Flight> allFlights = query.getResultList();
        return allFlights;
    }
}
