/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
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
        stewards = stewService.findAllStewards();
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution edit(){
        stewards = stewService.findAllStewards();
        return new ForwardResolution("/steward/list.jsp");
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
