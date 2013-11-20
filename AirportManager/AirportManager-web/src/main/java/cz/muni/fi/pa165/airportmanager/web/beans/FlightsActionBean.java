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
import static cz.muni.fi.pa165.airportmanager.web.beans.BaseActionBean.escapeHTML;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samo
 */
@UrlBinding("/flights/{$event}/{flight.id}")
public class FlightsActionBean extends BaseActionBean{
    final static Logger log = LoggerFactory.getLogger(FlightsActionBean.class);

    @SpringBean
    protected FlightService flightService;
    @SpringBean
    protected DestinationService destinationService;
    @SpringBean
    protected AirplaneService airplaneService;
    @SpringBean
    protected StewardService stewardService;
    
    private List<FlightTO> flights;

    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        
//        flight = new FlightTO();
        AirplaneTO airplane = new AirplaneTO();
//        airplane.setCapacity(10);
//        airplane.setName("plane");
//        airplane.setType("planetype");
//        airplaneService.createAirplane(airplane);
//        System.out.println(airplane.toString());
        airplane = airplaneService.getAllAirplanes().get(0);
        System.out.println(airplane.toString());
        DestinationTO des = new DestinationTO();
//        des.setCity("city");
//        des.setCode("ABD");
//        des.setCountry("c1");
//        destinationService.createDestination(des);
        des = destinationService.getAllDestinations().get(0);
//        System.out.println(des.toString());
//        
//        StewardTO stew = new StewardTO();
//        stew.setFirstName("jano");
//        stew.setLastName("jayes");
//        stewardService.createSteward(stew);
//        stew = stewardService.findAllStewards().get(0);
        List<StewardTO> sl = new ArrayList<>();
        //sl.add(stew);
        
        Timestamp ts = Timestamp.valueOf("2007-09-23 10:10:10.0");

        flight.setAirplaneTO(airplane);
        flight.setArrivalTime(ts);
        flight.setDepartureTime(ts);
        flight.setOrigin(des);
        flight.setTarget(des);
        flight.setStewList(sl);
        System.out.println(flight.toString());
        flightService.createFlight(flight);
        flights =  flightService.getAllFlights();
        //flights = flightService.getAllFlights();
        return new ForwardResolution("/flights/list.jsp");
    }
    
    public List<FlightTO> getFlights(){
        return flights;
    }
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "departureTime", required = true),
            @Validate(on = {"add", "save"}, field = "arrivalTime", required = true),
            @Validate(on = {"add", "save"}, field = "origin", required = true),
            @Validate(on = {"add", "save"}, field = "target", required = true),
            @Validate(on = {"add", "save"}, field = "airplane", required = true)
    })
    private FlightTO flight;
    
    //--- adding flight ----
    
    public Resolution add() {
        log.debug("add() flight={}", flight);
        flightService.createFlight(flight);
        getContext().getMessages().add(new LocalizableMessage(""
                + "flight.add.message",escapeHTML(flight.getOrigin().getCity()),
                escapeHTML(flight.getTarget().getCity()),escapeHTML(flight.getDepartureTime().toString()),
                escapeHTML(flight.getArrivalTime().toString()),
                escapeHTML(flight.getAirplaneTO().getName())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    //@Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        flights = flightService.getAllFlights();
        //return null to let the event handling continue
        return null;
    }
    
    public List<StewardTO> getStewards(Long id){
        flight = new FlightTO();
        flight = flightService.getFlight(id);
        List<StewardTO> stewList = new ArrayList<StewardTO>();
        for(StewardTO stew : flight.getStewList()){
            stewList.add(stew);
        }
        return stewList;
    }
    
    
    
    

    public Resolution save() {
        log.debug("save() flight={}", flight);
        flightService.updateFlight(flight);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution edit() {
        log.debug("edit() flight={}", flight);
        return new ForwardResolution("/flight/edit.jsp");
    }

    // edit
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadFlightFromDb() {
        String ids = getContext().getRequest().getParameter("flight.id");
        if (ids == null) return;
        flight = flightService.getFlight(Long.parseLong(ids));
    }
    
    //delete
    public Resolution delete() {
        log.debug("delete({})", flight.getId());
        //only id is filled by the form
        flight = flightService.getFlight(flight.getId());
        flightService.removeFlight(flight);
        getContext().getMessages().add(new LocalizableMessage("flight.delete.message",escapeHTML(flight.getOrigin().getCity()),
                escapeHTML(flight.getTarget().getCity()),escapeHTML(flight.getDepartureTime().toString()),
                escapeHTML(flight.getAirplaneTO().getName())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    
    
    
    public FlightTO getFlight() {
        return flight;
    }

    public void setFlight(FlightTO flight) {
        this.flight = flight;
    }
    
    public Resolution create() {
        log.debug("create() flight={}", flight);
        return new ForwardResolution("/flights/create.jsp");
    }
 
//    
//    public Resolution getDestination(Long id) {
//        if(id != null){
//            destinationService.getDestination(id);
//        }
//        
//    }
    public Resolution createTest() {
        flight = new FlightTO();
        AirplaneTO airplane = new AirplaneTO();
        airplane.setCapacity(10);
        airplane.setName("plane");
        airplane.setType("planetype");
        airplaneService.createAirplane(airplane);
        DestinationTO des = new DestinationTO();
        des.setCity("city");
        des.setCode("ABD");
        des.setCountry("c1");
        destinationService.createDestination(des);
//        StewardTO stew = new StewardTO();
//        stew.setFirstName("jano");
//        stew.setLastName("jayes");
//        stewardService.createSteward(stew);
        
        Timestamp ts = Timestamp.valueOf("2007-09-23 10:10:10.0");

        flight.setAirplaneTO(airplane);
        flight.setArrivalTime(ts);
        flight.setDepartureTime(ts);
        flight.setOrigin(des);
        flight.setTarget(des);
        flightService.createFlight(flight);
        flights =  flightService.getAllFlights();
        return new ForwardResolution("/flights/list.jsp");
    }
}
