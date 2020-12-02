package com.wmp.demo.dao;

import java.util.List;

import com.wmp.demo.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface RoomDao extends JpaRepository<Room, Long> {
    @Query("select r from Room r where r.name = ?1")
    List<Room> findByName(String name);    
}
