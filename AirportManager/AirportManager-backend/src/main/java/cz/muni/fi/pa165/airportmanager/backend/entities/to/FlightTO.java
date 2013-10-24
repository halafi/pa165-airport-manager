/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import java.sql.Timestamp;

/**
 *
 * @author Chorke
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
}
