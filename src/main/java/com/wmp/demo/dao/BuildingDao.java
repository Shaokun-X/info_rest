package com.wmp.demo.dao;

import com.wmp.demo.model.Building;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingDao extends JpaRepository<Building, Long> {
    
}
