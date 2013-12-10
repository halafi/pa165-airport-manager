package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

import javax.persistence.PersistenceException;

/**
 *
 * @author matus
 */
public class JPAException extends PersistenceException {
    public JPAException() {
    }

    public JPAException(String msg) {
        super(msg);
    }
    
    public JPAException(Throwable cause) {
        super(cause);
    }
    
    public JPAException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
