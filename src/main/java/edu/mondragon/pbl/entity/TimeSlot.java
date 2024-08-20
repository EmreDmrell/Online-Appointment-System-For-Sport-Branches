package edu.mondragon.pbl.entity;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 20)
    private Integer id;

    @Column(name = "date", nullable = false, length = 8)
    private String date;

    @ElementCollection
    @Column(name = "time", nullable = false)
    private List<Integer> time;

    @JoinColumn(name = "sport", nullable = false)
    @ManyToOne(targetEntity=Sport.class, fetch=FetchType.EAGER)
    private Sport sport;

    @JoinColumn(name = "branch", nullable = false)
    @ManyToOne(targetEntity=Branch.class, fetch=FetchType.EAGER)
    private Branch branch;

    @JoinColumn(name = "user", nullable = false)
    @ManyToOne(targetEntity=User.class, fetch=FetchType.EAGER)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
