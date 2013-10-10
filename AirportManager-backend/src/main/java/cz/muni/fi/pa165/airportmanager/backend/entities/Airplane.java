/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.backend.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author Chorke
 */
@Entity
@NamedQuery(
            name="Airplane.findAllAirplanes",
            query="SELECT a FROM Airplane a"
    )
public class Airplane implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int capacity;
    
    @Column(length = 50)
    private String name;
    
    @Column(length = 50)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if(capacity < 0){
            throw new IllegalArgumentException("Capacity is negative.");
        }
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(this.id == null 
                || object == null
                || !(object instanceof Airplane)){
            return false;
        }
        Airplane ap = (Airplane)object;
        if(ap.getId() == null){
            return false;
        }
        return ap.getId().equals(this.id);
    }

    @Override
    public String toString() {
        return "Airplane {" + id + "}"
                + "[name = " + name + ", type = " + type + ", capacity = " + capacity + "]";
    }
    
}
