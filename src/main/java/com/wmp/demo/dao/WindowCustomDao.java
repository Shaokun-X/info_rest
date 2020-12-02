package com.wmp.demo.dao;

import java.util.List;

import com.wmp.demo.model.Window;

public interface WindowCustomDao {
    List<Window> findRoomOpenWindowsByRoomId(Long id);
    
    void deleteByRoomId(Long id);

}
