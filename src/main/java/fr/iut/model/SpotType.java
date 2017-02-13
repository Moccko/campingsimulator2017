package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class SpotType {
    private String label;
    private int id;
    private Collection<Spot> spotsById;

    @Basic
    @Column(name = "label", nullable = false, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotType spotType = (SpotType) o;

        if (id != spotType.id) return false;
        if (label != null ? !label.equals(spotType.label) : spotType.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    @OneToMany(mappedBy = "spotTypeBySpotTypeId")
    public Collection<Spot> getSpotsById() {
        return spotsById;
    }

    public void setSpotsById(Collection<Spot> spotsById) {
        this.spotsById = spotsById;
    }
}
