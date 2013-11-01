/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.services;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
public class ServiceDataAccessException extends DataAccessException {

    
    /**
     * Constructs an instance of
     * <code>ServiceDataAccessException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceDataAccessException(String msg) {
        super(msg);
    }

    public ServiceDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
