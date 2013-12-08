package cz.muni.fi.pa165.airportmanager.rest.client;

/**
 *
 * @author Filip
 */
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RESTClient {
    
    /*public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/pa165/airport-manager-web/rest-jersey-server/destination/");
        WebTarget resourceWebTarget = webTarget.path("customers/json/1");
        
        Invocation.Builder invocationBuilder =
        resourceWebTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("accept", "application/json");
        
        Response response = invocationBuilder.get();
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));
   }   */
}
