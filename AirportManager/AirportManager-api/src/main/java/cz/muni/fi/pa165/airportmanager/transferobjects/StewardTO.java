
package cz.muni.fi.pa165.airportmanager.transferobjects;

import java.util.Objects;


/**
 * Simple transfer object, that represents Steward object.
 * 
 * @author Chorke
 */
public class StewardTO {
    private Long id;
    private String firstName;
    private String lastName;

    /**
     * @return Stewards ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Set stewards id.
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Stewards first name;
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets stewards first name
     * 
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Stewards last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets stewards last name
     * 
     * @param lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        
    }
    
    @Override
    public String toString() {
        return "Steward [" + id + "] " + firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.firstName);
        hash = 73 * hash + Objects.hashCode(this.lastName);
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
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }
}