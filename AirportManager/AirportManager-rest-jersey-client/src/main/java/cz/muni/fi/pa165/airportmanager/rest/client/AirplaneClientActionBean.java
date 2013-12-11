package cz.muni.fi.pa165.airportmanager.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private LocalizableError err;
    ObjectMapper mapper = new ObjectMapper();
    
    private static final String URL="http://localhost:8080/pa165/airport-manager-web/rest-jersey-server/airplane";

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
        
        AirplaneTO[] airplanesArray = restTemplate.getForObject(URL, AirplaneTO[].class);
        airplanes = Arrays.asList(airplanesArray);

        return new ForwardResolution("/airplane/list.jsp");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        
        AirplaneTO[] airplns = restTemplate.getForObject(URL, AirplaneTO[].class);
        airplanes = Arrays.asList(airplns);

        return null;
    }

    public Resolution delete() {

        restTemplate.delete(URL + "/delete/{id}", airplane.getId());
       
        return new RedirectResolution(this.getClass(), "list");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadAirplaneFromDatabase() {
        String ids = getContext().getRequest().getParameter("airplane.id");
        if (ids == null) {
            return;
        }
           airplane = restTemplate.getForObject(URL + "/{id}", AirplaneTO.class, Long.parseLong(ids));

    }

    public Resolution add() {

        log.debug("add() airplane", airplane);
        restTemplate.postForObject(URL + "/post", airplane, String.class);
        getContext().getMessages().add(new LocalizableMessage("airplane.created", airplane.getCapacity(), escapeHTML(airplane.getName()), escapeHTML(airplane.getType())));

        return new RedirectResolution(this.getClass(), "list.jsp");
    }

    public Resolution edit() {
        log.debug("edit() airplane={}", airplane);
        return new ForwardResolution("/airplane/edit.jsp");
    }

    public Resolution save() {

        log.debug("save() airplane={}", airplane);
        
        restTemplate.put(URL + "/put/{id}", airplane, airplane.getId());

        return new RedirectResolution(this.getClass(), "list");
    }

    @HandlesEvent("flightsOfPlane")
    public Resolution listFlights() {

        AirplaneTO[] airplns = restTemplate.getForObject(URL, AirplaneTO[].class);
        airplanes = Arrays.asList(airplns);
        
        FlightTO[] flightsArray = restTemplate.getForObject(URL + "/" + airplane.getId() + "/flights", FlightTO[].class); 
        flights = Arrays.asList(flightsArray);

        return new ForwardResolution("/airplane/listFlights.jsp");
    }
}
