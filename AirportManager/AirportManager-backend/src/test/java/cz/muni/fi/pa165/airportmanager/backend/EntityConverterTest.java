/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend;

import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.DestinationTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.EntityDTOTransformer;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.FlightTO;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Chorke
 */
public class EntityConverterTest {
    
    private static Flight flight;
    private static FlightTO flightTO;
    private static Destination destination;
    private static DestinationTO destinationTO;
    private static Steward steward;
    private static StewardTO stewardTO;
    private static Airplane airplane;
    private static AirplaneTO airplaneTO;
    private static List<Steward> listSteward;
    private static List<StewardTO> listStewardTO;
    private static List<Flight> listFlight;
    
    @BeforeClass
    public static void setUpClass(){
        flight = new Flight();
        flightTO = new FlightTO();
        destination = new Destination();
        destinationTO = new DestinationTO();
        steward = new Steward();
        stewardTO = new StewardTO();
        airplane = new Airplane();
        airplaneTO = new AirplaneTO();
        listSteward = new ArrayList<>();
        listStewardTO = new ArrayList<>();
        listFlight = new ArrayList<>();
        
        steward.setFirstName("first");
        steward.setLastName("last");
        steward.setId(10L);
        
        stewardTO.setFirstName("firstTO");
        stewardTO.setLastName("lastTO");
        stewardTO.setId(100L);
        
        destination.setCity("Poprad");
        destination.setCode("SVK");
        destination.setCountry("Slovakia");
        
        destinationTO.setCity("Brno");
        destinationTO.setCode("CZE");
        destinationTO.setCountry("Czech republic");
        
        airplane.setCapacity(10);
        airplane.setId(10L);
        airplane.setName("airplane");
        airplane.setType("small");
        
        airplaneTO.setCapacity(100);
        airplaneTO.setId(100L);
        airplaneTO.setName("airplaneTO");
        airplaneTO.setType("big");
        
        flight.setAirplane(airplane);
        flight.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        flight.setDepartureTime(new Timestamp(System.currentTimeMillis() + 1000000));
        flight.setId(20L);
        flight.setOrigin(destination);
        flight.setTarget(destination);
        flight.setStewardList(listSteward);
        
        flightTO.setAirplaneTO(airplaneTO);
        flightTO.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        flightTO.setDepartureTime(new Timestamp(System.currentTimeMillis() + 1000000));
        flightTO.setId(20L);
        flightTO.setOrigin(destinationTO);
        flightTO.setTarget(destinationTO);
        flightTO.setStewList(listStewardTO);
        
        listFlight.add(flight);
        listSteward.add(steward);
        listStewardTO.add(stewardTO);
    }
    
    @Test
    public void stew2StewTOTest(){
        assertDeepEquals(steward, EntityDTOTransformer.stewardConvert(steward));
    }
    
    @Test
    public void stewTO2StewTest(){
        assertDeepEquals(EntityDTOTransformer.stewardTOConvert(stewardTO), stewardTO);
    }
    
    @Test
    public void air2airTOTest(){
        assertDeepEquals(airplane, EntityDTOTransformer.airplaneConvert(airplane));
    }
    
    @Test
    public void airTO2AirTest(){
        assertDeepEquals(EntityDTOTransformer.airplaneTOConvert(airplaneTO), airplaneTO);
    }
    
    @Test
    public void des2DesTOTest(){
        assertDeepEquals(destination, EntityDTOTransformer.destinationConvert(destination));
    }
    
    @Test
    public void desTO2DesTest(){
        assertDeepEquals(EntityDTOTransformer.destinationTOConvert(destinationTO), destinationTO);
    }
    
    @Test
    public void flig2FligTOTest(){
        assertDeepEquals(flight, EntityDTOTransformer.flightConvert(flight));
    }
    
    @Test
    public void fligTO2FligTest(){
        assertDeepEquals(EntityDTOTransformer.flightTOConvert(flightTO), flightTO);
    }
    
    @Test
    public void fligList2FlighListTOTest(){
        List<FlightTO> l = new ArrayList<>(1);
        FlightTO f = new FlightTO();
        DestinationTO or = EntityDTOTransformer.destinationConvert(destination);
        AirplaneTO ar = EntityDTOTransformer.airplaneConvert(airplane);
        List<StewardTO> ls = EntityDTOTransformer.stewardListConvert(listSteward);
        
        f.setAirplaneTO(ar);
        f.setArrivalTime(flight.getArrivalTime());
        f.setDepartureTime(flight.getDepartureTime());
        f.setId(flight.getId());
        f.setOrigin(or);
        f.setTarget(or);
        f.setStewList(ls);
        l.add(f);
        assertDeepEqualsListFlight(EntityDTOTransformer.flightListConvert(listFlight), l);
    }
    
    @Test
    public void stewList2StewListTOTest(){
        List<StewardTO> l = new ArrayList<>(1);
        StewardTO s = new StewardTO();
        s.setFirstName(steward.getFirstName());
        s.setLastName(steward.getLastName());
        s.setId(steward.getId());
        l.add(s);
        assertDeepEqualsListStewTO(EntityDTOTransformer.stewardListConvert(listSteward), l);
    }
    
    @Test
    public void stewListTO2StewListTest(){
        List<Steward> l = new ArrayList<>(1);
        Steward s = new Steward();
        s.setFirstName(stewardTO.getFirstName());
        s.setLastName(stewardTO.getLastName());
        s.setId(stewardTO.getId());
        l.add(s);
        assertDeepEqualsListStew(EntityDTOTransformer.stewardTOListConvert(listStewardTO), l);
    }
    
    private void assertDeepEquals(Flight f1, FlightTO f2){
        assertDeepEquals(f1.getAirplane(), f2.getAirplaneTO());
        assertEquals(f1.getArrivalTime(), f2.getArrivalTime());
        assertEquals(f1.getDepartureTime(), f2.getDepartureTime());
        assertEquals(f1.getId(), f2.getId());
        assertDeepEquals(f1.getOrigin(), f2.getOrigin());
        assertDeepEquals(f1.getTarget(), f2.getTarget());
        assertDeepEqualsListStewMixed(f1.getStewardList(), f2.getStewList());
    }
    
    private void assertDeepEquals(FlightTO f1, FlightTO f2){
        assertDeepEquals(f1.getAirplaneTO(), f2.getAirplaneTO());
        assertEquals(f1.getArrivalTime(), f2.getArrivalTime());
        assertEquals(f1.getDepartureTime(), f2.getDepartureTime());
        assertEquals(f1.getId(), f2.getId());
        assertDeepEquals(f1.getOrigin(), f2.getOrigin());
        assertDeepEquals(f1.getTarget(), f2.getTarget());
        assertDeepEqualsListStewTO(f1.getStewList(), f2.getStewList());
    }
    
    private void assertDeepEquals(Airplane a1, AirplaneTO a2){
        assertEquals(a1.getCapacity(), a2.getCapacity());
        assertEquals(a1.getId(), a2.getId());
        assertEquals(a1.getName(), a2.getName());
        assertEquals(a1.getType(), a2.getType());
    }
    
    private void assertDeepEquals(AirplaneTO a1, AirplaneTO a2){
        assertEquals(a1.getCapacity(), a2.getCapacity());
        assertEquals(a1.getId(), a2.getId());
        assertEquals(a1.getName(), a2.getName());
        assertEquals(a1.getType(), a2.getType());
    }
    
    private void assertDeepEquals(Destination d1, DestinationTO d2){
        assertEquals(d1.getCity(), d2.getCity());
        assertEquals(d1.getId(), d2.getId());
        assertEquals(d1.getCode(), d2.getCode());
        assertEquals(d1.getCountry(), d2.getCountry());
    }
    
    private void assertDeepEquals(DestinationTO d1, DestinationTO d2){
        assertEquals(d1.getCity(), d2.getCity());
        assertEquals(d1.getId(), d2.getId());
        assertEquals(d1.getCode(), d2.getCode());
        assertEquals(d1.getCountry(), d2.getCountry());
    }
    
    private void assertDeepEquals(Steward s1, StewardTO s2){
        assertEquals(s1.getFirstName(), s2.getFirstName());
        assertEquals(s1.getId(), s2.getId());
        assertEquals(s1.getLastName(), s2.getLastName());
    }
    
    private void assertDeepEquals(Steward s1, Steward s2){
        assertEquals(s1.getFirstName(), s2.getFirstName());
        assertEquals(s1.getId(), s2.getId());
        assertEquals(s1.getLastName(), s2.getLastName());
    }
    
    private void assertDeepEquals(StewardTO s1, StewardTO s2){
        assertEquals(s1.getFirstName(), s2.getFirstName());
        assertEquals(s1.getId(), s2.getId());
        assertEquals(s1.getLastName(), s2.getLastName());
    }
    
    private void assertDeepEqualsListStewTO(List<StewardTO> stew1, List<StewardTO> stew2){
        if(stew1 == null && stew2 == null){ return; }
        if(stew1 == null && stew2 != null){ fail(); }
        if(stew1 != null && stew2 == null){ fail(); }
        if(stew1.size() != stew2.size()){ fail(); }
        Iterator i1 = stew1.iterator();
        Iterator i2 = stew2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            StewardTO s1 = (StewardTO)i1.next();
            StewardTO s2 = (StewardTO)i2.next();
            assertDeepEquals(s1, s2);
        }
    }
    
    private void assertDeepEqualsListStew(List<Steward> stew1, List<Steward> stew2){
        if(stew1 == null && stew2 == null){ return; }
        if(stew1 == null && stew2 != null){ fail(); }
        if(stew1 != null && stew2 == null){ fail(); }
        if(stew1.size() != stew2.size()){ fail(); }
        Iterator i1 = stew1.iterator();
        Iterator i2 = stew2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            Steward s1 = (Steward)i1.next();
            Steward s2 = (Steward)i2.next();
            assertDeepEquals(s1, s2);
        }
    }
    
    private void assertDeepEqualsListStewMixed(List<Steward> stew1, List<StewardTO> stew2){
        if(stew1 == null && stew2 == null){ return; }
        if(stew1 == null && stew2 != null){ fail(); }
        if(stew1 != null && stew2 == null){ fail(); }
        if(stew1.size() != stew2.size()){ fail(); }
        Iterator i1 = stew1.iterator();
        Iterator i2 = stew2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            Steward s1 = (Steward)i1.next();
            StewardTO s2 = (StewardTO)i2.next();
            assertDeepEquals(s1, s2);
        }
    }
    
    private void assertDeepEqualsListFlight(List<FlightTO> flight1, List<FlightTO> flight2){
        if(flight1 == null && flight2 == null){ return; }
        if(flight1 == null && flight2 != null){ fail(); }
        if(flight1 != null && flight2 == null){ fail(); }
        if(flight1.size() != flight2.size()){ fail(); }
        Iterator i1 = flight1.iterator();
        Iterator i2 = flight2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            FlightTO f1 = (FlightTO)i1.next();
            FlightTO f2 = (FlightTO)i2.next();
            assertDeepEquals(f1, f2);
        }
    }
}