/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.entities.to;

import cz.muni.fi.pa165.airportmanager.backend.entities.Flight;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Samo
 */
public class DestinationTO {
    private Long id;
    private String code;
    private String country;
    private String city;
    private List<Flight> incoming;
    private List<Flight> outcoming;

    public List<Flight> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<Flight> incoming) {
        this.incoming = incoming;
    }

    public List<Flight> getOutcoming() {
        return outcoming;
    }

    public void setOutcoming(List<Flight> outcoming) {
        this.outcoming = outcoming;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.code);
        hash = 67 * hash + Objects.hashCode(this.country);
        hash = 67 * hash + Objects.hashCode(this.city);
        hash = 67 * hash + Objects.hashCode(this.incoming);
        hash = 67 * hash + Objects.hashCode(this.outcoming);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DestinationTO other = (DestinationTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.incoming, other.incoming)) {
            return false;
        }
        if (!Objects.equals(this.outcoming, other.outcoming)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "DestinationTO{" + "id=" + id + ", code=" + code + ", country=" + country + ", city=" + city + ", incoming=" + incoming + ", outcoming=" + outcoming + '}';
    }
    
    
    
}
