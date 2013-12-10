package cz.muni.fi.pa165.airportmanager.backend.entities;

import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Convertor for TO objects into entities.
 *
 * @author Filip
 */
public class EntityDTOTransformer {

    public static List<FlightTO> flightListConvert(List<Flight> flights) {
        if(flights == null){
            return null;
        }
        List<FlightTO> toReturn = new ArrayList<>(flights.size());
        for(Flight f : flights){
            toReturn.add(flightConvert(f));
        }
        return toReturn;
    }
    
    public static FlightTO flightConvert(Flight flight) {
        if (flight == null) {
            return null;
        } else {
            FlightTO toReturn = new FlightTO();
            toReturn.setId(flight.getId());
            toReturn.setArrivalTime(flight.getArrivalTime());
            toReturn.setDepartureTime(flight.getDepartureTime());
            toReturn.setOrigin(destinationConvert(flight.getOrigin()));
            toReturn.setTarget(destinationConvert(flight.getTarget()));
            List<StewardTO> stewTOs = stewardListConvert(flight.getStewardList());
            toReturn.setStewList(stewTOs);
            toReturn.setAirplaneTO(airplaneConvert(flight.getAirplane()));
            return toReturn;
        }
    }
    
    public static Flight flightTOConvert(FlightTO flightTO) {
        if (flightTO == null) {
            return null;
        }
        Flight toReturn = new Flight();
        toReturn.setId(flightTO.getId());
        toReturn.setArrivalTime(flightTO.getArrivalTime());
        toReturn.setDepartureTime(flightTO.getDepartureTime());
        toReturn.setAirplane(airplaneTOConvert(flightTO.getAirplaneTO()));
        toReturn.setOrigin(destinationTOConvert(flightTO.getOrigin()));
        toReturn.setTarget(destinationTOConvert(flightTO.getTarget()));
        List<Steward> stews = stewardTOListConvert(flightTO.getStewList());
        toReturn.setStewardList(stews);
        return toReturn;
        
    }

    public static Airplane airplaneTOConvert(AirplaneTO airplaneTO) {
        if (airplaneTO == null) {
            return null;
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
            return null;
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
            return null;
        }
        Steward stew = new Steward();
        stew.setFirstName(steward.getFirstName());
        stew.setLastName(steward.getLastName());
        stew.setId(steward.getId());
        return stew;
    }
    
    public static StewardTO stewardConvert(Steward steward) throws IllegalArgumentException{
        if(steward == null){
            return null;
        }
        StewardTO stew = new StewardTO();
        stew.setFirstName(steward.getFirstName());
        stew.setLastName(steward.getLastName());
        stew.setId(steward.getId());
        return stew;
    }
    
    public static List<StewardTO> stewardListConvert(List<Steward> list) throws IllegalArgumentException{
        if(list == null){
            return null;
        }
        List<StewardTO> out = new ArrayList<>(list.size());
        for(Steward s : list){
            out.add(stewardConvert(s));
        }
        return out;
    }
    
    public static List<Steward> stewardTOListConvert(List<StewardTO> list) throws IllegalArgumentException{
        if(list == null){
            return null;
        }
        List<Steward> out = new ArrayList<>(list.size());
        for(StewardTO s : list){
            out.add(stewardTOConvert(s));
        }
        return out;
    }
    
    public static Destination destinationTOConvert(DestinationTO destinationTo){
        if (destinationTo == null){
            return null;
        }
        Destination destination = new Destination();
        destination.setId(destinationTo.getId());
        destination.setCity(destinationTo.getCity());
        destination.setCode(destinationTo.getCode());
        destination.setCountry(destinationTo.getCountry());
        return destination;
    }
    
    public static DestinationTO destinationConvert(Destination destination){
        if (destination == null){
            return null;
        }
        DestinationTO destinationTo = new DestinationTO();
        destinationTo.setId(destination.getId());
        destinationTo.setCity(destination.getCity());
        destinationTo.setCode(destination.getCode());
        destinationTo.setCountry(destination.getCountry());
        return destinationTo;
    } 
}
