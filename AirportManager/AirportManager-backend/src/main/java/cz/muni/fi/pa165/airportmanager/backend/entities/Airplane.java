package cz.muni.fi.pa165.airportmanager.backend.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity, thath represents Aiplane object. Has its ID, name, type and nonnegative 
 * capacity.
 * 
 * @author Juraj Duráni
 */
@Entity
@Table(name="Airplanes")
@NamedQuery(
            name="Airplane.findAllAirplanes",
            query="SELECT a FROM Airplane a"
    )
public class Airplane implements Serializable {
    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private List<Flight> flights;
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int capacity;
    
    @Column(length = 50)
    private String name;
    
    @Column(length = 50)
    private String type;
    
    /**
     * @return Airplanes ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets airplanes id
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Capacity of airplane
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity of airplane.
     * 
     * @param capacity 
     * @throws IllegalArgumentException if capacity is negative.
     */
    public void setCapacity(int capacity) throws IllegalArgumentException{
        if(capacity < 0){
            throw new IllegalArgumentException("Capacity is negative.");
        }
        this.capacity = capacity;
    }

    /**
     * @return Name of airplane.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of airplane.
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Type of airplane.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type of airplane.
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns airplanes flights.
     * 
     * @return list of airplanes flights
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets airplanes flights.
     * @param flights 
     */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + this.capacity;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.type);
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
        final Airplane other = (Airplane) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.capacity != other.capacity) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Airplane {" + id + "} "
                + "[name = " + name + ", type = " + type + ", capacity = " + capacity + "]";
    }
}
