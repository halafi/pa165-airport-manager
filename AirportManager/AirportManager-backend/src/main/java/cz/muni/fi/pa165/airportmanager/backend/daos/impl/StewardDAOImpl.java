/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juraj Duráni
 */
@Component
public class StewardDAOImpl implements StewardDAO{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void createSteward(Steward steward) throws  JPAException, IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null.");
        }
        if(steward.getId() != null){
            throw new IllegalArgumentException("Stewards ID already set.");
        }
        if(steward.getFirstName() == null || steward.getFirstName().isEmpty()){
            throw new IllegalArgumentException("Stewards first name is null or empty");
        }
        if(steward.getLastName() == null || steward.getLastName().isEmpty()){
            throw new IllegalArgumentException("Stewards last name is null or empty");
        }
//        EntityManager manager = factory.createEntityManager();
        try{
//            manager.getTransaction().begin();
            manager.persist(steward);
//            manager.getTransaction().commit();
        } catch (Exception ex){
//            if(manager.getTransaction().isActive()){
//                manager.getTransaction().rollback();
//            }
//            manager.close();
            throw new JPAException("Error by creating steward " + steward, ex);
        }
//        manager.close();
    }

    @Override
    public void updateSteward(Steward steward) throws JPAException, IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null.");
        }
        if(steward.getId() == null){
            throw new IllegalArgumentException("Stewards ID is null.");
        }
        if(steward.getFirstName() == null || steward.getFirstName().isEmpty()){
            throw new IllegalArgumentException("Stewards first name is null or empty");
        }
        if(steward.getLastName() == null || steward.getLastName().isEmpty()){
            throw new IllegalArgumentException("Stewards last name is null or empty");
        }
//        EntityManager manager = factory.createEntityManager();
//        manager.clear();
        try{
//            manager.getTransaction().begin();
            Steward finded = manager.find(Steward.class, steward.getId());
            if(finded == null){
                throw new JPAException("Steward does not exist (" + steward + ")");
            }
            manager.merge(steward);
//            manager.getTransaction().commit();
        } catch (Exception ex){
//            if(manager.getTransaction().isActive()){
//                manager.getTransaction().rollback();
//            }
//            manager.close();
            throw new JPAException("Error by updating steward " + steward, ex);
        }
//        manager.close();
    }

    @Override
    public void removeSteward(Steward steward) throws JPAException, IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null");
        }
        if(steward.getId() == null){
            throw new IllegalArgumentException("Stewards ID is null");
        }
//        manager.clear();
//        EntityManager manager = factory.createEntityManager();
        try{
//            manager.getTransaction().begin();
            Steward stew = manager.find(Steward.class, steward.getId());
            if(stew == null){
//                manager.getTransaction().rollback();
                throw new JPAException("Steward does not exist (" + steward + ")");
            }
            manager.remove(stew);
//            manager.getTransaction().commit();
        } catch (Exception ex){
//            if(manager.getTransaction().isActive()){
//                manager.getTransaction().rollback();
//            }
//            manager.close();
            throw new JPAException("Error by removing steward " + steward, ex);
        }
//        manager.close();
    }

    @Override
    public Steward getSteward(Long id) throws JPAException, IllegalArgumentException{
        if(id == null){
            throw new IllegalArgumentException("Can not fing stewar (id = null)");
        }
//        EntityManager manager = factory.createEntityManager();
//        manager.clear();
        try{
//            manager.getTransaction().begin();
            Steward stew = manager.find(Steward.class, id);
            if(stew == null){
//                manager.getTransaction().rollback();
                throw new JPAException("Steward does not exist (" + id + ")");
            }
//            manager.getTransaction().commit();
//            manager.close();
            return stew;
        } catch(Exception ex){
//            if(manager.getTransaction().isActive()){
//                manager.getTransaction().rollback();
//            }
//            manager.close();
            throw new JPAException("Error by finding steward (" + id + ")", ex);
        }
//        return stew;
    }

    @Override
    public List<Steward> getAllStewards() throws JPAException{
//        EntityManager manager = factory.createEntityManager();
//        manager.clear();
        List<Steward> stewards;
        try{
//            manager.getTransaction().begin();
            stewards = manager.createNamedQuery("Steward.findAllStewards", 
                    Steward.class).getResultList();
//            manager.getTransaction().commit();
//            manager.close();
            return stewards;
        } catch(Exception ex){
//            if(manager.getTransaction().isActive()){
//                manager.getTransaction().rollback();
//            }
//            manager.close();
            throw new JPAException("Error by finding all stewards.",ex);
        }
    }

    @Override
    public List<Flight> getAllStewardsFlights(Steward steward) throws JPAException, IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null");
        }
        if(steward.getId() == null){
            throw new IllegalArgumentException("Stewards ID is null");
        }
//        EntityManager manager = factory.createEntityManager();
//        manager.clear();
        List<Flight> flights;
        try{
//            manager.getTransaction().begin();
            TypedQuery<Flight> q = manager.createNamedQuery("Steward.findAllStewardsFlights", 
                    Flight.class);
            q.setParameter("steward", steward);
            flights = q.getResultList();
//            manager.getTransaction().commit();
//            manager.close();
            return flights;
        } catch (Exception ex){
//            if(manager.getTransaction().isActive()){
//                manager.getTransaction().rollback();
//            }
//            manager.close();
            throw new JPAException("Error by finding all stewards flights (" + steward + ")", ex);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
 
}