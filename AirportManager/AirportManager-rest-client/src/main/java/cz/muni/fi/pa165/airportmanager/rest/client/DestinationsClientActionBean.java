package cz.muni.fi.pa165.airportmanager.rest.client;

import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.Arrays;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Action bean for Destination entity using REST server.
 *
 * @author Filip
 */
@UrlBinding("/destinations/{$event}/{destination.id}")
public class DestinationsClientActionBean extends BaseActionBean implements ValidationErrorHandler {
    
    final static Logger log = LoggerFactory.getLogger(DestinationsClientActionBean.class);
    
    @SpringBean
    private RestTemplate restTemplate;
    
    private List<DestinationTO> destinations;
    private List<FlightTO> flights;
    
    private static final String serverUrl = "http://localhost:8080/pa165/airport-manager-web/rest-jersey-server/destination";

    public static String getUrl() {
        return serverUrl;
    }

    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "country", required = true),
            @Validate(on = {"add", "save"}, field = "city", required = true),
            @Validate(on = {"add", "save"}, field = "code", required = true)
    })
    private DestinationTO destination;

    /**
     * Get method for list of destinations DTO
     * 
     * @return List of DestinationTO objects
     */
    public List<DestinationTO> getDestinations() {
        return destinations;
    }
    
    /**
     * Get method for list of flights DTO
     * 
     * @return List of FlightTO objects
     */
     public List<FlightTO> getFlights() {
        return flights;
    }
    
    /**
     * Get method for single DestinationTO.
     * 
     * @return destination DestinationTO
     */
    public DestinationTO getDestination() {
        return destination;
    }

    /**
     * Setter for destination DTO.
     * 
     * @param destination DestiinationTO to be set 
     */
    public void setDestination(DestinationTO destination) {
        this.destination = destination;
    }
    
    /**
     * Default handler that returns all DestinationTO's and redirects to /destination/list.jsp.
     * 
     * @return redirect to /destination/list.jsp
     */
    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        try {
            DestinationTO[] dsts = restTemplate.getForObject(getUrl(), DestinationTO[].class);
            destinations = Arrays.asList(dsts);
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/destination/list.jsp");
    }
    
    /**
     * Resolution that handles validation errors.
     * 
     * @param errors ValidationErrors to be handled
     * @return null
     */
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) {
        try {
            DestinationTO[] dsts = restTemplate.getForObject(getUrl(), DestinationTO[].class);
            destinations = Arrays.asList(dsts);
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return null;
    }

    /**
     * Method that pre-loads one destination in to form.
     */
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"save", "edit", "delete"})
    public void loadDestinationFromDatabase() {
        String ids = getContext().getRequest().getParameter("destination.id");
        if (ids == null) return;
        try {
            destination = restTemplate.getForObject(getUrl() + "/{id}", DestinationTO.class, Long.parseLong(ids));
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
    }
    
    /**
     * Resolution that creates destination.
     * 
     * @return redirect to /destination/list.jsp
     */
    @HandlesEvent("add")
    public Resolution createDestination() {
        log.debug("add() destination={}", destination);
        try {
            restTemplate.postForObject(getUrl() + "/", destination, DestinationTO.class);
            getContext().getMessages().add(
                    new LocalizableMessage("destination.created",
                        escapeHTML(destination.getCountry()),
                        escapeHTML(destination.getCity()),
                        escapeHTML(destination.getCode())));
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    /**
     * Resolution that updates the selected/modified destination.
     * 
     * @return redirect to /destination/list.jsp 
     */
    @HandlesEvent("save")
    public Resolution updateDestination() {
        log.debug("save() destination={}", destination);
        try {
            restTemplate.put(getUrl() + "/{id}", destination, destination.getId());
            getContext().getMessages().add(
                    new LocalizableMessage("destination.updated",
                        escapeHTML(destination.getCountry()),
                        escapeHTML(destination.getCity()),
                        escapeHTML(destination.getCode())));
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    /**
     * Resolution that deletes the selected destination.
     * 
     * @return redirect to /destination/list.jsp
     */
    @HandlesEvent("delete")
    public Resolution deleteDestination() {
        log.debug("delete({})", destination.getId());
        try {
            restTemplate.delete(getUrl() + "/{id}", destination.getId());
            getContext().getMessages().add(
                    new LocalizableMessage("destination.deleted",
                        escapeHTML(destination.getCountry()),
                        escapeHTML(destination.getCity()),
                        escapeHTML(destination.getCode())));
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    /**
     * Resolution that shows all the outcoming flights for the selected destination.
     * 
     * @return redirect to /destination/listOutcoming.jsp
     */
    @HandlesEvent("outcoming")
    public Resolution getAllOutcomingFlights() { 
        try {
            String ids = getContext().getRequest().getParameter("destination.id");
            DestinationTO[] dsts = restTemplate.getForObject(getUrl(), DestinationTO[].class);
            destinations = Arrays.asList(dsts);
            destination = restTemplate.getForObject(getUrl() + "/{id}", DestinationTO.class, Long.parseLong(ids));
            FlightTO[] fligs = restTemplate.getForObject(getUrl() + "/{id}/outcoming", FlightTO[].class, Long.parseLong(ids));
            flights = Arrays.asList(fligs); 
            getContext().getMessages().add(
                    new LocalizableMessage("destination.list.outcoming", 
                        escapeHTML(destination.getId().toString())));
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/destination/listOutcoming.jsp");
    }
    
    /**
     * Resolution that shows all the incoming flights for the selected destination.
     * 
     * @return redirect to /destination/listIncoming.jsp
     */
    @HandlesEvent("incoming")
    public Resolution getAllIncomingFlights() {      
        try {
            String ids = getContext().getRequest().getParameter("destination.id");
            DestinationTO[] dsts = restTemplate.getForObject(getUrl(), DestinationTO[].class);
            destinations = Arrays.asList(dsts);
            destination = restTemplate.getForObject(getUrl() + "/{id}", DestinationTO.class, Long.parseLong(ids));
            FlightTO[] fligs = restTemplate.getForObject(getUrl() + "/{id}/incoming", FlightTO[].class, Long.parseLong(ids));
            flights = Arrays.asList(fligs); 
            getContext().getMessages().add(
                    new LocalizableMessage("destination.list.incoming",
                        escapeHTML(destination.getId().toString())));
        } catch (RestClientException ex) {
            LocalizableError err = new LocalizableError("destination.error.rest", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("destination.error.unknown", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/destination/listIncoming.jsp");
    }
    
    /**
     * Simple resolution that redirects jsp page.
     * 
     * @return redirect to /destination/list.jsp
     */
    public Resolution cancel(){
        return new RedirectResolution(this.getClass());
    }
    
    /**
     * Simple resolution that redirects jsp page.
     * 
     * @return redirect to /destination/edit.jsp
     */
    public Resolution edit() {
        log.debug("edit() destination={}", destination);
        return new ForwardResolution("/destination/edit.jsp");
    }
}
