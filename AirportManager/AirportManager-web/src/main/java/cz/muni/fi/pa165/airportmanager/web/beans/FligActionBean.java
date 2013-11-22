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
    @ValidateNestedProperties(value = {
        //            @Validate(on = {"addflight", "saveflight"}, field = "departureTime", required = true),
        //            @Validate(on = {"addflight", "saveflight"}, field = "arrivalTime", required = true),
        @Validate(on = {"addflight", "saveflight"}, field = "origin", required = true),
        @Validate(on = {"addflight", "saveflight"}, field = "target", required = true),
        @Validate(on = {"addflight", "saveflight"}, field = "airplane", required = true)
    })
    private FlightTO flight;
    private List<FlightTO> flights;
    private List<DestinationTO> desList;
    private List<AirplaneTO> airList;
    private List<StewardTO> stewList;
    private String arrTime;
    private String arrDate;
    private String depTime;
    private String depDate;

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
    public Resolution addNewFlight() {
        System.out.println("add fligth called");
        System.out.println("arrTime: " + arrTime);
        System.out.println("arrDate: " + arrDate);
        System.out.println("depDate: " + depDate);
        System.out.println("depTime: " + depTime);
        flightService.createFlight(flight);
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("createflight")
    public Resolution createFlightFormular() {
        System.out.println("create formular called");
        return new ForwardResolution("/flight/create.jsp");
    }

    @HandlesEvent("saveflight")
    public Resolution updateFlight() {
        System.out.println("save flight called");
        System.out.println("arrTime: " + arrTime);
        System.out.println("arrDate: " + arrDate);
        System.out.println("depDate: " + depDate);
        System.out.println("depTime: " + depTime);
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
        arrDate = "arrdate";
        arrTime = "arrtime";
        depTime = "deptime";
        depDate = "depdate";
        System.out.println("arrTime: " + arrTime);
        System.out.println("arrDate: " + arrDate);
        System.out.println("depDate: " + depDate);
        System.out.println("depTime: " + depTime);
//        loadFlight();
        return new ForwardResolution("/flight/edit.jsp");
    }

    @HandlesEvent("cancelflight")
    public Resolution doNothing() {
        System.out.println("cancel flight called");
        return new RedirectResolution(this.getClass());
    }

    public Timestamp stringToTimestampSK(String string) throws ParseException {
        
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd.MM.yyy hh:mm");
        
        Date lFromDate1 = datetimeFormatter1.parse(string);
    
        return new Timestamp(lFromDate1.getTime());
    }
    
    public Timestamp stringToTimestampEN(String string) throws ParseException {
        
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("MM/dd/yyy h:mm");
        
        Date lFromDate1 = datetimeFormatter1.parse(string);
        
        return new Timestamp(lFromDate1.getTime()) ;
    }
}
