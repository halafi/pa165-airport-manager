package cz.muni.fi.pa165.airportmanager.backend.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity representing destination.
 *
 * @author Filip
 */
@Entity
@Table(name="Destinations")
public class Destination implements Serializable {
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL)
    private List<Flight> incoming;
    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    private List<Flight> outcoming;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String country;
    private String city;
    
    /**
     * Get method for id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set method for id.
     * @param id id to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get method for code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set method for code.
     * @param code code to be set
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Get method for country.
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set method for country.
     * @param country country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get method for city.
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set method for city.
     * @param city city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return hash code value for the object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.code);
        hash = 13 * hash + Objects.hashCode(this.country);
        hash = 13 * hash + Objects.hashCode(this.city);
        return hash;
    }

    /**
     * Check whether two instances of this class equals or not.
     * 
     * @param obj object to check equality for
     * @return true if equals, else otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Destination other = (Destination) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string describing instance of this class.
     * @return string describing instance of this class.
     */
    @Override
    public String toString() {
        return "Destination{" + "id=" + id + ", code=" + code + ", country=" + country + ", city=" + city + '}';
    }
}
