/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.rest.server;

import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.hibernate.exception.DataException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Chorke
 */
@Path("/destination")
public class DestinationRestApi {
    
    private static final ApplicationContext appConfig = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
    
    private DestinationService destService;

    public DestinationRestApi() {
        destService = appConfig.getBean(DestinationService.class);
        System.out.println(destService);
    }
    
    @GET
    public Response getAllDestinations(){
        try{
             return Response.ok(destService.getAllDestinations()).build();
        } catch (DataException ex){
            return Response.status(Status.NOT_FOUND).entity(ex).build();
        } catch (Throwable ex){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getAllDestinations(@PathParam("id") Long id){
        try{
             return Response.ok(destService.getDestination(id)).build();
        } catch (DataException ex){
            return Response.status(Status.NOT_FOUND).entity(ex).build();
        } catch (Throwable ex){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteDestination(@PathParam("id") Long id){
        try{
            DestinationTO des = destService.getDestination(id);
            destService.removeDestination(des);
            return Response.ok().build();
        } catch (DataException ex){
            return Response.status(Status.NOT_FOUND).entity(ex).build();
        } catch (Throwable ex){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }
}
