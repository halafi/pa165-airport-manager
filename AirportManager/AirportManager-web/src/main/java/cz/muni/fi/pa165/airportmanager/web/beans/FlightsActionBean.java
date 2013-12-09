
package cz.muni.fi.pa165.airportmanager.web.beans;

import cz.muni.fi.pa165.airportmanager.services.AirplaneService;
import cz.muni.fi.pa165.airportmanager.services.DestinationService;
import cz.muni.fi.pa165.airportmanager.services.FlightService;
import cz.muni.fi.pa165.airportmanager.services.StewardService;
import cz.muni.fi.pa165.airportmanager.transferobjects.AirplaneTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.DestinationTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.FlightTO;
import cz.muni.fi.pa165.airportmanager.transferobjects.StewardTO;
import static cz.muni.fi.pa165.airportmanager.web.beans.BaseActionBean.escapeHTML;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Juraj Duráni
 */
@UrlBinding("/flig/{$event}/{flight.id}")
public class FlightsActionBean extends BaseActionBean {// implements ValidationErrorHandler {

    @SpringBean
    private FlightService flightService;
    @SpringBean
    private AirplaneService airService;
    @SpringBean
    private StewardService stewService;
    @SpringBean
    private DestinationService desService;
    private FlightTO flight;
    private List<FlightTO> flights;
    private List<DestinationTO> desList;
    private List<AirplaneTO> airList;
    private List<StewardTO> stewList;
    private boolean notexecute;

    private String arrtime;
    private String arrdate;
    private String deptime;
    private String depdate;
    private String target;
    private String origin;
    private String airplane;

    public List<DestinationTO> getDesList() {
        desList = desService.getAllDestinations();
        return desList;
    }

    public void setDesList(List<DestinationTO> desList) {
        this.desList = desList;
    }

    public List<AirplaneTO> getAirList() {
        airList = airService.getAllAirplanes();
        return airList;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public void setAirList(List<AirplaneTO> airList) {
        this.airList = airList;
    }

    public List<StewardTO> getStewList() {
        stewList = stewService.findAllStewards();
        return stewList;
    }

    public void setStewList(List<StewardTO> stewList) {
        this.stewList = stewList;
    }

    public String getArrtime() {
        return arrtime;
    }

    public void setArrtime(String arrtime) {
        this.arrtime = arrtime;
    }

    public String getArrdate() {
        return arrdate;
    }

    public void setArrdate(String arrdate) {
        this.arrdate = arrdate;
    }

    public String getDeptime() {
        return deptime;
    }

    public void setDeptime(String deptime) {
        this.deptime = deptime;
    }

    public String getDepdate() {
        return depdate;
    }

    public void setDepdate(String depdate) {
        this.depdate = depdate;
    }

    public FlightTO getFlight() {
        return flight;
    }

    public void setFlight(FlightTO flight) {
        this.flight = flight;
    }

    public List<FlightTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightTO> flights) {
        this.flights = flights;
    }

    @Before(stages = LifecycleStage.EventHandling, on = {"saveflight", "addflight"})
    public void valid() throws Exception {
        if (arrdate == null) {
            getContext().getValidationErrors().add("arrdate", 
                    new LocalizableError("validation.required.valueNotPresent", "arrdate"));
            notexecute = true;
        }
        if (arrtime == null) {
            getContext().getValidationErrors().add("arrtime", 
                    new LocalizableError("validation.required.valueNotPresent", "arrtime"));
            notexecute = true;
        }
        if (depdate == null) {
            getContext().getValidationErrors().add("depdate", 
                    new LocalizableError("validation.required.valueNotPresent", "depdate"));
            notexecute = true;
        }
        if (deptime == null) {
            getContext().getValidationErrors().add("arrdate", 
                    new LocalizableError("validation.required.valueNotPresent", "deptime"));
            notexecute = true;
        }
        if (origin == null) {
            getContext().getValidationErrors().add("origin", 
                    new LocalizableError("validation.required.valueNotPresent", "origin"));
            notexecute = true;
        }
        if (target == null) {
            getContext().getValidationErrors().add("target", 
                    new LocalizableError("validation.required.valueNotPresent", "target"));
            notexecute = true;
        }
        if (airplane == null) {
            getContext().getValidationErrors().add("airplane", 
                    new LocalizableError("validation.required.valueNotPresent", "airplane"));
            notexecute = true;
        }
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"saveflight",
        "editflight", "deleteflight"})
    public void loadFlight() {
        String id = getContext().getRequest().getParameter("flight.id");
        if (id == null || id.isEmpty()) {
//            System.out.println("flight id is null or epmty");
            return;
        }
        try {
            flight = flightService.getFlight(Long.parseLong(id));
        } catch (DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
    }

    @DefaultHandler
    @HandlesEvent("listflight")
    public Resolution listFlights() {
//        System.out.println("list called");
        try {
            flights = flightService.getAllFlights();
        } catch (DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new ForwardResolution("/flight/list.jsp");
    }

    @HandlesEvent("addflight")
    public Resolution addNewFlight() throws ParseException {
//        System.out.println("add fligth called");
//        System.out.println("arrTime: " + arrtime);
//        System.out.println("arrDate: " + arrdate);
//        System.out.println("depDate: " + depdate);
//        System.out.println("depTime: " + deptime);
//        System.out.println("target: " + target);
//        System.out.println("origin: " + origin);
//        System.out.println("airplane: " + airplane);
        if (notexecute) {
            return new ForwardResolution(this.getClass(), "createflight");
        }
        try {
            flight = new FlightTO();
            prepareFlight(getContext().getRequest().getLocale().getLanguage());
            flight.setStewList(new ArrayList<StewardTO>());
//        System.out.println(flight.getArrivalTime());
//        System.out.println(flight.getDepartureTime());
            flightService.createFlight(flight);
        } catch (DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("createflight")
    public Resolution createFlightFormular() {
        notexecute = false;
//        System.out.println("create formular called");
        return new ForwardResolution("/flight/create.jsp");
    }

    @HandlesEvent("saveflight")
    public Resolution updateFlight() throws ParseException {
//        System.out.println("save flight called");
        if (notexecute) {
            return new ForwardResolution(this.getClass(), "editflight");
        }
        try {
//        System.out.println("arrTime: " + arrTime);
//        System.out.println("arrDate: " + arrDate);
//        System.out.println("depDate: " + depDate);
//        System.out.println("depTime: " + depTime);
            prepareFlight(getContext().getRequest().getLocale().getLanguage());
        //System.out.println("actual list " + actualFlightStewList);
        //flight.setStewList(actualFlightStewList);
//        System.out.println(flight.getArrivalTime());
//        System.out.println(flight.getDepartureTime());
            flightService.updateFlight(flight);
        } catch (DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("deleteflight")
    public Resolution removeFlight() {
//        System.out.println("delete flight called");
//        loadFlight();
        try {
            flightService.removeFlight(flight);
        } catch (DataAccessException ex) {
            LocalizableError err = new LocalizableError("flight.error.service",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        } catch (Exception ex) {
            LocalizableError err = new LocalizableError("flight.error.uknown",
                    escapeHTML(ex.toString()));
            getContext().getValidationErrors().addGlobalError(err);
        }
        return new RedirectResolution(this.getClass(), "listflight");
    }

    @HandlesEvent("editflight")
    public Resolution edit() {
        notexecute = false;
//        System.out.println("edit flight called");
        String loc = getContext().getRequest().getLocale().getLanguage();
        arrdate = formatDateStamp(flight.getArrivalTime(), loc);
        arrtime = formatTimeStamp(flight.getArrivalTime(), loc);
        deptime = formatTimeStamp(flight.getDepartureTime(), loc);
        depdate = formatDateStamp(flight.getDepartureTime(), loc);
        //actualFlightStewList = flight.getStewList();
        //System.out.println("edit actual: " + actualFlightStewList);
//        System.out.println("arrTime: " + arrTime);
//        System.out.println("arrDate: " + arrDate);
//        System.out.println("depDate: " + depDate);
//        System.out.println("depTime: " + depTime);
        loadFlight();
        return new ForwardResolution("/flight/edit.jsp");
    }

    @HandlesEvent("cancelflight")
    public Resolution doNothing() {
//        System.out.println("cancel flight called");
        return new RedirectResolution(this.getClass());
    }

    private String formatTimeStamp(Timestamp ts, String lang) {
//        System.out.println(lang);
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(ts.getTime());
        String s = "";
        if (lang.equals("sk") || lang.equals("cs") || lang.equals("cz")) {
            if (cal.get(Calendar.HOUR_OF_DAY) < 10) {
                s += "0";
            }
            s += cal.get(Calendar.HOUR_OF_DAY) + ":";
            if (cal.get(Calendar.MINUTE) < 10) {
                s += "0";
            }
            s += cal.get(Calendar.MINUTE);
            return s;
        }
        if (cal.get(Calendar.HOUR) < 10) {
            s += "0";
        }
        s += cal.get(Calendar.HOUR) + ":";
        if (cal.get(Calendar.MINUTE) < 10) {
            s += "0";
        }
        s += cal.get(Calendar.MINUTE);
        if (cal.get(Calendar.AM_PM) == cal.get(Calendar.AM)) {
            return s + " AM";
        }
        return s + " PM";
    }

    private String formatDateStamp(Timestamp ts, String lang) {
//        System.out.println(lang);
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(ts.getTime());
        if (lang.equals("sk") || lang.equals("cs") || lang.equals("cz")) {
            return cal.get(Calendar.DATE) + ". "
                    + (cal.get(Calendar.MONTH) + 1) + ". "
                    + cal.get(Calendar.YEAR);
        }
        return (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DATE) + "/"
                + cal.get(Calendar.YEAR);
    }

    public Timestamp stringToTimestampSK(String string) throws ParseException {
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date lFromDate1 = datetimeFormatter1.parse(string);
        return new Timestamp(lFromDate1.getTime());
    }

    public Timestamp stringToTimestampEN(String string) throws ParseException {
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        Date lFromDate1 = datetimeFormatter1.parse(string);
        return new Timestamp(lFromDate1.getTime());
    }

    private void prepareFlight(String lang) throws ParseException {
        if (lang.equals("sk") || lang.equals("cs") || lang.equals("cz")) {
            flight.setArrivalTime(stringToTimestampSK(arrdate + " " + arrtime));
            flight.setDepartureTime(stringToTimestampSK(depdate + " " + deptime));
        } else {
            flight.setArrivalTime(stringToTimestampEN(arrdate + " " + arrtime));
            flight.setDepartureTime(stringToTimestampEN(depdate + " " + deptime));
        }
//        List<StewardTO> s = getStewList();
//        List<DestinationTO> d = getDesList();
//        List<AirplaneTO> a = getAirList();
        flight.setAirplaneTO(airService.getAirplane(getIdOfEntity(airplane)));
        flight.setTarget(desService.getDestination(Long.parseLong(target)));
        flight.setOrigin(desService.getDestination(Long.parseLong(origin)));
    }

    private Long getIdOfEntity(String toParse) {
//        System.out.println("String: " + toParse);
        String[] s = toParse.split("[()]+");
//        System.out.println(Arrays.toString(s));
        return Long.valueOf(s[s.length - 1]);
    }

//    @Override
//    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
//        System.out.println("valiiiiiiiiiiiiiiiiiiiiiid");
//        return null;
//    }
}
