package daos;

import cz.muni.fi.pa165.airportmanager.backend.entities.Airplane;
import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;

/**
 *
 * @author Filip
 */
public interface AirplaneDAO {
    public void createAirplane(Airplane airplane);
    public void updateAirplane(Airplane airplane);
    public void removeAirplane(Airplane airplane);
    public Airplane getAirplane(Long id);
    public List<Airplane> getAllAirplanes();
    public List<Flight> getAllAirplanesFlights(Airplane airplane);
    
}
