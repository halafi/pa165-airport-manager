/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.services.FlightService;
import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 *
 * @author Chorke
 */
@UrlBinding("/flig/{$event}/{flight.id}")
public class FligActionBean extends BaseActionBean {

    @SpringBean
    private FlightService flightService;
    @SpringBean
    private AirplaneService airService;
    @SpringBean
    private StewardService stewService;
    @SpringBean
    private DestinationService desService;
//    @ValidateNestedProperties(value = {
        //            @Validate(on = {"addflight", "saveflight"}, field = "departureTime", required = true),
        //            @Validate(on = {"addflight", "saveflight"}, field = "arrivalTime", required = true),
//        @Validate(on = {"addflight", "saveflight"}, field = "origin", required = true),
//        @Validate(on = {"addflight", "saveflight"}, field = "target", required = true),
//        @Validate(on = {"addflight", "saveflight"}, field = "airplane", required = true)
//    }) 
    private FlightTO flight;
    private List<FlightTO> flights;
    private List<DestinationTO> desList;
    private List<AirplaneTO> airList;
    private List<StewardTO> stewList;
    private static List<StewardTO> actualFlightStewList;
    
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String arrTime;
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String arrDate;
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String depTime;
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String depDate;
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String target;
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String origin;
    @Validate(on = {"addflight", "saveflight"}, required = true)
    private String airplane;

    public List<DestinationTO> getDesList() {
        desList = desService.getAllDestinations();
        return desList;
    }

    public void setDesList(List<DestinationTO> desList) {
        this.desList = desList;
    }

    public List<AirplaneTO> getAirList() {
        airList = airService.getAllAirplanes();
        return airList;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public void setAirList(List<AirplaneTO> airList) {
        this.airList = airList;
    }

    public List<StewardTO> getStewList() {
        stewList = stewService.findAllStewards();
        return stewList;
    }

    public void setStewList(List<StewardTO> stewList) {
        this.stewList = stewList;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public FlightTO getFlight() {
        return flight;
    }

    public void setFlight(FlightTO flight) {
        this.flight = flight;
    }

    public List<FlightTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightTO> flights) {
        this.flights = flights;
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"addflight",
        "editflight", "deleteflight"})
    public void loadFlight() {
        String id = getContext().getRequest().getParameter("flight.id");
        if (id == null || id.isEmpty()) {
            System.out.println("flight id is null or epmty");
            return;
        }
        flight = flightService.getFlight(Long.parseLong(id));
    }

    @DefaultHandler
    @HandlesEvent("listflight")
    public Resolution listFlights() {
        System.out.println("list called");
        flights = flightService.getAllFlights();
        return new ForwardResolution("/flight/list.jsp");
    }

    @HandlesEvent("addflight")
    public Resolution addNewFlight() throws ParseException {
        System.out.println("add fligth called");
//        System.out.println("arrTime: " + arrTime);
//        System.out.println("arrDate: " + arrDate);
//        System.out.println("depDate: " + depDate);
//        System.out.println("depTime: " + depTime);
        flight = new FlightTO();
        prepareFlight(getContext().getRequest().getLocale().getLanguage());
        flight.setStewList(new ArrayList<StewardTO>());
//        System.out.println(flight.getArrivalTime());
//        System.out.println(flight.getDepartureTime());
        flightService.createFlight(flight);
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("createflight")
    public Resolution createFlightFormular() {
        System.out.println("create formular called");
        return new ForwardResolution("/flight/create.jsp");
    }

    @HandlesEvent("saveflight")
    public Resolution updateFlight() throws ParseException {
        System.out.println("save flight called");
//        System.out.println("arrTime: " + arrTime);
//        System.out.println("arrDate: " + arrDate);
//        System.out.println("depDate: " + depDate);
//        System.out.println("depTime: " + depTime);
        prepareFlight(getContext().getRequest().getLocale().getLanguage());
        System.out.println("actual list " + actualFlightStewList);
        flight.setStewList(actualFlightStewList);
//        System.out.println(flight.getArrivalTime());
//        System.out.println(flight.getDepartureTime());
        flightService.updateFlight(flight);
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("deleteflight")
    public Resolution removeFlight() {
        System.out.println("delete flight called");
//        loadFlight();
        flightService.removeFlight(flight);
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("editflight")
    public Resolution edit() {
        System.out.println("edit flight called");
        String loc = getContext().getRequest().getLocale().getLanguage();
        arrDate = formatDateStamp(flight.getArrivalTime(), loc);
        arrTime = formatTimeStamp(flight.getArrivalTime(), loc);
        depTime = formatTimeStamp(flight.getDepartureTime(), loc);
        depDate = formatDateStamp(flight.getDepartureTime(), loc);
        actualFlightStewList = flight.getStewList();
        System.out.println("edit actual: " + actualFlightStewList);
//        System.out.println("arrTime: " + arrTime);
//        System.out.println("arrDate: " + arrDate);
//        System.out.println("depDate: " + depDate);
//        System.out.println("depTime: " + depTime);
//        loadFlight();
        return new ForwardResolution("/flight/edit.jsp");
    }

    @HandlesEvent("cancelflight")
    public Resolution doNothing() {
        System.out.println("cancel flight called");
        return new RedirectResolution(this.getClass());
    }
    
    private String formatTimeStamp(Timestamp ts, String lang){
        System.out.println(lang);
        if(lang.equals("sk") || lang.equals("cs") || lang.equals("cz")){
            return ts.getHours() + ":" + ts.getMinutes();
        }
        if(ts.getHours() > 12){
            return (ts.getHours()-12) + ":" + ts.getMinutes() + " PM";
        }
        return ts.getHours() + ":" + ts.getMinutes() + " AM";
    }
    
    private String formatDateStamp(Timestamp ts, String lang){
        System.out.println(lang);
        if(lang.equals("sk") || lang.equals("cs") || lang.equals("cz")){
            return ts.getDate() + ". " + ts.getMonth() + ". " + ts.getYear();
        }
        return ts.getMonth()+ "/" + ts.getDate()+ "/" + ts.getYear();
    }
    
    public Timestamp stringToTimestampSK(String string) throws ParseException {
        
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd.MM.yyy hh:mm");
        
        Date lFromDate1 = datetimeFormatter1.parse(string);
    
        return new Timestamp(lFromDate1.getTime());
    }
    
    public Timestamp stringToTimestampEN(String string) throws ParseException {
        
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        
        Date lFromDate1 = datetimeFormatter1.parse(string);
        
        return new Timestamp(lFromDate1.getTime()) ;
    }
    
    private void prepareFlight(String lang)throws ParseException{
        if(lang.equals("sk") || lang.equals("cs") || lang.equals("cz")){
            flight.setArrivalTime(stringToTimestampSK(arrDate + " " + arrTime));
            flight.setDepartureTime(stringToTimestampSK(depDate + " " + depTime));
        } else {
            flight.setArrivalTime(stringToTimestampEN(arrDate + " " + arrTime));
            flight.setDepartureTime(stringToTimestampEN(depDate + " " + depTime));
        }
//        List<StewardTO> s = getStewList();
//        List<DestinationTO> d = getDesList();
//        List<AirplaneTO> a = getAirList();
        flight.setAirplaneTO(airService.getAirplane(getIdOfEntity(airplane)));
        flight.setTarget(desService.getDestination(Long.parseLong(target)));
        flight.setOrigin(desService.getDestination(Long.parseLong(origin)));
    }
    
    private Long getIdOfEntity(String toParse){
        System.out.println("String: " + toParse);
        String[] s = toParse.split("[()]+");
        System.out.println(Arrays.toString(s));
        return Long.valueOf(s[s.length-1]);
    }
}
