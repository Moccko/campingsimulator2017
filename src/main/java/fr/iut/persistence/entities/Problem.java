package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "PROBLEM")
public class Problem {

    /**
     * Problem's id
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    /**
     * Problem's description.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Problem's date and time of appearance.
     */
    @Column(nullable = false)
    private Timestamp appearanceDatetime;

    /**
     * Problem's date and time of solution.
     */
    @Column
    private Timestamp solutionDatetime;

    /**
     * Locations affected by the problem.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "problems",
            targetEntity = Location.class
    )
    private Set<Location> locations = new HashSet<>();

    /**
     * Clients affected by the problems.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "problems",
            targetEntity = Client.class
    )
    private Set<Client> clients = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getAppearanceDatetime() {
        return appearanceDatetime;
    }

    public Timestamp getSolutionDatetime() {
        return solutionDatetime;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppearanceDatetime(Timestamp appearanceDatetime) {
        this.appearanceDatetime = appearanceDatetime;
    }

    public void setSolutionDatetime(Timestamp solutionDatetime) {
        this.solutionDatetime = solutionDatetime;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public boolean isSolved(){
        return solutionDatetime != null;
    }
}
