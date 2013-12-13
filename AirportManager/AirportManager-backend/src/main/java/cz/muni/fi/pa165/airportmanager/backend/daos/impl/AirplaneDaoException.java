package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

import javax.persistence.PersistenceException;

/**
 *
 * @author Matus Makovy
 */
public class AirplaneDaoException extends PersistenceException {
    
    public AirplaneDaoException() {
    }

    public AirplaneDaoException(String msg) {
        super(msg);
    }
    
    public AirplaneDaoException(Throwable cause) {
        super(cause);
    }
    
    public AirplaneDaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
