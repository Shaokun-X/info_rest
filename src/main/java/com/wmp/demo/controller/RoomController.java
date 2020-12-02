package com.wmp.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.wmp.demo.dao.BuildingDao;
import com.wmp.demo.dao.HeaterDao;
import com.wmp.demo.dao.RoomDao;
import com.wmp.demo.dao.WindowDao;
import com.wmp.demo.dto.HeaterDto;
import com.wmp.demo.dto.RoomDto;
import com.wmp.demo.dto.WindowDto;
import com.wmp.demo.model.Building;
import com.wmp.demo.model.Heater;
import com.wmp.demo.model.Room;
import com.wmp.demo.model.Window;
import com.wmp.demo.model.Heater.HeaterStatus;
import com.wmp.demo.model.Window.WindowStatus;

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
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final RoomDao roomDao;
    private final WindowDao windowDao;
    private final HeaterDao heaterDao;
    private final BuildingDao buildingDao;

    public RoomController(RoomDao roomDao, WindowDao windowDao, HeaterDao heaterDao, BuildingDao buildingDao) {
        this.roomDao = roomDao;
        this.windowDao = windowDao;
        this.heaterDao = heaterDao;
        this.buildingDao = buildingDao;
    }

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }    

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        Set<Heater> heaters = new HashSet<Heater>();
        Set<Window> windows = new HashSet<Window>();
        if (dto.getHeaterIds() != null) {
            heaters.addAll(heaterDao.findAllById(dto.getHeaterIds()));
        }
        if (dto.getWindowIds() != null) {
            windows.addAll(windowDao.findAllById(dto.getWindowIds()));
        }
        Room newRoom = new Room(dto.getName(), dto.getFloor(), heaters, windows);
        // building required
        Building building = buildingDao.findById(dto.getBuildingId()).orElseThrow(IllegalArgumentException::new);
        if(building != null) {
            Set<Room> buildingRooms = building.getRooms();
            buildingRooms.add(newRoom);
            building.setRooms(buildingRooms);
        }
        newRoom.setBuilding(building);
        return new RoomDto(roomDao.saveAndFlush(newRoom));
    }

    @PutMapping(path = "/{id}")
    public RoomDto update(@RequestBody RoomDto dto, @PathVariable Long id) {
        // stricly speaking, when no entity found, the PUT should be able to create a new one
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        Set<Heater> heaters = new HashSet<Heater>();
        Set<Window> windows = new HashSet<Window>();
        if (dto.getHeaterIds() != null) {
            heaters.addAll(heaterDao.findAllById(dto.getHeaterIds()));
        }
        if (dto.getWindowIds() != null) {
            windows.addAll(windowDao.findAllById(dto.getWindowIds()));
        }
        Building building = buildingDao.findById(dto.getBuildingId()).orElseThrow(IllegalArgumentException::new);
        room.setBuilding(building);
        room.setHeaters(heaters);
        room.setWindows(windows);
        room.setCurrentTemperature(dto.getCurrentTemperature());
        room.setTargetTemperature(dto.getTargetTemperature());
        room.setFloor(dto.getFloor());
        room.setName(dto.getName());
        return new RoomDto(roomDao.save(room));
    }    

    @PutMapping(path = "/{id}/switchWindow")
    public Set<WindowDto> switchWindows(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        Set<Window> windows = room.getWindows();
        Set<WindowDto> returnDtos = new HashSet<WindowDto>();
        for (Window window : windows) {
            window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED : WindowStatus.OPEN);
            returnDtos.add(new WindowDto(window));
            windowDao.save(window);
        }
        return returnDtos;
    }

    @PutMapping(path = "/{id}/switchHeater")
    public Set<HeaterDto> switchHeaters(@PathVariable Long id) {
        Room room = roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        Set<Heater> heaters = room.getHeaters();
        Set<HeaterDto> returnDtos = new HashSet<HeaterDto>();
        for (Heater heater : heaters) {
            heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF : HeaterStatus.ON);
            returnDtos.add(new HeaterDto(heater));
            heaterDao.save(heater);
        }
        return returnDtos;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        roomDao.deleteById(id);
    }    
}
