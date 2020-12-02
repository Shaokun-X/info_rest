package com.wmp.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.wmp.demo.dao.RoomDao;
import com.wmp.demo.dao.WindowDao;
import com.wmp.demo.dto.WindowDto;
import com.wmp.demo.model.Room;
import com.wmp.demo.model.Window;
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
@RequestMapping("/api/windows")
@Transactional // because Hibernate you cannot execute a query outside of a transaction
public class WindowController {
 
    private final WindowDao windowDao;
    private final RoomDao roomDao;

    public WindowController(WindowDao windowDao, RoomDao roomDao) {
        this.windowDao = windowDao;
        this.roomDao = roomDao;
    }

    @GetMapping
    public List<WindowDto> findAll() {
        return windowDao.findAll().stream().map(WindowDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public WindowDto findById(@PathVariable Long id) {
        return windowDao.findById(id).map(WindowDto::new).orElse(null);
    }

    @PutMapping(path = "/{id}/switch")
    public WindowDto switchStatus(@PathVariable Long id) {
        Window window = windowDao.findById(id).orElseThrow(IllegalArgumentException::new);
        window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED : WindowStatus.OPEN);
        return new WindowDto(windowDao.save(window));
    }

    @PutMapping(path = "/{id}")
    public WindowDto update(@RequestBody WindowDto dto, @PathVariable Long id) {
        Window window = windowDao.findById(id).orElseThrow(IllegalArgumentException::new);
        window.setWindowStatus(dto.getWindowStatus());
        window.setName(dto.getName());
        window.setRoom(roomDao.getOne(dto.getRoomId()));
        // it is said that save is not necessary using getOne()
        return new WindowDto(windowDao.save(window));
    }    

    @PostMapping
    public WindowDto create(@RequestBody WindowDto dto) {
        // WindowDto must always contain the window room
        Room room = roomDao.findById(dto.getRoomId()).orElseThrow(IllegalArgumentException::new);
        // On creation id is NOT specified
        if (dto.getId() == null) {
            return new WindowDto(
                 windowDao.save(new Window(room, dto.getName(), dto.getWindowStatus()))
            );
        } else {
        // On creation id is specified
            Window newWindow = new Window(room, dto.getName(), dto.getWindowStatus());
            newWindow.setId(dto.getId());
            return new WindowDto( 
                windowDao.save(newWindow) 
            );
        }
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        windowDao.deleteById(id);
    }
 
}
