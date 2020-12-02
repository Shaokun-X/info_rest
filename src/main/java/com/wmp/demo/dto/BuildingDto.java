package com.wmp.demo.dto;

import java.util.HashSet;
import java.util.Set;

import com.wmp.demo.model.Building;
import com.wmp.demo.model.Room;

import org.springframework.stereotype.Component;

@Component
public class BuildingDto {
    private Long id;
    private String name;
    private Set<Long> roomIds;
    private String address;

    public BuildingDto() {}
    
    public BuildingDto(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.address = building.getAddress();
        this.roomIds = new HashSet<Long>();
        for (Room room : building.getRooms()) {
            this.roomIds.add(room.getId());
        }
    }

    public Long getId() {
        return id;
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
    public Set<Long> getRoomIds() {
        return roomIds;
    }
    public void setRoomIds(Set<Long> roomIds) {
        this.roomIds = roomIds;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
