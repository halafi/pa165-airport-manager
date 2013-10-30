package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;

/**
 * Convertor for TO objects into entities.
 * @author Filip
 */
public class EntityDTOTransformer {
    public static FlightTO flightToTO(Flight flight) {
        if (flight == null)
            throw new NullPointerException("flight is null");
        else {
            FlightTO toReturn = new FlightTO();
            toReturn.setId(flight.getId());
            toReturn.setArrivalTime(flight.getArrivalTime());
            toReturn.setDepartureTime(flight.getDepartureTime());
            //toReturn.setOrigin(flight.getOrigin());
            //toReturn.setTarget(flight.getTarget());
            //toReturn.setStewardList(flight.getStewardList());
            //toReturn.setAirplane(flight.getAirplane());
            return toReturn;
        }
    }
}
