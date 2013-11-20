package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import static cz.muni.fi.pa165.airportmanager.web.beans.BaseActionBean.escapeHTML;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    @ValidateNestedProperties(value = {
        @Validate(on = {"add", "save"}, field = "name", required = true),
        @Validate(on = {"add", "save"}, field = "type", required = true),
        @Validate(on = {"add", "save"}, field = "capacity", required = true)
    })
    private AirplaneTO airplane;

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
        System.out.println("list called");
        log.debug("list()");
        airplanes = airplaneService.getAllAirplanes();
        return new ForwardResolution("/airplane/list.jsp");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        airplanes = airplaneService.getAllAirplanes();
        //return null to let the event handling continue
        return null;
    }

    public Resolution delete() {
        System.out.println("delete called");
        log.debug("delete({})", airplane.getId());
        airplaneService.removeAirplane(airplane);
        getContext().getMessages().add(new LocalizableMessage("airplane.deleted",airplane.getCapacity(),escapeHTML(airplane.getName()),escapeHTML(airplane.getType())));
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadAirplaneFromDatabase() {
        String ids = getContext().getRequest().getParameter("airplane.id");
        if (ids == null) return;
        airplane = airplaneService.getAirplane(Long.parseLong(ids));
    }

    public Resolution add() {
        System.out.println("add called");
        log.debug("add() airplane", airplane);
        airplaneService.createAirplane(airplane);
        getContext().getMessages().add(new LocalizableMessage("airplane.created",airplane.getCapacity(),escapeHTML(airplane.getName()),escapeHTML(airplane.getType())));
        return new RedirectResolution(this.getClass(), "list");
    }

    public Resolution edit() {
        System.out.println("edit called");
        log.debug("edit() airplane={}", airplane);
        return new ForwardResolution("/airplane/edit.jsp");
    }

    public Resolution save() {
        System.out.println("save called");
        log.debug("save() airplane={}", airplane);
        airplaneService.updateAirplane(airplane);
        getContext().getMessages().add(new LocalizableMessage("airplane.updated",airplane.getCapacity(),escapeHTML(airplane.getName()),escapeHTML(airplane.getType())));
        return new RedirectResolution(this.getClass(), "list");
    }

    
}
