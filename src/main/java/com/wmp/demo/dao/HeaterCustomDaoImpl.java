package com.wmp.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.wmp.demo.model.Heater;

public class HeaterCustomDaoImpl implements HeaterCustomDao {
    @PersistenceContext
    private EntityManager em;
        
    @Override
    public void deleteByRoomId(Long id) {
        String jpql = "select r from Heater r where r.room.id = :id";
        List<Heater> heaterList = em.createQuery(jpql, Heater.class).setParameter("id", id).getResultList();
        for (Heater heater : heaterList) {
            em.remove(heater);
        }
    }
}
