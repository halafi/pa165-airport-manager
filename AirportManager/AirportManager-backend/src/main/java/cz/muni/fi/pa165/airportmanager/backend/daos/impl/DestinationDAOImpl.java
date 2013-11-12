/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of DestinationDAO
 *
 * @author Samo
 */
@Repository
public class DestinationDAOImpl implements DestinationDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createDestination(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("destination argument is null");
        }
        if (destination.getCity() == null || destination.getCity().isEmpty()) {
            throw new IllegalArgumentException("city argument is null");
        }
        if (destination.getCode() == null || destination.getCode().isEmpty()) {
            throw new IllegalArgumentException("code argument is null");
        }
        if (destination.getCountry() == null || destination.getCountry().isEmpty()) {
            throw new IllegalArgumentException("country argument is null");
        }

        em.persist(destination);
    }

    @Override
    public void updateDestination(Destination destination) throws JPAException {
        if (destination == null) {
            throw new IllegalArgumentException("destination argument is null");
        }

        if (destination.getId() == null) {
            throw new IllegalArgumentException("destination id is null");
        }
        if (destination.getCity() == null || destination.getCity().isEmpty()) {
            throw new IllegalArgumentException("city argument is null");
        }
        if (destination.getCode() == null || destination.getCode().isEmpty()) {
            throw new IllegalArgumentException("code argument is null");
        }
        if (destination.getCountry() == null || destination.getCountry().isEmpty()) {
            throw new IllegalArgumentException("country argument is null");
        }

        Destination destinationFromDB = em.find(Destination.class, destination.getId());
        if (destinationFromDB == null) {
            throw new JPAException("destination not in database");
        } else {
            em.merge(destination);
        }
    }

    @Override
    public void removeDestination(Destination destination) throws JPAException {
        if (destination == null) {
            throw new IllegalArgumentException("destination argument is null");
        }
        if (destination.getId() == null) {
            throw new IllegalArgumentException("destination id is null");
        }
        if (destination.getCity() == null || destination.getCity().isEmpty()) {
            throw new IllegalArgumentException("city argument is null");
        }
        if (destination.getCode() == null || destination.getCode().isEmpty()) {
            throw new IllegalArgumentException("code argument is null");
        }
        if (destination.getCountry() == null || destination.getCountry().isEmpty()) {
            throw new IllegalArgumentException("country argument is null");
        }

        Destination destinationFromDB = em.find(Destination.class, destination.getId());
        if (destinationFromDB == null) {
            throw new JPAException("destination not in database");
        } else {
            em.remove(destinationFromDB);
        }
    }

    @Override
    public Destination getDestination(Long id) throws JPAException {
        if (id == null) {
            throw new IllegalArgumentException("id argument is null");
        }

        Destination destination = em.find(Destination.class, id);
        
        if (destination == null) {
            throw new JPAException("destination not in database");
        }
        return destination;
    }

    @Override
    public List<Destination> getAllDestinations() throws JPAException {
        TypedQuery<Destination> query = em.createQuery(
                "SELECT d FROM Destination d", Destination.class);
        List<Destination> destinationsList = query.getResultList();

        if (destinationsList == null) {
            throw new JPAException("no destinations in database");
        }
        return destinationsList;
    }

    @Override
    public List<Flight> getAllIncomingFlights(Destination destination) throws JPAException {
        if (destination == null) {
            throw new IllegalArgumentException("destination argument is null");
        }
        if (destination.getId() == null) {
            throw new IllegalArgumentException("destination id is null");
        }
        if (destination.getCity() == null || destination.getCity().isEmpty()) {
            throw new IllegalArgumentException("city argument is null");
        }
        if (destination.getCode() == null || destination.getCode().isEmpty()) {
            throw new IllegalArgumentException("code argument is null");
        }
        if (destination.getCountry() == null || destination.getCountry().isEmpty()) {
            throw new IllegalArgumentException("country argument is null");
        }

        Destination destinationFromDB = em.find(Destination.class, destination.getId());
        
        if (destinationFromDB == null) {
            throw new JPAException("destination not in DB");
        }

        TypedQuery<Flight> query = em.createNamedQuery(
                "Flight.findByIncoming", Flight.class);
        query.setParameter("target", destination.getId());
        List<Flight> flightsList = query.getResultList();

        if (flightsList == null) {
            throw new JPAException("no flights found");
        }
        return flightsList;
    }

    @Override
    public List<Flight> getAllOutcomingFlights(Destination destination) throws JPAException {
        if (destination == null) {
            throw new IllegalArgumentException("destination argument is null");
        }
        if (destination.getId() == null) {
            throw new IllegalArgumentException("destination id is null");
        }
        if (destination.getCity() == null || destination.getCity().isEmpty()) {
            throw new IllegalArgumentException("city argument is null");
        }
        if (destination.getCode() == null || destination.getCode().isEmpty()) {
            throw new IllegalArgumentException("code argument is null");
        }
        if (destination.getCountry() == null || destination.getCountry().isEmpty()) {
            throw new IllegalArgumentException("country argument is null");
        }

        Destination destinationFromDB = em.find(Destination.class, destination.getId());
        
        if (destinationFromDB == null) {
            throw new JPAException("destination not in DB");
        }

        TypedQuery<Flight> query = em.createNamedQuery(
                "Flight.findByOutcoming", Flight.class);
        query.setParameter("origin", destination.getId());
        List<Flight> flightsList = query.getResultList();

        if (flightsList == null) {
            throw new JPAException("no flights found");
        }
        return flightsList;
    }
}
