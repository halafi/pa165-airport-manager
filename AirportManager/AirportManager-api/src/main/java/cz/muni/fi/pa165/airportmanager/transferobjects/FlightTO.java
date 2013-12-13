package cz.muni.fi.pa165.airportmanager.transferobjects;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Simple data transfer object, that represents Flight object.
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

    /**
     * Get method for airplaneTo.
     * @return AirplanteTo
     */
    public AirplaneTO getAirplaneTO() {
        return airplaneTO;
    }

    /**
     * Set method for airplaneTo.
     * @param airplaneTO airplaneTo to be set
     */
    public void setAirplaneTO(AirplaneTO airplaneTO) {
        this.airplaneTO = airplaneTO;
    }

    /**
     * Get method for steward list.
     * @return stewList list of stewards in this FlightTO
     */
    public List<StewardTO> getStewList() {
        return stewList;
    }

    /**
     * Setter for list of stewards.
     * @param stewList list of stewards to be set
     */
    public void setStewList(List<StewardTO> stewList) {
        this.stewList = stewList;
    }

    /**
     * Get method for id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set method for id
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get method for departureTime
     * @return departureTime
     */
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    /**
     * Set method for departureTime
     * @param departureTime time to be set
     */
    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Get method for arrival time
     * @return arrivalTime
     */
    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Set method for arrivalTime
     * @param arrivalTime time to be set
     */
    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Get method for origin destination
     * @return origin destination
     */
    public DestinationTO getOrigin() {
        return origin;
    }

    /**
     * Set method for origin destination
     * @param origin origin destination to be set
     */
    public void setOrigin(DestinationTO origin) {
        this.origin = origin;
    }

    /**
     * Get method for target destination
     * @return target destination
     */
    public DestinationTO getTarget() {
        return target;
    }

    /**
     * Set method for target destination
     * @param target target destination
     */
    public void setTarget(DestinationTO target) {
        this.target = target;
    }

    /**
     * @return hash code value for the object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.departureTime);
        hash = 67 * hash + Objects.hashCode(this.arrivalTime);
        hash = 67 * hash + Objects.hashCode(this.origin);
        hash = 67 * hash + Objects.hashCode(this.target);
        hash = 67 * hash + Objects.hashCode(this.airplaneTO);
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
        if (!Objects.equals(this.airplaneTO, other.airplaneTO)) {
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
        return "FlightTO{" + "id=" + id + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", origin=" + origin + ", target=" + target + ", stewList=" + stewList + ", airplaneTO=" + airplaneTO + '}';
    }

}