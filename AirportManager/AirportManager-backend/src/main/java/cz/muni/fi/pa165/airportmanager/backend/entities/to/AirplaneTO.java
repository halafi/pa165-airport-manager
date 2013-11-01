package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Matus Makovy
 */
public class AirplaneTO {
    
    private List<FlightTO> flights;
    private Long id;
    private int capacity;
    private String name;
    private String type;

    public List<FlightTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightTO> flights) {
        this.flights = flights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.flights);
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + this.capacity;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.type);
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
        final AirplaneTO other = (AirplaneTO) obj;
        if (!Objects.equals(this.flights, other.flights)) {
            return false;
        }
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
        return "AirplaneTO{" + "flights=" + flights + ", id=" + id + ", capacity=" + capacity + ", name=" + name + ", type=" + type + '}';
    }
    
    
    
    
}
