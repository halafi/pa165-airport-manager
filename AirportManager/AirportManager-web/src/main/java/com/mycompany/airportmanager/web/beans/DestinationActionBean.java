package com.mycompany.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
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
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Filip
 */
@UrlBinding("/destinations/{$event}/{destination.id}")
public class DestinationActionBean extends BaseActionBean {
    final static Logger log = LoggerFactory.getLogger(DestinationActionBean.class);

    @SpringBean //Spring can inject even to private and protected fields
    protected DestinationLibrary destinationLibrary;

    //--- part for showing a list of books ----
    private List<Destination> destinations;

    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        destinations = destinationLibrary.getAllDestinations();
        return new ForwardResolution("/destination/list.jsp");
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    //--- part for adding a book ----

    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "country", required = true),
            @Validate(on = {"add", "save"}, field = "city", required = true),
            @Validate(on = {"add", "save"}, field = "code", required = true, minvalue = 800)
    })
    private Destination destination;

    public Resolution add() {
        log.debug("add() destination={}", destination);
        destinationLibrary.createDestination(destination);
        getContext().getMessages().add(new LocalizableMessage(""
                + "destination.add.message",escapeHTML(destination.getCountry()),
                escapeHTML(destination.getCity()),escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }

    //@Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        destinations = destinationLibrary.getAllDestinations();
        //return null to let the event handling continue
        return null;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    //--- part for deleting a book ----

    public Resolution delete() {
        log.debug("delete({})", destination.getId());
        //only id is filled by the form
        destination = destinationLibrary.getDestination(destination.getId());
        destinationLibrary.deleteDestination(destination.getId());
        getContext().getMessages().add(new LocalizableMessage("destination.delete.message",
                escapeHTML(destination.getCountry()),escapeHTML(escapeHTML(destination.getCity())),
                escapeHTML(destination.getCode())));
        return new RedirectResolution(this.getClass(), "list");
    }

    //--- part for editing a book ----

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadBookFromDatabase() {
        String ids = getContext().getRequest().getParameter("book.id");
        if (ids == null) return;
        destination = destinationLibrary.getDestination(Long.parseLong(ids));
    }

    public Resolution edit() {
        log.debug("edit() destination={}", destination);
        return new ForwardResolution("/destination/edit.jsp");
    }

    public Resolution save() {
        log.debug("save() destination={}", destination);
        destinationLibrary.updateBook(destination);
        return new RedirectResolution(this.getClass(), "list");
    }
}
