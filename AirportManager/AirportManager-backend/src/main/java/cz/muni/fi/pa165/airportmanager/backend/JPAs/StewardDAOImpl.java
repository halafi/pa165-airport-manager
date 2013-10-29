/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.JPAs;

import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juraj Duráni
 */
public class StewardDAOImpl implements StewardDAO{

    private EntityManagerFactory factory;

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }
    
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
        EntityManager man = factory.createEntityManager();
        try{
            man.getTransaction().begin();
            man.persist(steward);
            man.getTransaction().commit();
        } catch (Exception ex){
            if(man.getTransaction().isActive()){
                man.getTransaction().rollback();
            }
            man.close();
            throw new JPAException("Error by creating steward " + steward, ex);
        }
        man.close();
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
        EntityManager man = factory.createEntityManager();
        try{
            man.getTransaction().begin();
            Steward finded = man.find(Steward.class, steward.getId());
            if(finded == null){
                throw new JPAException("Steward does not exist (" + steward + ")");
            }
            man.merge(steward);
            man.getTransaction().commit();
        } catch (Exception ex){
            if(man.getTransaction().isActive()){
                man.getTransaction().rollback();
            }
            man.close();
            throw new JPAException("Error by updating steward " + steward, ex);
        }
        man.close();
    }

    @Override
    public void removeSteward(Steward steward) throws JPAException, IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null");
        }
        if(steward.getId() == null){
            throw new IllegalArgumentException("Stewards ID is null");
        }
        EntityManager man = factory.createEntityManager();
        try{
            man.getTransaction().begin();
            Steward stew = man.find(Steward.class, steward.getId());
            if(stew == null){
                man.getTransaction().rollback();
                throw new JPAException("Steward does not exist (" + steward + ")");
            }
            man.remove(stew);
            man.getTransaction().commit();
        } catch (Exception ex){
            if(man.getTransaction().isActive()){
                man.getTransaction().rollback();
            }
            man.close();
            throw new JPAException("Error by removing steward " + steward, ex);
        }
        man.close();
    }

    @Override
    public Steward getSteward(Long id) throws JPAException, IllegalArgumentException{
        if(id == null){
            throw new IllegalArgumentException("Can not fing stewar (id = null)");
        }
        EntityManager man = factory.createEntityManager();
        Steward stew = null;
        try{
            man.getTransaction().begin();
            stew = man.find(Steward.class, id);
            if(stew == null){
                man.getTransaction().rollback();
                throw new JPAException("Steward does not exist (" + id + ")");
            }
            man.getTransaction().commit();
            man.close();
        } catch(Exception ex){
            if(man.getTransaction().isActive()){
                man.getTransaction().rollback();
            }
            man.close();
            throw new JPAException("Error by finding steward (" + id + ")", ex);
        }
        return stew;
    }

    @Override
    public List<Steward> getAllStewards() throws JPAException{
        EntityManager man = factory.createEntityManager();
        List<Steward> stewards;
        try{
            man.getTransaction().begin();
            stewards = man.createNamedQuery("Steward.findAllStewards", 
                    Steward.class).getResultList();
            man.getTransaction().commit();
            man.close();
            return stewards;
        } catch(Exception ex){
            if(man.getTransaction().isActive()){
                man.getTransaction().rollback();
            }
            man.close();
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
        EntityManager man = factory.createEntityManager();
        List<Flight> flights;
        try{
            man.getTransaction().begin();
            TypedQuery<Flight> q = man.createNamedQuery("Steward.findAllStewardsFlights", 
                    Flight.class);
            q.setParameter("steward", steward);
            flights = q.getResultList();
            man.getTransaction().commit();
            man.close();
            return flights;
        } catch (Exception ex){
            if(man.getTransaction().isActive()){
                man.getTransaction().rollback();
            }
            man.close();
            throw new JPAException("Error by finding all stewards flights (" + steward + ")", ex);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "      " +factory.toString() + "   " 
                + (factory instanceof EntityManagerFactory);
    }
 
}