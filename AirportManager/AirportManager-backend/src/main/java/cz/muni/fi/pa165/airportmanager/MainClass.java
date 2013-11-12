package cz.muni.fi.pa165.airportmanager;

import cz.muni.fi.pa165.airportmanager.backend.daos.impl.AirplaneDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.DestinationDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.FlightDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.daos.impl.StewardDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.backend.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.backend.services.FlightService;
import cz.muni.fi.pa165.airportmanager.backend.services.StewardService;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.AirplaneServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.DestinationServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.services.impl.StewardServiceImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.backend.entities.to.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chorke
 */
public class MainClass {
//      public static final EntityManagerFactory EM_FACTORY = 
//            Persistence.createEntityManagerFactory("AirportManager");
    
    public static void main(String[] args){
        chorkeTest();
        //halafiTest();
    }
    
    private static void chorkeTest(){
//        EntityManager manager = EM_FACTORY.createEntityManager();
//        Airplane ap = new Airplane();
//        ap.setCapacity(10);
//        ap.setName("air");
//        ap.setType("boeing");
//        Airplane ap1 = new Airplane();
//        ap1.setCapacity(100);
//        ap1.setName("airplane excelent");
//        ap1.setType("boeing 747");
//        
//        
//        manager.getTransaction().begin();
//        manager.persist(ap);
//        manager.persist(ap1);
//        manager.getTransaction().commit();
//        
//        ap.setName("airplane");
//        manager.clear();
//        manager.close();
//        manager = EM_FACTORY.createEntityManager();
//        manager.getTransaction().begin();
//        manager.merge(ap);
//        manager.getTransaction().commit();
//        
//        manager.getTransaction().begin();
//        manager.remove(ap1);
//        manager.getTransaction().commit();
//        
//        System.out.println(ap1);
//        AnnotationConfigApplicationContext con = new AnnotationConfigApplicationContext(Config.class);
//        con.register(StewardDAOImpl.class);
//        con.refresh();
        ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AnnotationConfigApplicationContext con = 
//                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        StewardService service = con.getBean(StewardService.class);
        StewardDAO stewdao = con.getBean(StewardDAO.class);
        Steward s = new Steward();
        s.setFirstName("ja");
        s.setLastName("ty");
        s.setId(1L);
        stewdao.createSteward(s);
//        StewardTO stew = new StewardTO();
//        stew.setFirstName("ja");
//        stew.setLastName("ty");
//        stew.setId(1L);
//        service.createSteward(stew);
//        StewardTO finded = service.findSteward(stew.getId());
//        System.out.println(finded);
//        FlightService s = con.getBean(FlightServiceImpl.class);
//        DestinationService s = con.getBean(DestinationServiceImpl.class);
//        AirplaneService s = con.getBean(AirplaneServiceImpl.class);
//        System.out.println(service);
//        String s = "last name";
//        Long l = new Long(1);
//        Steward stew = new Steward();
//        stew.setLastName(s);
//        stew.setId(l);
//        System.out.println(s);
//        System.out.println(l);
//        System.out.println(stew.getLastName());
//        System.out.println(stew.getId());
//        s = "aaa";
//        System.out.println(s);
//        System.out.println(l);
//        System.out.println(stew.getLastName());
//        System.out.println(stew.getId());
//        ChorkeClass chc = new ChorkeClass();
//        ChorkeSubClass chsc = new ChorkeSubClass();
//        chsc.s = "first";
//        
//        chc.cl = chsc;
//        
//        ChorkeClass clazz = (ChorkeClass)chc.clone();
//        System.out.println(chc.cl.s);
//        System.out.println(clazz.cl.s);
//        
//        chc.cl.s = "second";
//        System.out.println(chc.cl.s);
//        System.out.println(clazz.cl.s);
    }
    
//    private static class ChorkeClass implements Cloneable{
//        ChorkeSubClass cl;
//
//        @Override
//        protected Object clone() {
//            try{
//                ChorkeClass c = (ChorkeClass) super.clone();
//                c.cl = this.cl;
//                return c;
//            } catch (CloneNotSupportedException ex){}
//            return null;
//        }
//        
//    }
//    
//    private static class ChorkeSubClass {
//        private String s;
//    }
    
    private static void halafiTest() {
//        EntityManager manager = EM_FACTORY.createEntityManager();
//        StewardDAO stewDAO = new StewardDAOImpl(EM_FACTORY);
//        //DestinationDAO destDAO = new DestinationDAOImpl(EM_FACTORY);
//        AirplaneDAO airplaneDAO = new AirplaneDAOImpl(EM_FACTORY);
//        FlightDAO flightDAO = new FlightDAOImpl(EM_FACTORY);
//        Airplane plane1 = newAirplane(700,"Jet3000","Passenger transport");
//        airplaneDAO.createAirplane(plane1);
//        Destination dest1 = newDestination("CZB","Czech Republic","Brno");
//        //destDAO.createDestination(dest1);
//        manager.getTransaction().begin();
//        manager.persist(dest1);
//        manager.getTransaction().commit();
//        manager.close();
//        Steward steward1 = newSteward("Elaine","Dickinson");
//        try {
//            stewDAO.createSteward(steward1);
//        } catch (JPAException ex) {
//            
//        } catch (IllegalArgumentException ex) {
//            
//        }
//        List<Steward> stewList = new ArrayList<Steward>();
//        stewList.add(steward1);
//        
//        Flight flight1 = newFlight(new Timestamp(100000),new Timestamp(500000),dest1,dest1,plane1,stewList);
//        flightDAO.createFlight(flight1);
    }
    
    /**
     * Constructor for Steward.
     */
    private static Steward newSteward(String firstName, String lastName) {
        Steward steward = new Steward();
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        return steward;
    }
    
    /**
     * Constructor for Flight.
     */
    private static Flight newFlight(Timestamp departureTime, 
            Timestamp arrivalTime, Destination origin, Destination target,
            Airplane airplane, List<Steward> stewardList) {
        Flight flight = new Flight();
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setOrigin(origin);
        flight.setTarget(target);
        flight.setAirplane(airplane);
        flight.setStewardList(stewardList);
        return flight;
    }
    
    /**
     * Constructor for Destination.
     */
    private static Destination newDestination(String code, String country, 
            String city) {
        Destination destination = new Destination();
        destination.setCode(code);
        destination.setCountry(country);
        destination.setCity(city);
        return destination;
    }
    
    /**
     * Constructor for Airplane.
     */
    private static Airplane newAirplane(int capacity, String name, String type) {
        Airplane plane = new Airplane();
        plane.setCapacity(capacity);
        plane.setName(name);
        plane.setType(type);
        return plane;
    }
}
