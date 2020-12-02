package com.wmp.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Heater {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    // @Column
    private Long power;

    @ManyToOne
    private Room room;

    @Enumerated(EnumType.STRING)
    private HeaterStatus heaterStatus;

    public Heater() {}

    public Heater(Room room, String name, HeaterStatus status, Long power) {
        this.heaterStatus = status;
        this.name = name;
        this.room = room;
        this.power = power;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPower() {
        return this.power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public enum HeaterStatus {
        ON,
        OFF
    }
}

