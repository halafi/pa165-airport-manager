/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
@UrlBinding("/stewards/{$event}/{steward.id}")
public class StewardsActionBean extends BaseActionBean{
    
    @SpringBean
    private StewardService stewService;
    
    private List<StewardTO> stewards;
    private List<FlightTO> flights;
    @ValidateNestedProperties({
            @Validate(on = {"add", "save"}, field = "firstName", 
                    required = true, trim = true, minlength = 1),
            @Validate(on = {"add", "save"}, field = "lastName", 
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

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadSteward(){
        String id = getContext().getRequest().getParameter("steward.id");
        if(id == null){
            return;
        }
        steward = stewService.findSteward(Long.parseLong(id));
    }
    
    @DefaultHandler
    public Resolution list(){
        try{
            stewards = stewService.findAllStewards();
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing " + ex);
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error" + ex);
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/steward/list.jsp");
    }
    
    public Resolution add(){
        try{
            stewService.createSteward(steward);
            getContext().getMessages().add(new SimpleMessage("added steward", escapeHTML(steward.toString())));
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution edit(){
        System.out.println("edit called");
        return new ForwardResolution("/steward/edit.jsp");
    }
    
    public Resolution save(){
        try{
            stewService.updateSteward(steward);
            getContext().getMessages().add(new SimpleMessage("updated steward", escapeHTML(steward.toString())));
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution delete(){
        try{
            stewService.removeSteward(steward);
        } catch (DataAccessException ex){
            SimpleError err = new SimpleError("Error service providing ", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex){
            SimpleError err = new SimpleError("Unknown error", escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(),"list");
    }
    
    public Resolution flights(){
        //TODO
        flights = stewService.getAllStewardsFlights(steward);
        return new ForwardResolution("/steward/list.jsp");
    }
    
    public Resolution cancel(){
        return new RedirectResolution(this.getClass());
    }
}
