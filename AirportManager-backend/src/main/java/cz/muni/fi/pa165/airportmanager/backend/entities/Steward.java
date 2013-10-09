package cz.muni.fi.pa165.airportmanager.backend.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Matus Makovy
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Steward.findAllStewards", query = "SELECT s FROM Steward s"),
    @NamedQuery(name = "Steward.findAllStewardsFlights", query = "SELECT f FROM Flight f JOIN f.stewardList s WHERE s=:steward")
})

public class Steward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "stewardList")
    private List<Flight> flights;

    public Steward() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Steward{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + '}';
    }
}
