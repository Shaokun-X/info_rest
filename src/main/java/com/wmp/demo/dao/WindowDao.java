package com.wmp.demo.dao;

import java.util.List;

import com.wmp.demo.model.Window;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 

// looks like that spring would somehow create `implementation` for each subtype interface of JpaRepository
// (since it is a "Repository", actually the name implies that)
public interface WindowDao extends JpaRepository<Window, Long>, WindowCustomDao {
    @Query("select w from Window w where w.name = ?1")
    List<Window> findByName(String name);
}
