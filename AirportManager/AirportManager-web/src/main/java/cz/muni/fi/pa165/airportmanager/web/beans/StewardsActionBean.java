
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
import java.util.LinkedList;
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
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
@UrlBinding("/stewards/{$event}/{steward.id}")
public class StewardsActionBean extends BaseActionBean{
    
    private static boolean dbFilled = false;
    
    @SpringBean
    private StewardService stewService;
    
    @SpringBean
    private FlightService flightService;
    
    @SpringBean
    private DestinationService desService;
    
    @SpringBean
    private AirplaneService airService;
    
    private List<StewardTO> stewards;
    private List<FlightTO> flights;
    
    @ValidateNestedProperties({
            @Validate(on = {"addsteward", "savesteward"}, field = "firstName", 
                    required = true, trim = true, minlength = 1),
            @Validate(on = {"addsteward", "savesteward"}, field = "lastName", 
                    required = true, trim = true, minlength = 1)
            })
    private StewardTO steward;
    
    public List<FlightTO> getFlights() {
        return flights;
    }

    public StewardTO getSteward() {
        return steward;
    }

    public List<StewardTO> getStewards() {
        return stewards;
    }

    public void setSteward(StewardTO steward) {
        this.steward = steward;
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"editsteward", "savesteward", 
        "flights", "addflight", "deletesteward", "removeflight"})
    public void loadSteward(){
        String id = getContext().getRequest().getParameter("steward.id");
        if(id == null){
            return;
        }
        try{
            steward = stewService.findSteward(Long.parseLong(id));
            flights = stewService.getAllStewardsFlights(steward);
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
    }
    
    public FlightTO loadFlight(){
        String id = getContext().getRequest().getParameter("flight.id");
        if(id == null){
            return null;
        }
        try{
            return flightService.getFlight(Long.parseLong(id));
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return null;
    }
    
    @DefaultHandler
    @HandlesEvent("list")
    public Resolution showStewardsList(){
        try{
//            prepareDB();
            stewards = stewService.findAllStewards();
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/steward/list.jsp");
    }
    
    @HandlesEvent("addsteward")
    public Resolution addNewSteward(){
        try{
            System.out.println("created steward: " + steward);
            stewService.createSteward(steward);
            getContext().getMessages().add(new LocalizableMessage("steward.created", 
                    escapeHTML(steward.getFirstName()),
                    escapeHTML(steward.getLastName())));
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @HandlesEvent("editsteward")
    public Resolution editFormular(){
        ForwardResolution f = new ForwardResolution("/steward/edit.jsp?createnew=false");
        f.addParameter("event", "edit");
        return f;
    }
    
    @HandlesEvent("createsteward")
    public Resolution createFormular(){
//        ForwardResolution f = new ForwardResolution("/steward/edit.jsp?createnew=true");
////        f.addParameter("createnew", "true");
//        return f;
        return new ForwardResolution("/steward/edit.jsp?createnew=true");
    }
    
    @HandlesEvent("savesteward")
    public Resolution saveStewardsEdit(){
        try{
            stewService.updateSteward(steward);
            getContext().getMessages().add(new LocalizableMessage("steward.updated", 
                    escapeHTML(steward.getFirstName()),
                    escapeHTML(steward.getLastName())));
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @HandlesEvent("deletesteward")
    public Resolution removeSteward(){
        try{
            loadSteward();
            stewService.removeSteward(steward);
            getContext().getMessages().add(new LocalizableMessage("steward.deleted", 
                    escapeHTML(steward.getFirstName()),
                    escapeHTML(steward.getLastName())));
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(),"list");
    }
    
    @HandlesEvent("flights")
    public Resolution showAllStewardsFlights(){
        try{
            flights = stewService.getAllStewardsFlights(steward);
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/steward/flights.jsp?add=false");
    }
    
    @HandlesEvent("addflight")
    public Resolution showStewardsFlightToAdd(){
        try{
            flights = stewService.getAllStewardsFlights(steward);
            List<FlightTO> allFlights = flightService.getAllFlights();
            flights = getRemainingFights(flights, allFlights);
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/steward/flights.jsp?add=true");
    }
    
    @HandlesEvent("removeflight")
    public Resolution removeStewardsFlight(){
       try{
            FlightTO flight = loadFlight();
            loadSteward();
            if(flight == null){
                throw new IllegalStateException("Missing flight id");
            }
            flight.getStewList().remove(steward);
            flightService.updateFlight(flight);
            flights = stewService.getAllStewardsFlights(steward);
            stewService.updateSteward(steward);
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        if(getContext().getRequest().getParameter("event").equals("flights")){
            ForwardResolution res = new ForwardResolution(this.getClass(), "flights");
            return res;
        } else {
            return new ForwardResolution(this.getClass(), "editsteward");
        }
    }
    
    @HandlesEvent("addflighttolist")
    public Resolution addStewardsFlight(){
        try{
            FlightTO flight = loadFlight();
            if(flight == null){
                throw new IllegalStateException("Missing flight id");
            }
            flight.getStewList().add(steward);
            flightService.updateFlight(flight);
        } catch (DataAccessException ex){
            LocalizableError err = new LocalizableError("steward.error.service", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            LocalizableError err = new LocalizableError("steward.error.uknown", 
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution(this.getClass(), "addflight");
    }
    
    @HandlesEvent("cancelsteward")
    public Resolution doNothing(){
        return new RedirectResolution(this.getClass());
    }

    private List<FlightTO> getRemainingFights(List<FlightTO> stewFlights, List<FlightTO> allFlights) {
        List<FlightTO> out = new LinkedList<>();
        for(FlightTO f : allFlights){
            if(!stewFlights.contains(f)){
                out.add(f);
            }
        }
        return out;
    }
    
    private void prepareDB(){
        System.out.println("prepareDB " + dbFilled);
        if(dbFilled){
            dbFilled = true;
            return;
        }
        dbFilled = true;
        DestinationTO d1 = new DestinationTO();
        DestinationTO d2 = new DestinationTO();
        d1.setCity("pp1");
        d1.setCountry("sk1");
        d1.setCode("bla1");
        d2.setCity("pp2");
        d2.setCountry("sk2");
        d2.setCode("bla2");
        
        AirplaneTO a = new AirplaneTO();
        a.setCapacity(100);
        a.setName("moje");
        a.setType("747");
        
        long time = System.currentTimeMillis();
        
        FlightTO f1 = new FlightTO();
        FlightTO f2 = new FlightTO();
        FlightTO f3 = new FlightTO();
        FlightTO f4 = new FlightTO();
        FlightTO f5 = new FlightTO();
        f1.setAirplaneTO(a);
        f1.setOrigin(d1);
        f1.setTarget(d2);
        f1.setArrivalTime(new Timestamp(time + 100000));
        f1.setDepartureTime(new Timestamp(time));
        f2.setAirplaneTO(a);
        f2.setOrigin(d1);
        f2.setTarget(d2);
        f2.setArrivalTime(new Timestamp(time + 300000));
        f2.setDepartureTime(new Timestamp(time + 200000));
        f3.setAirplaneTO(a);
        f3.setOrigin(d1);
        f3.setTarget(d2);
        f3.setArrivalTime(new Timestamp(time + 500000));
        f3.setDepartureTime(new Timestamp(time + 400000));
        f4.setAirplaneTO(a);
        f4.setOrigin(d1);
        f4.setTarget(d2);
        f4.setArrivalTime(new Timestamp(time + 700000));
        f4.setDepartureTime(new Timestamp(time + 600000));
        f5.setAirplaneTO(a);
        f5.setOrigin(d1);
        f5.setTarget(d2);
        f5.setArrivalTime(new Timestamp(time + 900000));
        f5.setDepartureTime(new Timestamp(time + 800000));
        
        StewardTO s = new StewardTO();
        s.setFirstName("firsť");
        s.setLastName("last");
        
        List<StewardTO> ls = new ArrayList<>();
        List<StewardTO> empty = new ArrayList<>();
        ls.add(s);
        
        f1.setStewList(ls);
        f3.setStewList(ls);
        f2.setStewList(empty);
        f4.setStewList(empty);
        f5.setStewList(empty);
        
        desService.createDestination(d1);
        desService.createDestination(d2);
        
        airService.createAirplane(a);
        
        stewService.createSteward(s);
        
        flightService.createFlight(f1);
        flightService.createFlight(f2);
        flightService.createFlight(f3);
        flightService.createFlight(f4);
        flightService.createFlight(f5);
    }
}
