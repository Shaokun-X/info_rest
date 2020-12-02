package com.wmp.demo.dao;

import com.wmp.demo.model.Heater;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeaterDao extends JpaRepository<Heater, Long>, HeaterCustomDao {
    
}
