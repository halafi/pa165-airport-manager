package cz.muni.fi.pa165.airportmanager.web.rest.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Juraj Duráni
 */
@Path("/destination")
public class DestinationRestApi {

    private static final ApplicationContext APP_CONFIG =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    @Autowired
    private DestinationService destService;
    private ObjectMapper mapper = new ObjectMapper();

    public DestinationRestApi() {
        destService = APP_CONFIG.getBean(DestinationService.class);
        System.out.println(destService);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDestinations() {
        try {
            return mapper.writerWithType(new TypeReference<List<DestinationTO>>() {})
                    .writeValueAsString(destService.getAllDestinations());
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDestination(@PathParam("id") Long id) {
        try {
            return mapper.writerWithType(new TypeReference<DestinationTO>() {})
                    .writeValueAsString(destService.getDestination(id));
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Throwable ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}/incoming")
    @Produces(MediaType.APPLICATION_JSON)
    public String getIncomingFlights(@PathParam("id") Long id) {
        try {
            DestinationTO des = destService.getDestination(id);
            return mapper.writerWithType(new TypeReference<List<FlightTO>>() {})
                    .writeValueAsString(destService.getAllIncomingFlights(des));
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Throwable ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}/outcoming")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOutcomintFlights(@PathParam("id") Long id) {
        try {
            DestinationTO des = destService.getDestination(id);
            return mapper.writerWithType(new TypeReference<List<FlightTO>>() {})
                    .writeValueAsString(destService.getAllOutcomingFlights(des));
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Throwable ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDestination(@PathParam("id") Long id) {
        try {
            DestinationTO des = destService.getDestination(id);
            destService.removeDestination(des);
            return Response.status(Status.NO_CONTENT).build();
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Throwable ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDestination(String destination) {
        try {
            DestinationTO des = mapper
                    .readValue(destination, new TypeReference<DestinationTO>() {});
            destService.createDestination(des);
            return mapper.writerWithType(new TypeReference<DestinationTO>() {})
                    .writeValueAsString(des);
        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(ex, Status.BAD_REQUEST);
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Throwable ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateDestination(String destination, @PathParam("id") Long id) {
        try {
            DestinationTO des = mapper
                    .readValue(destination, new TypeReference<DestinationTO>() {});
            des.setId(id);
            destService.updateDestination(des);
            return Response.status(Status.OK).build();
        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(ex, Status.BAD_REQUEST);
        } catch (DataAccessException ex) {
            throw new WebApplicationException(ex, Status.SERVICE_UNAVAILABLE);
        } catch (Throwable ex) {
            throw new WebApplicationException(ex, Status.INTERNAL_SERVER_ERROR);
        }
    }
}