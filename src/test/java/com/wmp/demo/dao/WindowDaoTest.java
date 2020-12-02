package com.wmp.demo.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.wmp.demo.model.Room;
import com.wmp.demo.model.Window;
import com.wmp.demo.model.Window.WindowStatus;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WindowDaoTest {
    
    @Autowired
    private WindowDao windowDao;
    
    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldCreateOneNewWindow() {
        Window w = new Window("test", WindowStatus.CLOSED);
        windowDao.save(w);
        windowDao.findAll();
        List<Window> w_list =  windowDao.findByName("test");
        assertTrue(w_list.get(0).getName() == "test");
    }

    @Test
    public void shoudFindWindow() {
        Window window = windowDao.getOne(-10L);
        Assertions.assertThat(window.getName()).isEqualTo("Window1");
        Assertions.assertThat(window.getWindowStatus()).isEqualTo(WindowStatus.CLOSED);
    }

    @Test
    public void shouldFindOpenWindowByRoomId() {
        List<Window> result = windowDao.findRoomOpenWindowsByRoomId(-9L);
        Assertions.assertThat(result).extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN));
    }

    @Test
    public void shouldNotFindRoomOpenWindows() {
        List<Window> result = windowDao.findRoomOpenWindowsByRoomId(-10L);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldDeleteWindowsRoom() {
        Room room = roomDao.getOne(-10L);
        List<Long> roomIds = room.getWindows().stream().map(Window::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        windowDao.deleteByRoomId(-10L);
        List<Window> result = windowDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();

    }
}
