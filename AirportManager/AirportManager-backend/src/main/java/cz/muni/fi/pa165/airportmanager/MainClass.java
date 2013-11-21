package cz.muni.fi.pa165.airportmanager;

import cz.muni.fi.pa165.airportmanager.backend.daos.StewardDAO;
import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Destination;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import cz.muni.fi.pa165.airportmanager.backend.entities.Steward;
import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.services.FlightService;
import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;


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
//        System.out.println(ap1);
//        AnnotationConfigApplicationContext con = new AnnotationConfigApplicationContext(Config.class);
//        con.register(StewardDAOImpl.class);
//        con.refresh();
        ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext("applicationContext.xml");
//        StewardDAO stewdao = con.getBean(StewardDAO.class);
//        Steward s = new Steward();
//        s.setFirstName("ja");
//        s.setLastName("ty");
//        s.setId(1L);
//        stewdao.createSteward(s);
        
        StewardService stewService = con.getBean(StewardService.class);
        AirplaneService airService = con.getBean(AirplaneService.class);
        FlightService flightService = con.getBean(FlightService.class);
        DestinationService desService = con.getBean(DestinationService.class);
        
        DestinationTO d1 = new DestinationTO();
        DestinationTO d2 = new DestinationTO();
        d1.setCity("pp1");
        d1.setCountry("sk1");
        d1.setCode("bla1");
        d2.setCity("pp2");
        d2.setCountry("sk2");
        d2.setCode("bla2");
        
        AirplaneTO a = new AirplaneTO();
        a.setCapacity(100);
        a.setName("moje");
        a.setType("747");
        
        long time = System.currentTimeMillis();
        
        FlightTO f1 = new FlightTO();
        FlightTO f2 = new FlightTO();
        FlightTO f3 = new FlightTO();
        FlightTO f4 = new FlightTO();
        FlightTO f5 = new FlightTO();
        f1.setAirplaneTO(a);
        f1.setOrigin(d1);
        f1.setTarget(d2);
        f1.setArrivalTime(new Timestamp(time + 100000));
        f1.setDepartureTime(new Timestamp(time));
        f2.setAirplaneTO(a);
        f2.setOrigin(d1);
        f2.setTarget(d2);
        f2.setArrivalTime(new Timestamp(time + 300000));
        f2.setDepartureTime(new Timestamp(time + 200000));
        f3.setAirplaneTO(a);
        f3.setOrigin(d1);
        f3.setTarget(d2);
        f3.setArrivalTime(new Timestamp(time + 500000));
        f3.setDepartureTime(new Timestamp(time + 400000));
        f4.setAirplaneTO(a);
        f4.setOrigin(d1);
        f4.setTarget(d2);
        f4.setArrivalTime(new Timestamp(time + 700000));
        f4.setDepartureTime(new Timestamp(time + 600000));
        f5.setAirplaneTO(a);
        f5.setOrigin(d1);
        f5.setTarget(d2);
        f5.setArrivalTime(new Timestamp(time + 900000));
        f5.setDepartureTime(new Timestamp(time + 800000));
        
        StewardTO s = new StewardTO();
        s.setFirstName("first");
        s.setLastName("last");
        
        List<StewardTO> ls = new ArrayList<>();
        List<StewardTO> empty = new ArrayList<>();
        ls.add(s);
        
        f1.setStewList(ls);
        f3.setStewList(ls);
        f2.setStewList(empty);
        f4.setStewList(empty);
        f5.setStewList(empty);
        
        desService.createDestination(d1);
        desService.createDestination(d2);
        
        airService.createAirplane(a);
        
        stewService.createSteward(s);
        
        flightService.createFlight(f1);
        flightService.createFlight(f2);
        flightService.createFlight(f3);
        flightService.createFlight(f4);
        flightService.createFlight(f5);
        
        FlightTO fl = stewService.getAllStewardsFlights(s).get(0);
        System.out.println("list stewards: " + fl.getStewList());
        fl.getStewList().remove(s);
        flightService.updateFlight(fl);
        System.out.println("list flights: " + stewService.getAllStewardsFlights(s));
//        StewardService service = con.getBean(StewardService.class);
//        StewardTO stew = new StewardTO();
//        stew.setFirstName("ja");
//        stew.setLastName("ty");
//        stew.setId(1L);
//        service.createSteward(stew);
//        
//        StewardTO finded = service.findSteward(stew.getId());
//        System.out.println(finded);
//        FlightService s = con.getBean(FlightServiceImpl.class);
//        DestinationService s = con.getBean(DestinationServiceImpl.class);
//        AirplaneService s = con.getBean(AirplaneServiceImpl.class);
//        System.out.println(service);
    }

    
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
