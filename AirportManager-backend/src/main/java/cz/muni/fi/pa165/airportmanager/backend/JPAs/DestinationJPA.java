/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs;

import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


/**
 *
 * @author Samo
 */
public class DestinationJPA implements DestinationDAO{

        private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("cz.muni.fi.pa165_AirportManager-backend_jar_1.0-SNAPSHOTPU");
        
        public void createDestination(Destination destination){
            if (destination == null){
                throw new IllegalArgumentException("destination argument is null");
            }
            EntityManager em = emf.createEntityManager();
        
            em.getTransaction().begin();
            em.persist(destination);
            em.getTransaction().commit();
        
            em.close();
        }
        
        public void updateDestination(Destination destination) {
            if(destination == null){
                throw new IllegalArgumentException("destination argument is null");
            }
            
            if (destination.getId() == null) {
            throw new IllegalArgumentException("airplane id is null");
            }
            
            EntityManager em = emf.createEntityManager();
            
            em.getTransaction().begin();
            em.merge(destination);
            em.getTransaction().commit();
            
            em.close();
        }
        
        public void removeDestination(Destination destination) throws JPAException{
            if (destination == null){
                throw new IllegalArgumentException("destination argument is null");
            }
            EntityManager em = emf.createEntityManager();
        
            em.getTransaction().begin();
            Destination destinationFromDB = em.find(Destination.class, destination.getId());
            if (destinationFromDB == null){
                throw new JPAException("destination not in database");
            } else {
                em.remove(destinationFromDB);
                em.getTransaction().commit();
            }
            
            em.close();
        }
        
        public Destination getDestination(Long id) throws JPAException{
            if(id == null){
                throw new IllegalArgumentException("id argument is null");
            }
            EntityManager em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Destination destination = em.find(Destination.class, id);
            em.getTransaction().commit();
            em.close();
            
            if(destination == null){
                throw new JPAException("destination not in database");
            }
            return destination;
        }
        
        public List<Destination> getAllDestinations() throws JPAException{
            EntityManager em = emf.createEntityManager();
            TypedQuery<Destination> query = em.createQuery(
            "SELECT d FROM Destination d", Destination.class);
            List<Destination> destinationsList = query.getResultList();
            
            em.close();
            
            if(destinationsList == null){
                throw new JPAException("no destinations in database");
            }
            return destinationsList;
        }
        
        public List<Flight> getAllIncomingFlights(Destination destination) throws JPAException{
            if(destination == null){
                throw new IllegalArgumentException("destination argument is null");
            }
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<Flight> query = em.createNamedQuery(
            "Flight.findByIncoming", Flight.class);
            query.setParameter("target",destination.getId());
            List<Flight> flightsList = query.getResultList();
            
            em.close();
            
            if(flightsList == null){
                throw new JPAException("no flights found");
            }
            return flightsList;
            
        }
        
        public List<Flight> getAllOutcomingFlights(Destination destination) throws JPAException{
            if(destination == null){
                throw new IllegalArgumentException("destination argument is null");
            }
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<Flight> query = em.createNamedQuery(
            "Flight.findByOutcoming", Flight.class);
            query.setParameter("origin",destination.getId());
            List<Flight> flightsList = query.getResultList();
            
            em.close();
            
            if(flightsList == null){
                throw new JPAException("no flights found");
            }
            return flightsList;
        }
}
