
package cz.muni.fi.pa165.airportmanager.web.userservice;

import cz.muni.fi.pa165.airportmanager.web.beans.BaseActionBean;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

/**
 *
 * @author Chorke
 */
public class UserActionBean extends BaseActionBean{
    
    @SpringBean
    private AirportManagerUserService userService;
    
    @Validate(required = true, on = {"register"})
    private String username;
    @Validate(required = true, on = {"register"})
    private String password;
    @Validate(required = true, on = {"register"})
    private String passwordconf;
    
    private boolean validationOK;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordconf() {
        return passwordconf;
    }

    public void setPasswordconf(String passwordconf) {
        this.passwordconf = passwordconf;
    }
    
    @Before(stages = LifecycleStage.EventHandling, on ={"register"})
    public void valid(){
        validationOK = true;
        if(!password.equals(passwordconf)){
            getContext().getValidationErrors().addGlobalError(
                    new LocalizableError("registration.passwords.not.equals"));
            validationOK = false;
        }
    }
    
    @DefaultHandler
    public Resolution form(){
        return new ForwardResolution("/registration.jsp");
    }
    
    @HandlesEvent("cancel")
    public Resolution cancelRegistration(){
        return new RedirectResolution("/");
    }
    
    @HandlesEvent("register")
    public Resolution registration(){
        if(validationOK){
            try{
                userService.addUser(username, password);
                getContext().getMessages().add(
                        new LocalizableMessage("registration.successful", username));
                return new RedirectResolution("/");
            } catch (UserAlreadyExistsException ex){
                getContext().getValidationErrors().add("username",
                        new LocalizableError("registration.username.exists", username));
            } catch (InvalidUsernameException ex){
                getContext().getValidationErrors().add("username",
                        new LocalizableError("registration.username.invalid", username));
            } catch (CannotCreateUserException ex){
                getContext().getValidationErrors().addGlobalError(
                        new LocalizableError("registration.error", 
                        ex.getCause() == null ? ex : ex.getCause()));
            }
        }
        return new ForwardResolution(this.getClass());
    }
}
