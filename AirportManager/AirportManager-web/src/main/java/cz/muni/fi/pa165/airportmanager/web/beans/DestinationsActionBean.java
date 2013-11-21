package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.services.FlightService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import static cz.muni.fi.pa165.airportmanager.web.beans.BaseActionBean.escapeHTML;
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
 * @author Filip
 */
@UrlBinding("/destinations/{$event}/{destination.id}")
public class DestinationsActionBean extends BaseActionBean implements ValidationErrorHandler {
    
    final static Logger log = LoggerFactory.getLogger(DestinationsActionBean.class);
    
    @SpringBean
    protected DestinationService destinationService;
    
    private List<DestinationTO> destinations;
    
    private List<FlightTO> flights;
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "country", required = true),
            @Validate(on = {"add", "save"}, field = "city", required = true),
            @Validate(on = {"add", "save"}, field = "code", required = true)
    })
    private DestinationTO destination;

    public List<DestinationTO> getDestinations() {
        return destinations;
    }
    
    public DestinationTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationTO destination) {
        this.destination = destination;
    }
    
    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        try {
            destinations = destinationService.getAllDestinations();
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/destination/list.jsp");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        try {
            destinations = destinationService.getAllDestinations();
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return null;
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"save", "edit", "delete"})
    public void loadDestinationFromDatabase() {
        String ids = getContext().getRequest().getParameter("destination.id");
        if (ids == null) return;
        try {
            destination = destinationService.getDestination(Long.parseLong(ids));
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing " + ex);
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error" + ex);
            getContext().getValidationErrors().addGlobalError(err);
        }
    }

    @HandlesEvent("outcoming")
    public Resolution getAllIncomingFlights() {
        String ids = getContext().getRequest().getParameter("destination.id");
        try {
            destination = destinationService.getDestination(Long.parseLong(ids));
            destinations = destinationService.getAllDestinations();
            flights = destinationService.getAllOutcomingFlights(destination);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing " + ex);
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error" + ex);
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage("destination.list.outcoming", escapeHTML(destination.getId().toString())));
        return new ForwardResolution("/destination/listOutcoming.jsp");
    }
    
    @HandlesEvent("incoming")
    public Resolution getAllOutcomingFlights() {
        String ids = getContext().getRequest().getParameter("destination.id");
        try {
            destination = destinationService.getDestination(Long.parseLong(ids));
            destinations = destinationService.getAllDestinations();
            flights = destinationService.getAllIncomingFlights(destination);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing " + ex);
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error" + ex);
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage("destination.list.incoming", escapeHTML(destination.getId().toString())));
        return new ForwardResolution("/destination/listIncoming.jsp");
    }
    
    @HandlesEvent("add")
    public Resolution createDestination() {
        log.debug("add() destination={}", destination);
        try {
            destinationService.createDestination(destination);
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage("destination.created",escapeHTML(destination.getCountry()),escapeHTML(destination.getCity()),escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @HandlesEvent("save")
    public Resolution updateDestination() {
        log.debug("save() destination={}", destination);
        try {
            destinationService.updateDestination(destination);
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage("destination.updated",escapeHTML(destination.getCountry()),escapeHTML(destination.getCity()),escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @HandlesEvent("delete")
    public Resolution deleteDestination() {
        log.debug("delete({})", destination.getId());
        try {
            destinationService.removeDestination(destination);
        } catch(DataAccessException ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        getContext().getMessages().add(new LocalizableMessage("destination.deleted",escapeHTML(destination.getCountry()),escapeHTML(destination.getCity()),escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    //Follows some redirect resolutions
    public Resolution cancel(){
        return new RedirectResolution(this.getClass());
    }
    
    public Resolution edit() {
        log.debug("edit() destination={}", destination);
        return new ForwardResolution("/destination/edit.jsp");
    }
}
