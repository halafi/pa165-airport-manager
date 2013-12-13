package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

import javax.persistence.PersistenceException;

/**
 *
 * @author Matus Makovy
 */
public class AirportDaoException extends PersistenceException {
    
    public AirportDaoException() {
    }

    public AirportDaoException(String msg) {
        super(msg);
    }
    
    public AirportDaoException(Throwable cause) {
        super(cause);
    }
    
    public AirportDaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
