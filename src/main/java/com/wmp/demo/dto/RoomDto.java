package com.wmp.demo.dto;

import java.util.HashSet;
import java.util.Set;

import com.wmp.demo.model.Heater;
import com.wmp.demo.model.Room;
import com.wmp.demo.model.Window;

import org.springframework.stereotype.Component;


@Component
public class RoomDto {
    private Long id;
    private String name;
    private Integer floor;
    private Double currentTemperature;
    private Double targetTemperature;
    private Set<Long> heaterIds = new HashSet<Long>();
    private Set<Long> windowIds = new HashSet<Long>();
    private Long buildingId;

    public RoomDto() {}

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.floor = room.getFloor();
        this.currentTemperature = room.getCurrentTemperature();
        this.targetTemperature = room.getTargetTemperature();
        for (Heater heater : room.getHeaters()) {
            heaterIds.add(heater.getId());
        }
        for (Window window : room.getWindows()) {            
            windowIds.add(window.getId());
        }
        if (null != room.getBuilding()) {
            this.buildingId = room.getBuilding().getId();
        }
        
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    public Integer getFloor() {
        return floor;
    }
    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
    public Double getCurrentTemperature() {
        return currentTemperature;
    }
    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
    public Double getTargetTemperature() {
        return targetTemperature;
    }
    public void setHeaterIds(Set<Long> heaterIds) {
        this.heaterIds = heaterIds;
    }
    public Set<Long> getHeaterIds() {
        return heaterIds;
    }
    public void setWindowIds(Set<Long> windowIds) {
        this.windowIds = windowIds;
    }
    public Set<Long> getWindowIds() {
        return windowIds;
    }
    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
    public Long getBuildingId() {
        return buildingId;
    }
}
