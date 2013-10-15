package cz.muni.fi.pa165.airportmanager.backend.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Samo
 */
@Entity
@Table(name="Flights")
@NamedQueries({
    @NamedQuery(name="Flight.findByOutcoming", query="SELECT f FROM Flight f WHERE f.origin.id = :origin"),
    @NamedQuery(name="Flight.findByIncoming", query="SELECT f FROM Flight f WHERE f.target.id = :target"),
    @NamedQuery(name="Flight.findByAirplane", query="SELECT f FROM Flight f WHERE f.airplane.id = :airplane")
}) 
public class Flight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    @ManyToOne
    private Destination origin;
    @ManyToOne
    private Destination target;
    @ManyToOne
    private Airplane airplane;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Steward> stewardList;
    
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

    public Destination getOrigin() {
        return origin;
    }

    public void setOrigin(Destination origin) {
        this.origin = origin;
    }

    public Destination getTarget() {
        return target;
    }

    public void setTarget(Destination target) {
        this.target = target;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public List<Steward> getStewardList() {
        return stewardList;
    }

    public void setStewardList(List<Steward> stewardList) {
        this.stewardList = stewardList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Flight other = (Flight) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Flight{" + "id=" + id + ", departureTime=" + departureTime + 
                ", arrivalTime=" + arrivalTime + ", origin=" + origin + 
                ", target=" + target + ", airplane=" + airplane + 
                ", stewardList=" + stewardList + '}';
    }
    
}
