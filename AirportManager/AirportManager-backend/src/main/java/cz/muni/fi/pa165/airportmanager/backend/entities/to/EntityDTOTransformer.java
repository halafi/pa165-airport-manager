package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import java.util.ArrayList;
import java.util.List;

/**
 * Convertor for TO objects into entities.
 *
 * @author Filip
 */
public class EntityDTOTransformer {

    public static List<FlightTO> flightListConvert(List<Flight> flights) {
        List<FlightTO> toReturn = new ArrayList<>(flights.size());
        for(Flight f : flights){
            toReturn.add(flightConvert(f));
        }
        return toReturn;
    }
    
    public static FlightTO flightConvert(Flight flight) {
        if (flight == null) {
            throw new NullPointerException("flight is null");
        } else {
            FlightTO toReturn = new FlightTO();
            toReturn.setId(flight.getId());
            toReturn.setArrivalTime(flight.getArrivalTime());
            toReturn.setDepartureTime(flight.getDepartureTime());
            toReturn.setOrigin(destinationConvert(flight.getOrigin()));
            toReturn.setTarget(destinationConvert(flight.getTarget()));
            List<StewardTO> stewTOs = new ArrayList<>(flight.getStewardList().size());
            for(Steward s : flight.getStewardList()){
                stewTOs.add(EntityDTOTransformer.stewardConvert(s));
            }
            toReturn.setStewList(stewTOs);
            toReturn.setAirplaneTO(airplaneConvert(flight.getAirplane()));
            return toReturn;
        }
    }
    
    public static Flight flightTOConvert(FlightTO flightTO) {
        if (flightTO == null) {
            throw new NullPointerException("flightTO is null");
        }
        Flight toReturn = new Flight();
        toReturn.setId(flightTO.getId());
        toReturn.setArrivalTime(flightTO.getArrivalTime());
        toReturn.setDepartureTime(flightTO.getDepartureTime());
        toReturn.setAirplane(airplaneTOConvert(flightTO.getAirplaneTO()));
        toReturn.setOrigin(destinationTOConvert(flightTO.getOrigin()));
        toReturn.setTarget(destinationTOConvert(flightTO.getTarget()));
        List<Steward> stews = new ArrayList<>(flightTO.getStewList().size());
            for(StewardTO s : flightTO.getStewList()){
                stews.add(EntityDTOTransformer.stewardTOConvert(s));
            }
        toReturn.setStewardList(stews);
        return toReturn;
        
    }

    public static Airplane airplaneTOConvert(AirplaneTO airplaneTO) {
        
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

    public static AirplaneTO airplaneConvert(Airplane airplane) {
        
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
    
    public static Steward stewardTOConvert(StewardTO steward) throws IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Steward can not be null");
        }
        Steward stew = new Steward();
        stew.setFirstName(steward.getFirstName());
        stew.setLastName(steward.getLastName());
        stew.setId(steward.getId());
        return stew;
    }
    
    public static StewardTO stewardConvert(Steward steward) throws IllegalArgumentException{
        if(steward == null){
            throw new IllegalArgumentException("Steward can not be null");
        }
        StewardTO stew = new StewardTO();
        stew.setFirstName(steward.getFirstName());
        stew.setLastName(steward.getLastName());
        stew.setId(steward.getId());
        return stew;
    }
    
    public static Destination destinationTOConvert(DestinationTO destinationTo){
        if (destinationTo == null){
            throw new IllegalArgumentException("DestinationTo is null");
        }
        Destination destination = new Destination();
        destination.setId(new Long(destinationTo.getId()));
        destination.setCity(destinationTo.getCity());
        destination.setCode(destinationTo.getCode());
        destination.setCountry(destinationTo.getCountry());
        return destination;
    }
    
    public static DestinationTO destinationConvert(Destination destination){
        if (destination == null){
            throw new IllegalArgumentException("Destination is null");
        }
        DestinationTO destinationTo = new DestinationTO();
        destinationTo.setId(new Long(destination.getId()));
        destinationTo.setCity(destination.getCity());
        destinationTo.setCode(destination.getCode());
        destinationTo.setCountry(destination.getCountry());
        return destinationTo;
    } 
}
