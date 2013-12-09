/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.rest.server;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.hibernate.exception.DataException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Samo
 */
@Path("/airplane")
public class AirplaneRestApi {

    private static final ApplicationContext appConfig = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
    
    private AirplaneService airService;
    private ObjectMapper mapper = new ObjectMapper();

    public AirplaneRestApi() {
        airService = appConfig.getBean(AirplaneService.class);
        System.out.println(airService);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllAirplanes() {
        try{
            List<AirplaneTO> airList = new ArrayList<AirplaneTO>();
            airList = airService.getAllAirplanes();
            return mapper.writerWithType(new TypeReference<List<AirplaneTO>>() {}).writeValueAsString(airList);
        } catch (DataException ex){
//            return Response.status(401).build();
            return null;
        } catch (Exception ex){
            return null;
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getAirplane(@PathParam("id") Long id){
        AirplaneTO airplane = airService.getAirplane(id);
        try {
            return mapper.writeValueAsString(airplane);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/flights")
    public String getAirplanesFlights(@PathParam("id") Long id){
        try{
            AirplaneTO airplane = airService.getAirplane(id);
            List<FlightTO> flightsList = airService.getAllAirplanesFlights(airplane);
            return mapper.writerWithType(new TypeReference<List<FlightTO>>() {}).writeValueAsString(flightsList);
        } catch (Exception ex){
            return null;
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public String postPlane(String json){
        try {
            AirplaneTO airplane = mapper.readValue(json, new TypeReference<AirplaneTO>(){});
            airService.createAirplane(airplane);
            return airplane.toString();
        } catch (Exception ex) {
            return null;
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/put/{id}")
    public String putPlane(String json, @PathParam("id") Long id){
        try {
            AirplaneTO airplane = airService.getAirplane(id);
            airplane = mapper.readValue(json, new TypeReference<AirplaneTO>(){});
            airplane.setId(id);
            airService.updateAirplane(airplane);
            return airplane.toString();
        } catch (Exception ex) {
            return null;
        }
    }
    
    @DELETE
    @Path("/delete/{id}")
    public String deletePlane(@PathParam("id") Long id){
        try{
            AirplaneTO airplane = airService.getAirplane(id);
            airService.removeAirplane(airplane);
            return airplane.toString();
        } catch(Exception ex) {
            return null;
        }
    }
}
