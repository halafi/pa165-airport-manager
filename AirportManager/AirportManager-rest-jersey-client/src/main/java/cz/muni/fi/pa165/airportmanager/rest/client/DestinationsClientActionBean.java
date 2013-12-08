package cz.muni.fi.pa165.airportmanager.rest.client;

import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
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
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Filip
 */
@UrlBinding("/destinations/{$event}/{destination.id}")
public class DestinationsClientActionBean extends BaseActionBean implements ValidationErrorHandler {
    
    final static Logger log = LoggerFactory.getLogger(DestinationsClientActionBean.class);
    
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
    
     public List<FlightTO> getFlights() {
        return flights;
    }
    
    public DestinationTO getDestination() {
        return destination;
    }

    public void setDestination(DestinationTO destination) {
        this.destination = destination;
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors ve) throws Exception {
        return null;
    }
    
    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        return new ForwardResolution("/destination/list.jsp");
    }
}
