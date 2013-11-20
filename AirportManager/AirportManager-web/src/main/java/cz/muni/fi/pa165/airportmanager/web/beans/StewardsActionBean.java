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
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

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
//    @ValidateNestedProperties() //TODO
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

    @DefaultHandler
    public Resolution list(){
        steward = new StewardTO();
        steward.setFirstName("first");
        steward.setLastName("last");
        stewService.createSteward(steward);
        stewards = stewService.findAllStewards();
        return new ForwardResolution("/steward/list.jsp");
    }
    
    public Resolution add(){
        System.out.println("add called");
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution edit(){
        System.out.println("edit called");
//        System.out.println(getContext().getRequest().getParameter("event"));
        return new ForwardResolution("/steward/edit.jsp");
    }
    
    public Resolution save(){
        System.out.println("save called");
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadSteward(){
        String id = getContext().getRequest().getParameter("steward.id");
        System.out.println(id);
        if(id == null){
            return;
        }
        steward = stewService.findSteward(Long.parseLong(id));
        System.out.println(steward);
    }
    
    public Resolution delete(){
        stewards = stewService.findAllStewards();
        return new ForwardResolution("/steward/list.jsp");
    }
    
    public Resolution flights(){
        flights = stewService.getAllStewardsFlights(steward);
        return new ForwardResolution("/steward/list.jsp");
    }
    
    public Resolution cancel(){
        return new RedirectResolution(this.getClass());
    }
}
