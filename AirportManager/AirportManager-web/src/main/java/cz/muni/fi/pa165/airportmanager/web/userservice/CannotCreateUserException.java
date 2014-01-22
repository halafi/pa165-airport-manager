
package cz.muni.fi.pa165.airportmanager.web.userservice;

/**
 *
 * @author Chorke
 */
public class CannotCreateUserException extends RuntimeException{

    public CannotCreateUserException(String message) {
        super(message);
    }

    public CannotCreateUserException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
