package cz.muni.fi.pa165.airportmanager.backend.JPAs;

import cz.muni.fi.pa165.airportmanager.MainClass;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Matus Makovy
 */
public class AirplaneDAOImpl implements AirplaneDAO {
    
//    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("cz.muni.fi.pa165_AirportManager-backend_jar_1.0-SNAPSHOTPU");
    private static EntityManagerFactory emf = MainClass.EM_FACTORY;
    
    public void createAirplane(Airplane airplane) {
        
        if (airplane == null) {
            throw new IllegalArgumentException("Airplane argument is null");
        } else if (airplane.getName() == null) {
            throw new IllegalArgumentException("Airplane name wasn't set");
        } else if (airplane.getType() == null) {
            throw new IllegalArgumentException("Airplane type wasn't set");
        } else if (airplane.getCapacity() <= 0) {
            throw new IllegalArgumentException("Airplane capacity must be greater than 0");
        }
         
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(airplane);
        em.getTransaction().commit();
        
        em.close();
    }
    
    public void updateAirplane(Airplane airplane) {
        
        if (airplane == null) {
            throw new IllegalArgumentException("Airplane argument is null");
        } else if (airplane.getName() == null) {
            throw new IllegalArgumentException("Airplane name wasn't set");
        } else if (airplane.getType() == null) {
            throw new IllegalArgumentException("Airplane type wasn't set");
        } else if (airplane.getCapacity() <= 0) {
            throw new IllegalArgumentException("Airplane capacity must be greater than 0");
        }
        
        if (airplane.getId() == null) {
            throw new IllegalArgumentException("airplane id is null");
        }
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(airplane);
        em.getTransaction().commit();
        
        em.close();
    }
    
    public void removeAirplane(Airplane airplane) throws JPAException{
        
        if (airplane == null) {
            throw new IllegalArgumentException("airplane argument is null");
        }
        
        if (airplane.getId() == null) {
            throw new IllegalArgumentException("airplane id is null");
        }
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Airplane airplaneFromDB = em.find(Airplane.class, airplane.getId());
        
        if (airplaneFromDB == null) {
            throw new JPAException("Airplane isn't in database");
        }
        
        em.remove(airplaneFromDB);
        em.getTransaction().commit();
        
        em.close();
    }
    
    public Airplane getAirplane(Long id) throws JPAException{
        
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Airplane airplane = em.find(Airplane.class, id);
        em.getTransaction().commit();
        em.close(); 
        
        if (airplane == null){
            throw new JPAException("Airplane with given id isn't in database");
        } else {
            return airplane;
        }
        
    }
    
    public List<Airplane> getAllAirplanes() {
        
        EntityManager em = emf.createEntityManager();
        
        Query allAirplanes = em.createNamedQuery("Airplane.findAllAirplanes");
        
        List<Airplane> airplanes = allAirplanes.getResultList();
        
        return airplanes;
    }
    
    public List<Flight> getAllAirplanesFlights(Airplane airplane) {
        
        if (airplane == null) {
            throw new IllegalArgumentException("airplane arugument is null");
        }
        
        EntityManager em = emf.createEntityManager();
        
        Query flightsQuery = em.createNamedQuery("Flight.findByAirplane");
        flightsQuery.setParameter("airplane", airplane);
        
        List<Flight> flights = flightsQuery.getResultList();
        
        return flights;
    }
}
