package com.wmp.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.wmp.demo.model.Window;
import com.wmp.demo.model.Window.WindowStatus;

public class WindowCustomDaoImpl implements WindowCustomDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Window> findRoomOpenWindowsByRoomId(Long id) {
        String jpql = "select w from Window w where w.room.id = :id and w.windowStatus= :status";
        return em.createQuery(jpql, Window.class).setParameter("id", id).setParameter("status", WindowStatus.OPEN)
                .getResultList();
    }

    @Override
    public void deleteByRoomId(Long id) {
        String jpql = "select w from Window w where w.room.id = :id";
        List<Window> windowList = em.createQuery(jpql, Window.class).setParameter("id", id).getResultList();
        for (Window window : windowList) {            
            em.remove(window);
        }
    }
}
