package cz.muni.fi.pa165.airportmanager.web.rest.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.exception.DataException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 *
 * @author Samo
 */
@Path("/airplane")
public class AirplaneRestApi {

    private static final XmlWebApplicationContext APP_CONF =
            new XmlWebApplicationContext();
    
    @Context
    private ServletContext context;
    private AirplaneService airService;
    private ObjectMapper mapper = new ObjectMapper();

    public AirplaneRestApi() {
        APP_CONF.setNamespace("spring-context");
    }
    
    private void initBeforeRequest() {
        APP_CONF.setServletContext(context);
        APP_CONF.refresh();
        airService = APP_CONF.getBean(AirplaneService.class);
        SecurityContextHolder.getContext().setAuthentication(DestinationRestApi.authentication);
    }
    
    private void destroyAfterRequest(){
        SecurityContextHolder.getContext().setAuthentication(null);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllAirplanes() {
        try{
            initBeforeRequest();
            List<AirplaneTO> airList = airService.getAllAirplanes();
            return mapper.writerWithType(new TypeReference<List<AirplaneTO>>() {}).writeValueAsString(airList);
        } catch (DataException ex){
            throw new WebApplicationException(ex, Response.Status.SERVICE_UNAVAILABLE);

        } catch (JsonProcessingException ex) {
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            destroyAfterRequest();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getAirplane(@PathParam("id") Long id){
        try{
            initBeforeRequest();
            AirplaneTO airplane = airService.getAirplane(id);
            return mapper.writeValueAsString(airplane);
        } catch (DataException ex){
            throw new WebApplicationException(ex, Response.Status.SERVICE_UNAVAILABLE);
        } catch (Exception ex) {
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            destroyAfterRequest();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/flights")
    public String getAirplanesFlights(@PathParam("id") Long id){
        try{
            initBeforeRequest();
            AirplaneTO airplane = airService.getAirplane(id);
            List<FlightTO> flightsList = airService.getAllAirplanesFlights(airplane);
            return mapper.writerWithType(new TypeReference<List<FlightTO>>() {}).writeValueAsString(flightsList);
        } catch (DataException ex){
            throw new WebApplicationException(ex, Response.Status.SERVICE_UNAVAILABLE);
        } catch (Exception ex){
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            destroyAfterRequest();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/post")
    public String postPlane(String json){
        try{
            initBeforeRequest();
            AirplaneTO airplane = mapper.readValue(json, new TypeReference<AirplaneTO>(){});
            airService.createAirplane(airplane);
            return mapper.writeValueAsString(airplane);
        } catch (DataException ex){
            throw new WebApplicationException(ex, Response.Status.SERVICE_UNAVAILABLE);
        } catch (JsonMappingException ex){
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        } catch (Exception ex){
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            destroyAfterRequest();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/put/{id}")
    public String putPlane(String json, @PathParam("id") Long id){
        try{
            initBeforeRequest();
            AirplaneTO airplane = mapper.readValue(json, new TypeReference<AirplaneTO>(){});
            airplane.setId(id);
            airService.updateAirplane(airplane);
            return mapper.writeValueAsString(airplane);
        } catch (DataException ex){
            throw new WebApplicationException(ex, Response.Status.SERVICE_UNAVAILABLE);
        } catch (JsonMappingException ex){
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        } catch (Exception ex){
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            destroyAfterRequest();
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public String deletePlane(@PathParam("id") Long id){
        try{
            initBeforeRequest();
            AirplaneTO airplane = airService.getAirplane(id);
            airService.removeAirplane(airplane);
            return mapper.writeValueAsString(airplane);
        } catch (DataException ex){
            throw new WebApplicationException(ex, Response.Status.SERVICE_UNAVAILABLE);
        } catch (JsonMappingException ex){
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        } catch (Exception ex){
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            destroyAfterRequest();
        }
    }
}
