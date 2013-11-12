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
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juraj Duráni
 */
@Repository
public class StewardDAOImpl implements StewardDAO{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void createSteward(Steward steward){
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
        try{
            manager.persist(steward);
        } catch (Exception ex){
            throw new JPAException("Error by creating steward " + steward, ex);
        }
    }

    @Override
    public void updateSteward(Steward steward){
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
        try{
            Steward finded = manager.find(Steward.class, steward.getId());
            if(finded == null){
                throw new JPAException("Steward does not exist (" + steward + ")");
            }
            manager.merge(steward);
        } catch (Exception ex){
            throw new JPAException("Error by updating steward " + steward, ex);
        }
    }

    @Override
    public void removeSteward(Steward steward){
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null");
        }
        if(steward.getId() == null){
            throw new IllegalArgumentException("Stewards ID is null");
        }
        try{
            Steward stew = manager.find(Steward.class, steward.getId());
            if(stew == null){
                throw new JPAException("Steward does not exist (" + steward + ")");
            }
            manager.remove(stew);
        } catch (Exception ex){
            throw new JPAException("Error by removing steward " + steward, ex);
        }
    }

    @Override
    public Steward getSteward(Long id){
        if(id == null){
            throw new IllegalArgumentException("Can not fing stewar (id = null)");
        }
        try{
            Steward stew = manager.find(Steward.class, id);
            if(stew == null){
                throw new JPAException("Steward does not exist (" + id + ")");
            }
            return stew;
        } catch(Exception ex){
            throw new JPAException("Error by finding steward (" + id + ")", ex);
        }
    }

    @Override
    public List<Steward> getAllStewards(){
        List<Steward> stewards;
        try{
            stewards = manager.createNamedQuery("Steward.findAllStewards", 
                    Steward.class).getResultList();
            return stewards;
        } catch(Exception ex){
            throw new JPAException("Error by finding all stewards.",ex);
        }
    }

    @Override
    public List<Flight> getAllStewardsFlights(Steward steward){
        if(steward == null){
            throw new IllegalArgumentException("Stewards is null");
        }
        if(steward.getId() == null){
            throw new IllegalArgumentException("Stewards ID is null");
        }
        List<Flight> flights;
        try{
            TypedQuery<Flight> q = manager.createNamedQuery("Steward.findAllStewardsFlights", 
                    Flight.class);
            q.setParameter("steward", steward);
            flights = q.getResultList();
            return flights;
        } catch (Exception ex){
            throw new JPAException("Error by finding all stewards flights (" + steward + ")", ex);
        }
    }
}