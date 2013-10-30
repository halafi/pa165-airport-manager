<<<<<<< HEAD
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
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import java.util.Objects;

/**
 *
 * @author Filip
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
        return "StewardTO{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StewardTO other = (StewardTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
>>>>>>> 311e5fa5269c5ded229f4dc7cf33cf4500c6c52d
