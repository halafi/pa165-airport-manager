/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;

/**
 *
 * @author Chorke
 */
public class StewardTO {
    private Long id;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(id == null
                || obj == null
                || !(obj instanceof StewardTO)){
            return false;
        }
        StewardTO s = (StewardTO) obj;
        return s.id == null ? false : s.id.equals(this.id);
    }
    
    public static Steward getStewardEntity(StewardTO steward) throws IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Steward can not be null");
        }
        Steward stew = new Steward();
        stew.setFirstName(new StringBuilder(steward.firstName).toString());
        stew.setLastName(new StringBuilder(steward.lastName).toString());
        stew.setId(new Long(steward.id.longValue()));
        return stew;
    }
    
    public static StewardTO getStewardTransferObject(Steward steward) throws IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Steward can not be null");
        }
        StewardTO stew = new StewardTO();
        stew.firstName = new StringBuilder(steward.getFirstName()).toString();
        stew.lastName = new StringBuilder(steward.getLastName()).toString();
        stew.id = new Long(steward.getId().longValue());
        return stew;
    }
}