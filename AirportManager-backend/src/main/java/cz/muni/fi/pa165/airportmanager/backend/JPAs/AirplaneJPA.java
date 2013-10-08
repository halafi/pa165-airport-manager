package cz.muni.fi.pa165.airportmanager.backend.JPAs;

import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Matus Makovy
 */
public class AirplaneJPA implements AirplaneDAO {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("cz.muni.fi.pa165_AirportManager-backend_jar_1.0-SNAPSHOTPU");
    
    public void createAirplane(Airplane airplane) {
        
        if (airplane == null) {
            throw new IllegalArgumentException("airplane argument is null");
        }
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(airplane);
        em.getTransaction().commit();
        
        em.close();
    }
    
    public void updateAirplane(Airplane airplane) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void removeAirplane(Airplane airplane) throws JPAException{
        
        if (airplane == null) {
            throw new IllegalArgumentException("airplane argument is null");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Flight> getAllAirplanesFlights(Airplane airplane) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
