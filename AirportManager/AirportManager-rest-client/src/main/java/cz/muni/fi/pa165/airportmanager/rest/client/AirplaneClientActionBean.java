package cz.muni.fi.pa165.airportmanager.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import static cz.muni.fi.pa165.airportmanager.rest.client.BaseActionBean.escapeHTML;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
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
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Matus Makovy
 */
@UrlBinding("/airplanes/{$event}/{airplane.id}")
public class AirplaneClientActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(AirplaneClientActionBean.class);
    @SpringBean
    private RestTemplate restTemplate;
    private List<AirplaneTO> airplanes;
    private List<FlightTO> flights;
    @ValidateNestedProperties(value = {
        @Validate(on = {"add", "save"}, field = "name", required = true),
        @Validate(on = {"add", "save"}, field = "type", required = true),
        @Validate(on = {"add", "save"}, field = "capacity", required = true)
    })
    private AirplaneTO airplane;
    ObjectMapper mapper = new ObjectMapper();
    private static final String URL = "http://localhost:8080/pa165/airport-manager-web/rest-jersey-server/airplane";

    public List<FlightTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightTO> flights) {
        this.flights = flights;
    }

    public List<AirplaneTO> getAirplanes() {
        return airplanes;
    }

    public AirplaneTO getAirplane() {
        return airplane;
    }

    public void setAirplane(AirplaneTO airplane) {
        this.airplane = airplane;
    }

    @DefaultHandler
    public Resolution list() {
        log.debug("list()");

        try {
            AirplaneTO[] airplanesArray = restTemplate.getForObject(URL, AirplaneTO[].class);
            airplanes = Arrays.asList(airplanesArray);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new ForwardResolution("/airplane/list.jsp");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {

        try {
            AirplaneTO[] airplns = restTemplate.getForObject(URL, AirplaneTO[].class);
            airplanes = Arrays.asList(airplns);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return null;
    }

    public Resolution delete() {

        try {
            restTemplate.delete(URL + "/delete/{id}", airplane.getId());
            getContext().getMessages().add(new LocalizableMessage("airplane.deleted"));
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new RedirectResolution(this.getClass(), "list");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadAirplaneFromDatabase() {

        String ids = getContext().getRequest().getParameter("airplane.id");

        if (ids == null) {
            return;
        }

        try {
            airplane = restTemplate.getForObject(URL + "/{id}", AirplaneTO.class, Long.parseLong(ids));
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
    }

    public Resolution add() {

        log.debug("add() airplane", airplane);

        try {
            restTemplate.postForObject(URL + "/post", airplane, String.class);
            getContext().getMessages().add(new LocalizableMessage("airplane.created"));
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new RedirectResolution(this.getClass(), "list.jsp");
    }

    public Resolution edit() {
        log.debug("edit() airplane={}", airplane);

        return new ForwardResolution("/airplane/edit.jsp");
    }

    public Resolution save() {

        log.debug("save() airplane={}", airplane);

        try {
            restTemplate.put(URL + "/put/{id}", airplane, airplane.getId());
            getContext().getMessages().add(new LocalizableMessage("airplane.updated"));
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new RedirectResolution(this.getClass(), "list");
    }

    @HandlesEvent("flightsOfPlane")
    public Resolution listFlights() {

        try {
            AirplaneTO[] airplns = restTemplate.getForObject(URL, AirplaneTO[].class);
            airplanes = Arrays.asList(airplns);

            FlightTO[] flightsArray = restTemplate.getForObject(URL + "/" + airplane.getId() + "/flights", FlightTO[].class);
            flights = Arrays.asList(flightsArray);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("airplane.error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new ForwardResolution("/airplane/listFlights.jsp");
    }
}
