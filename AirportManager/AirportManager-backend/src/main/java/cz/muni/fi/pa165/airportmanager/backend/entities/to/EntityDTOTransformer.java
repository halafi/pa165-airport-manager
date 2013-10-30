package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;

/**
 * Convertor for TO objects into entities.
 *
 * @author Filip
 */
public class EntityDTOTransformer {

    public static FlightTO flightToTO(Flight flight) {
        if (flight == null) {
            throw new NullPointerException("flight is null");
        } else {
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

    public static Airplane AirplaneTOConvert(AirplaneTO airplaneTO) {
        
        if (airplaneTO == null) {
            throw new IllegalArgumentException("airplaneTO is null");
        }

        Airplane airplane = new Airplane();

        airplane.setCapacity(airplaneTO.getCapacity());
        airplane.setId(airplaneTO.getId());
        airplane.setName(airplaneTO.getName());
        airplane.setType(airplaneTO.getType());

        return airplane;
    }

    public static AirplaneTO AirplaneConvert(Airplane airplane) {
        
        if (airplane == null) {
            throw new IllegalArgumentException("airplane is null");
        }

        AirplaneTO airplaneTO = new AirplaneTO();

        airplaneTO.setCapacity(airplane.getCapacity());
        airplaneTO.setId(airplane.getId());
        airplaneTO.setName(airplane.getName());
        airplaneTO.setType(airplane.getType());

        return airplaneTO;
    }
    
    public static Steward getStewardEntity(StewardTO steward) throws IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Steward can not be null");
        }
        Steward stew = new Steward();
        stew.setFirstName(steward.getFirstName());
        stew.setLastName(steward.getLastName());
        stew.setId(steward.getId());
        return stew;
    }
    
    public static StewardTO getStewardTransferObject(Steward steward) throws IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Steward can not be null");
        }
        StewardTO stew = new StewardTO();
        stew.setFirstName(steward.getFirstName());
        stew.setLastName(steward.getLastName());
        stew.setId(steward.getId());
        return stew;
    }
}
