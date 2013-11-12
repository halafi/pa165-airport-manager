package cz.muni.fi.pa165.airportmanager.transferobjects;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * A data transfer object is a simple POJO that only contains the properties
 * required for the client-side representation. The DTO only contains the data
 * that we want to persist and none of the lazy loading or persistence logic
 * added by the JPA instrumentation.
 * 
 * @author Filip
 */
public class FlightTO {
    private Long id;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private DestinationTO origin;
    private DestinationTO target;
    private List<StewardTO> stewList;
    private AirplaneTO airplaneTO;

    public AirplaneTO getAirplaneTO() {
        return airplaneTO;
    }

    public void setAirplaneTO(AirplaneTO airplaneTO) {
        this.airplaneTO = airplaneTO;
    }

    @Override
    public String toString() {
        return "FlightTO{" + "id=" + id + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", origin=" + origin + ", target=" + target + ", stewList=" + stewList + ", airplaneTO=" + airplaneTO + '}';
    }



    public List<StewardTO> getStewList() {
        return stewList;
    }

    public void setStewList(List<StewardTO> stewList) {
        this.stewList = stewList;
    }



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
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.departureTime);
        hash = 67 * hash + Objects.hashCode(this.arrivalTime);
        hash = 67 * hash + Objects.hashCode(this.origin);
        hash = 67 * hash + Objects.hashCode(this.target);
//        hash = 67 * hash + Objects.hashCode(this.stewList);
        hash = 67 * hash + Objects.hashCode(this.airplaneTO);
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
        if (!Objects.equals(this.departureTime, other.departureTime)) {
            return false;
        }
        if (!Objects.equals(this.arrivalTime, other.arrivalTime)) {
            return false;
        }
        if (!Objects.equals(this.origin, other.origin)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
//        if (!Objects.equals(this.stewList, other.stewList)) {
//            return false;
//        }
        if (!Objects.equals(this.airplaneTO, other.airplaneTO)) {
            return false;
        }
        return true;
    }



   

    
}
