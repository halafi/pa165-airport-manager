package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
@UrlBinding("/airplane/{$event}/{airplane.id}")
public class AirplaneActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(AirplaneActionBean.class);
    
    @SpringBean
    protected AirplaneService airplaneService;
    private List<AirplaneTO> airplanes;
    private AirplaneTO airplane;

    public List<AirplaneTO> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<AirplaneTO> airplanes) {
        this.airplanes = airplanes;
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
        airplanes = airplaneService.getAllAirplanes();
        return new ForwardResolution("/airplane/list.jsp");
    }

    public Resolution add() {
        log.debug("add() airplane", airplane);
        airplaneService.createAirplane(airplane);
        return new RedirectResolution(this.getClass(), "list");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        airplanes = airplaneService.getAllAirplanes();
        return null;
    }
}
