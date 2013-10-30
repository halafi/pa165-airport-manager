package cz.muni.fi.pa165.airportmanager;

import cz.muni.fi.pa165.airportmanager.backend.JPAs.AirplaneDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.DestinationDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.FlightDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.JPAException;
import cz.muni.fi.pa165.airportmanager.backend.JPAs.StewardDAOImpl;
import cz.muni.fi.pa165.airportmanager.backend.daos.AirplaneDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.DestinationDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.FlightDAO;
import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
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
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author Chorke
 */
public class MainClass {
//    public static final EntityManagerFactory EM_FACTORY = 
//            Persistence.createEntityManagerFactory("AirportManager");
    
    public static void main(String[] args){
//        chorkeTest();
        //halafiTest();
    }
    
    private static void chorkeTest(){
//        EntityManager man = EM_FACTORY.createEntityManager();
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
//        man.getTransaction().begin();
//        man.persist(ap);
//        man.persist(ap1);
//        man.getTransaction().commit();
//        
//        ap.setName("airplane");
//        man.clear();
//        man.close();
//        man = EM_FACTORY.createEntityManager();
//        man.getTransaction().begin();
//        man.merge(ap);
//        man.getTransaction().commit();
//        
//        man.getTransaction().begin();
//        man.remove(ap1);
//        man.getTransaction().commit();
//        
//        System.out.println(ap1);
//        AnnotationConfigApplicationContext con = new AnnotationConfigApplicationContext(Config.class);
//        con.register(StewardDAOImpl.class);
//        con.refresh();
//        ApplicationContext con = new ClassPathXmlApplicationContext("applicationContext.xml");
//        StewardDAO s = con.getBean(StewardDAOImpl.class);
//        System.out.println(s);
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
//        EntityManager man = EM_FACTORY.createEntityManager();
//        StewardDAO stewDAO = new StewardDAOImpl(EM_FACTORY);
//        //DestinationDAO destDAO = new DestinationDAOImpl(EM_FACTORY);
//        AirplaneDAO airplaneDAO = new AirplaneDAOImpl(EM_FACTORY);
//        FlightDAO flightDAO = new FlightDAOImpl(EM_FACTORY);
//        Airplane plane1 = newAirplane(700,"Jet3000","Passenger transport");
//        airplaneDAO.createAirplane(plane1);
//        Destination dest1 = newDestination("CZB","Czech Republic","Brno");
//        //destDAO.createDestination(dest1);
//        man.getTransaction().begin();
//        man.persist(dest1);
//        man.getTransaction().commit();
//        man.close();
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
