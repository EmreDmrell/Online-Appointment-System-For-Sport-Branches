package edu.mondragon.pbl.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 20)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "branch_code", nullable = false, length = 4, unique = true)
    private String branchCode;

    @JoinColumn(name = "city", nullable = false)
    @ManyToOne(targetEntity=City.class, fetch=FetchType.EAGER)
    private City city;

    @Column(name = "address", nullable = false)
    private String address;

    @JoinColumn(name = "sport")
    @ManyToMany(targetEntity=Sport.class, fetch=FetchType.EAGER)
    private List<Sport> sports;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
    
}
