package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import static cz.muni.fi.pa165.airportmanager.web.beans.BaseActionBean.escapeHTML;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.ForwardResolution;
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
 * @author Matus Makovy
 */
@UrlBinding("/airplanes/{$event}/{airplane.id}")
public class AirplaneActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(AirplaneActionBean.class);
    @SpringBean
    protected AirplaneService airplaneService;
    private List<AirplaneTO> airplanes;
    private List<FlightTO> flights;
    @ValidateNestedProperties(value = {
        @Validate(on = {"add", "save"}, field = "name", required = true),
        @Validate(on = {"add", "save"}, field = "type", required = true),
        @Validate(on = {"add", "save"}, field = "capacity", required = true)
    })
    private AirplaneTO airplane;
    private LocalizableError err;

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
        err = null;

        try {
            airplanes = airplaneService.getAllAirplanes();
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new ForwardResolution("/airplane/list.jsp");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        try {
            airplanes = airplaneService.getAllAirplanes();
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        }

        return null;
    }

    public Resolution delete() {
        err = null;

        try {
            log.debug("delete({})", airplane.getId());
            airplaneService.removeAirplane(airplane);
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        }

        if (err == null) {
            getContext().getMessages().add(new LocalizableMessage("airplane.deleted", airplane.getCapacity(), escapeHTML(airplane.getName()), escapeHTML(airplane.getType())));
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
            airplane = airplaneService.getAirplane(Long.parseLong(ids));
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error" + ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        }
    }

    public Resolution add() {

        log.debug("add() airplane", airplane);
        err = null;

        try {
            airplaneService.createAirplane(airplane);
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error" + ex.getMessage());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error" + ex.getMessage());
            getContext().getValidationErrors().addGlobalError(err);
        }

        if (err == null) {
            getContext().getMessages().add(new LocalizableMessage("airplane.created", airplane.getCapacity(), escapeHTML(airplane.getName()), escapeHTML(airplane.getType())));
        }

        return new RedirectResolution(this.getClass(), "list.jsp");
    }

    public Resolution edit() {
        log.debug("edit() airplane={}", airplane);
        return new ForwardResolution("/airplane/edit.jsp");
    }

    public Resolution save() {

        log.debug("save() airplane={}", airplane);
        err = null;

        try {
            airplaneService.updateAirplane(airplane);
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error", ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error", ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        }
        if (err == null) {
            getContext().getMessages().add(new LocalizableMessage("airplane.updated", airplane.getCapacity(), escapeHTML(airplane.getName()), escapeHTML(airplane.getType())));
        }
        return new RedirectResolution(this.getClass(), "list");
    }

    @HandlesEvent("flightsOfPlane")
    public Resolution listFlights() {

        try {
            flights = airplaneService.getAllAirplanesFlights(airplane);
            airplanes = airplaneService.getAllAirplanes();
        } catch (DataAccessException ex) {
            err = new LocalizableError("airplane.error", ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            err = new LocalizableError("airplane.error", ex.toString());
            getContext().getValidationErrors().addGlobalError(err);
        }

        return new ForwardResolution("/airplane/listFlights.jsp");
    }
}
