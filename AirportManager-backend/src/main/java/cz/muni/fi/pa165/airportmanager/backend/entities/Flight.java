package cz.muni.fi.pa165.airportmanager.backend.entities;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Filip
 */
public class Flight {
    private Long id;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Destination origin;
    private Destination target;
    private Airplane airplane;
    private List<Steward> stewardList;
    private String flightCode;
}
