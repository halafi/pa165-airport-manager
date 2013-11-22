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
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Samo
 */
@UrlBinding("/flights/{$event}/{flight.id}")
public class FlightsActionBean extends BaseActionBean implements ValidationErrorHandler{
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

        @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "departureTime", required = true),
            @Validate(on = {"add", "save"}, field = "arrivalTime", required = true)//,
//            @Validate(on = {"add", "updateFlight"}, field = "origin", required = true),
//            @Validate(on = {"add", "updateFlight"}, field = "target", required = true),
//            @Validate(on = {"add", "updateFlight"}, field = "airplane", required = true)
    })
    private FlightTO flight;
    
    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        try {
            flights =  flightService.getAllFlights();
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/flights/list.jsp");
    }
    
    public List<FlightTO> getFlights(){
        return flights;
    }
    
    //--- adding flight ----
    @HandlesEvent("add")
    public Resolution createFlight() {
        log.debug("add() flight={}", flight);
        try{
            flightService.createFlight(flight);
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage(""
                + "flight.add.message",escapeHTML(flight.getOrigin().getCity()),
                escapeHTML(flight.getTarget().getCity()),escapeHTML(flight.getDepartureTime().toString()),
                escapeHTML(flight.getArrivalTime().toString()),
                escapeHTML(flight.getAirplaneTO().getName())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        try {
            flights = flightService.getAllFlights();
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        //return null to let the event handling continue
        return null;
    }
    
//    public List<StewardTO> getStewards(Long id){
//        flight = new FlightTO();
//        flight = flightService.getFlight(id);
//        List<StewardTO> stewList = new ArrayList<StewardTO>();
//        for(StewardTO stew : flight.getStewList()){
//            stewList.add(stew);
//        }
//        return stewList;
//    }
    
    
    
    
    @HandlesEvent("save")
    public Resolution updateFlight() {
        log.debug("save() flight={}", flight);
        try {
            flightService.updateFlight(flight);
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    //Follows some redirect resolutions
    public Resolution cancel(){
        return new RedirectResolution(this.getClass());
    }
    
    public Resolution edit() {
        log.debug("edit() flight={}", flight);
        return new ForwardResolution("/flights/edit.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"save", "edit", "delete"})
    public void loadFlightFromDb() {
        String ids = getContext().getRequest().getParameter("flight.id");
        if (ids == null) return;
        try {
            flight = flightService.getFlight(Long.parseLong(ids));
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing " + ex);
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error" + ex);
            getContext().getValidationErrors().addGlobalError(err);
        }
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
    
    private List<DestinationTO> desList;
    public List<DestinationTO> getDesList(){
        desList = destinationService.getAllDestinations();
        return desList;
    }
    
    private List<AirplaneTO> airList;
    public List<AirplaneTO> getAirList(){
        airList = airplaneService.getAllAirplanes();
        return airList;
    }
    
    private List<StewardTO> stewList;
    public List<StewardTO> getStewList(){
        stewList = stewardService.findAllStewards();
        return stewList;
    }
    
//    private List<StewardTO> stewardList;
//    @HandlesEvent("stewards")
//    public Resolution getStewardList(){
//        stewardList = flight.getStewList();
//        return new ForwardResolution("/flights/listStewards.jsp");
//    }
    
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
 
    public Resolution createTest() {
        flight = new FlightTO();
        AirplaneTO airplane = new AirplaneTO();
        airplane.setCapacity(10);
        airplane.setName("plane");
        airplane.setType("planetype");
        DestinationTO des = new DestinationTO();
        des.setCity("city");
        des.setCode("ABD");
        des.setCountry("c1");
        StewardTO stew = new StewardTO();
        stew.setFirstName("jano");
        stew.setLastName("jayes");
        List<StewardTO> sl = new ArrayList<>();
        sl.add(stew);
        Timestamp ts = Timestamp.valueOf("2007-09-23 10:10:10.0");
        flight.setArrivalTime(ts);
        flight.setDepartureTime(ts);
        flight.setOrigin(des);
        flight.setAirplaneTO(airplane);
        
        flight.setTarget(des);
        flight.setStewList(sl);
        createFlight();
        flights =  flightService.getAllFlights();
        return new ForwardResolution("/flights/list.jsp");
    }
    
    public Resolution updateTest(){
        flight = new FlightTO();
        flight = flightService.getAllFlights().get(0);
        Timestamp ts = Timestamp.valueOf("2010-09-23 10:10:10.0");
        flight.setArrivalTime(ts);
        //flightService.updateFlight(flight);
        updateFlight();
        return new RedirectResolution(this.getClass(), "list");
    }
}
