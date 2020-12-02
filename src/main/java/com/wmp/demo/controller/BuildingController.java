package com.wmp.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.wmp.demo.dao.BuildingDao;
import com.wmp.demo.dao.RoomDao;
import com.wmp.demo.dto.BuildingDto;
import com.wmp.demo.model.Building;
import com.wmp.demo.model.Room;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    private final BuildingDao buildingDao;
    private final RoomDao roomDao;

    public BuildingController(BuildingDao buildingDao, RoomDao roomDao) {
        this.buildingDao = buildingDao;
        this.roomDao = roomDao;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto) {
        // HeaterDto must always contain the heater room
        Set<Room> rooms = new HashSet<Room>();
        if (dto.getRoomIds() != null) {
            rooms.addAll(roomDao.findAllById(dto.getRoomIds()));
        }
        Building newBuilding = new Building(dto.getName(), rooms, dto.getAddress());
        for (Room room : rooms) {
            room.setBuilding(newBuilding);
            roomDao.save(room);
        }
        return new BuildingDto(buildingDao.saveAndFlush(newBuilding));
    }

    @PutMapping(path = "/{id}")
    public BuildingDto update(@RequestBody BuildingDto dto, @PathVariable Long id) {
        Building building = buildingDao.findById(id).orElseThrow(IllegalArgumentException::new);
        Set<Room> rooms = new HashSet<Room>();
        if (dto.getRoomIds() != null) {
            rooms.addAll(roomDao.findAllById(dto.getRoomIds()));
        }
        for (Room room : rooms) {
            room.setBuilding(building);
            roomDao.save(room);
        }
        building.setAddress(dto.getAddress());
        building.setName(dto.getName());
        building.setRooms(rooms);
        return new BuildingDto(buildingDao.save(building));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        buildingDao.deleteById(id);
    }    
}
