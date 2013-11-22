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
import net.sourceforge.stripes.validation.LocalizableError;
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
    private List<DestinationTO> desList;
    private List<AirplaneTO> airList;
    private List<StewardTO> stewList;

    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "departureTime", required = true),
            @Validate(on = {"add", "save"}, field = "arrivalTime", required = true),
            @Validate(on = {"add", "save"}, field = "origin", required = true),
            @Validate(on = {"add", "save"}, field = "target", required = true),
            @Validate(on = {"add", "save"}, field = "airplane", required = true)
    })
    private FlightTO flight;

    @DefaultHandler
    @HandlesEvent("list")
    public Resolution list() {
        System.out.println("list called");
        log.debug("list()");
        try {
            flights =  flightService.getAllFlights();
        } catch(DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown", 
                    escapeHTML(ex.toString()));
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
        System.out.println("add called");
        log.debug("add() flight={}", flight);
        try{
//            System.out.println("added flight " + flight);
            flightService.createFlight(flight);
        } catch(DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown", 
                    escapeHTML(ex.toString()));
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
        System.out.println("validation errors called");
        try {
            flights = flightService.getAllFlights();
        } catch(DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
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
        System.out.println("save called");
        log.debug("save() flight={}", flight);
        try {
            flightService.updateFlight(flight);
        } catch(DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    //Follows some redirect resolutions
    @HandlesEvent("cancel")
    public Resolution cancel(){
        System.out.println("cancel called");
        return new RedirectResolution(this.getClass());
    }
    
    @HandlesEvent("edit")
    public Resolution edit() {
        System.out.println("edit called");
        log.debug("edit() flight={}", flight);
        return new RedirectResolution("/flights/edit.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"save", "edit", "delete"})
    public void loadFlightFromDb() {
        System.out.println("load from db called");
        String ids = getContext().getRequest().getParameter("flight.id");
        if (ids == null) {
            System.out.println("load from db return null");
            return;
        }
        try {
            System.out.println("load from db trying to load");
            flight = flightService.getFlight(Long.parseLong(ids));
            System.out.println("load from db success");
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("flight.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("flight.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
    }
    
    @HandlesEvent("delete")
    public Resolution deleteFlight() {
        System.out.println("delete called");
        log.debug("delete({})", flight.getId());
        //only id is filled by the form
        try{
            flightService.removeFlight(flight);
        } catch(DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage("flight.delete.message",escapeHTML(flight.getOrigin().getCity()),
                escapeHTML(flight.getTarget().getCity()),escapeHTML(flight.getDepartureTime().toString()),
                escapeHTML(flight.getAirplaneTO().getName())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public List<DestinationTO> getDesList(){
        desList = destinationService.getAllDestinations();
        return desList;
    }
    
    public List<AirplaneTO> getAirList(){
        airList = airplaneService.getAllAirplanes();
        return airList;
    }
    
    public List<StewardTO> getStewList(){
        stewList = stewardService.findAllStewards();
        return stewList;
    }
    
    public FlightTO getFlight() {
        return flight;
    }

    public void setFlight(FlightTO flight) {
        this.flight = flight;
    }
    
    @HandlesEvent("create")
    public Resolution create() {
        System.out.println("create called");
        log.debug("create() flight={}", flight);
        return new RedirectResolution("/flights/create.jsp");
    }
 
    @HandlesEvent("createtest")
    public Resolution createTest() {
        System.out.println("create test called");
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
    @HandlesEvent("updatetest")
    public Resolution updateTest(){
        System.out.println("update test called");
        flight = new FlightTO();
        flight = flightService.getAllFlights().get(0);
        Timestamp ts = Timestamp.valueOf("2010-09-23 10:10:10.0");
        flight.setArrivalTime(ts);
        //flightService.updateFlight(flight);
        updateFlight();
        return new RedirectResolution(this.getClass(), "list");
    }
}