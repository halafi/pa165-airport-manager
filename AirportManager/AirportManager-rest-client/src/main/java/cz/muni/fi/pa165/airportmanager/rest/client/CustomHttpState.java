package cz.muni.fi.pa165.airportmanager.rest.client;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.auth.AuthScope;

/**
 * Custom org.apache.commons.httpclient.HttpState to provide setter.
 *
 * @author Filip
 */
public class CustomHttpState extends HttpState {
    
    /**
     * Setter for credentials.
     * 
     * @param credentials 
     */
    public synchronized void setCredentials(Credentials credentials) {
        super.setCredentials(AuthScope.ANY, credentials);
    }
}