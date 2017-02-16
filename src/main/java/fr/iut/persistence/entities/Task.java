package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "TASK")
public class Task {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int idTask;

    @Column(nullable = false)
    private Timestamp starttime;

    @Column(nullable = false)
    private Timestamp endtime;

    @Column(nullable = false)
    private String label;

    @ManyToOne(optional = false)
    private Employee employee;

    public int getIdTask() {
        return idTask;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public String getLabel() {
        return label;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
