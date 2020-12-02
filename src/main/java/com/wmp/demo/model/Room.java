package com.wmp.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Integer floor;

    // @Column
    private Double currentTemperature;

    // @Column
    private Double targetTemperature;

    @OneToMany(mappedBy = "room")
    private Set<Heater> heaters;

    @OneToMany(mappedBy = "room")
    private Set<Window> windows;

    @ManyToOne
    private Building building;

    public Room() {}

    public Room(String name, Integer floor, Set<Heater> heaters, Set<Window> windows) {
        this.name = name;
        this.floor = floor;
        this.heaters = heaters;
        this.windows = windows;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    
    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double temp) {
        currentTemperature = temp;
    }
    
    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double temp) {
        targetTemperature = temp;
    }

    public Set<Heater> getHeaters() {
        return heaters;
    }

    public void setHeaters(Set<Heater> heaters) {
        this.heaters = heaters;
    }
    
    public Set<Window> getWindows() {
        return windows;
    }

    public void setWindows(Set<Window> windows) {
        this.windows = windows;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}
