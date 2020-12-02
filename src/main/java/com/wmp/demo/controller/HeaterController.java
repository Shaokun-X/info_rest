package com.wmp.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.wmp.demo.dao.HeaterDao;
import com.wmp.demo.dao.RoomDao;
import com.wmp.demo.dto.HeaterDto;
import com.wmp.demo.model.Heater;
import com.wmp.demo.model.Room;
import com.wmp.demo.model.Heater.HeaterStatus;

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
@RequestMapping("/api/heaters")
@Transactional // because Hibernate you cannot execute a query outside of a transaction
public class HeaterController {
    private final HeaterDao heaterDao;
    private final RoomDao roomDao;

    public HeaterController(HeaterDao heaterDao, RoomDao roomDao) {
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
    }

    @GetMapping
    public List<HeaterDto> findAll() {
        return heaterDao.findAll().stream().map(HeaterDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public HeaterDto findById(@PathVariable Long id) {
        return heaterDao.findById(id).map(HeaterDto::new).orElse(null);
    }

    @PutMapping(path = "/{id}/switch")
    public HeaterDto switchStatus(@PathVariable Long id) {
        Heater heater = heaterDao.findById(id).orElseThrow(IllegalArgumentException::new);
        heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF : HeaterStatus.ON);
        return new HeaterDto(heaterDao.saveAndFlush(heater));
    }

    @PutMapping(path = "/{id}")
    public HeaterDto update(@RequestBody HeaterDto dto, @PathVariable Long id) {
        Heater heater = heaterDao.findById(id).orElseThrow(IllegalArgumentException::new);
        heater.setHeaterStatus(dto.getHeaterStatus());
        heater.setName(dto.getName());
        heater.setPower(dto.getPower());
        heater.setRoom(roomDao.getOne(dto.getRoomId()));
        // it is said that save is not necessary
        return new HeaterDto(heaterDao.save(heater));
    }

    @PostMapping
    public HeaterDto create(@RequestBody HeaterDto dto) {
        // HeaterDto must always contain the heater room
        Room room = roomDao.findById(dto.getRoomId()).orElseThrow(IllegalArgumentException::new);
        // On creation id is NOT specified
        if (dto.getId() == null) {
            return( new HeaterDto(
                    heaterDao.save(new Heater(room, dto.getName(), dto.getHeaterStatus(), dto.getPower()))
                )
            );
        } else {
            // On creation id is specified
            // NOTE this is useless
            Heater newHeater = new Heater(room, dto.getName(), dto.getHeaterStatus(), dto.getPower());
            newHeater.setId(dto.getId());
            return( new HeaterDto(
                    heaterDao.save(newHeater)
                )
            );
        }
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        heaterDao.deleteById(id);
    }
}
