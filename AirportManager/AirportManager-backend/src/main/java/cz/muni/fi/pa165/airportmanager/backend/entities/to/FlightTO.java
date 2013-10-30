package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Filip
 */
public class FlightTO {
    private Long id;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private DestinationTO origin;
    private DestinationTO target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public DestinationTO getOrigin() {
        return origin;
    }

    public void setOrigin(DestinationTO origin) {
        this.origin = origin;
    }

    public DestinationTO getTarget() {
        return target;
    }

    public void setTarget(DestinationTO target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final FlightTO other = (FlightTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FlightTO{" + "id=" + id + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", origin=" + origin + ", target=" + target + '}';
    }

    
}
