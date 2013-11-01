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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.incoming);
        hash = 13 * hash + Objects.hashCode(this.outcoming);
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.code);
        hash = 13 * hash + Objects.hashCode(this.country);
        hash = 13 * hash + Objects.hashCode(this.city);
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
        final Destination other = (Destination) obj;
        if (!Objects.equals(this.incoming, other.incoming)) {
            return false;
        }
        if (!Objects.equals(this.outcoming, other.outcoming)) {
            return false;
        }
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



    @Override
    public String toString() {
        return "Destination{" + "id=" + id + ", code=" + code + ", country=" + country + ", city=" + city + '}';
    }


    
}
