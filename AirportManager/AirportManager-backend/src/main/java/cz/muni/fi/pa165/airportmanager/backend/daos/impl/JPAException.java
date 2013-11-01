/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.daos.impl;

/**
 *
 * @author matus
 */
public class JPAException extends Exception {
    
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
